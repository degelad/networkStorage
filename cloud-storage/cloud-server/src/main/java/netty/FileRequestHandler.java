package netty;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.CommandMessage;
import model.FileMessage;
import model.Message;
import niofilesystem.NFSResponse;
import niofilesystem.NioFileSystem;

public class FileRequestHandler extends SimpleChannelInboundHandler<Message> {

    private static final Map<String,ChannelHandlerContext> clients = new HashMap<String,ChannelHandlerContext>();
    private static final Map<String,NioFileSystem> fss = new HashMap<String, NioFileSystem>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        NFSResponse response = null;
        String username = msg.getUsername();
        NioFileSystem fs = this.getFileSystem(username);
        clients.put(username, ctx);
        if (msg instanceof FileMessage) {
            response = fs.put((FileMessage) msg);
        } else if (msg instanceof CommandMessage) {
            response = handleCommandMessage((CommandMessage) msg, fs);
        }

        if(response != null) {
            response.setUsername(msg.getUsername());
            ctx.writeAndFlush(response);
        }
    }

    private NioFileSystem getFileSystem(String username) {
        NioFileSystem fs = fss.get(username);
        if(fs == null) {
            fs = new NioFileSystem("filesServer/" + username);
            fss.put(username, fs);
        }
        return fs;
    }

    private void writeToChannel(FileMessage msg) {
        ChannelHandlerContext ctx = clients.get(msg.getUsername());
        if(ctx != null) {
            ctx.writeAndFlush(msg);
        } else {
            System.out.println("NO CONTEXT " + msg.getUsername());
        }
    }


    private NFSResponse handleCommandMessage(CommandMessage msg, NioFileSystem fs) {
        String command = msg.getContent();
        String[] args = command.split(" ");
        NFSResponse response = new NFSResponse(command);

        if (command.equals("ls")) {
            response = fs.ls();
        }

        if (command.startsWith("cd")) {
            if (args.length != 2) {
                response = fs.error("???????????? ???????? ???????????? 2 ??????????????????");
            } else {
                response = fs.cd(args[1]);
            }
        }

        if (command.startsWith("cat")) {
            if (args.length != 2) {
                response = fs.error("???????????? ???????? ???????????? 2 ??????????????????");
            } else {
                response = fs.cat(args[1]);
            }
        }

        if (command.startsWith("touch")) {
            if (args.length != 2) {
                response = fs.error("???????????? ???????? ???????????? 2 ??????????????????");
            } else {
                response = fs.touch(args[1]);
            }
        }

        if (command.startsWith("open")) {
            if (args.length != 2) {
                response = fs.error("???????????? ???????? ???????????? 2 ??????????????????");
            } else {
                String fileName = args[1];

                if (fs.isDir(fileName)) {
                   response = fs.cd(fileName);
                } else {
                    response = fs.transfer(fileName, this::writeToChannel, msg.getUsername());
                }
            }
        }

        if (command.startsWith("rm")) {
            if (args.length != 2) {
                response = fs.error("???????????? ???????? ???????????? 2 ??????????????????");
            } else {
                response = fs.rm(args[1]);
            }
        }

        if (command.startsWith("rn")) {
            if (args.length != 3) {
                response = fs.error("???????????? ???????? ???????????? 3 ??????????????????");
            } else {
                response = fs.rn(args[1], args[2]);
            }
        }

        if (command.startsWith("mkdir")) {
            if (args.length != 2) {
                response = fs.error("???????????? ???????? ???????????? 2 ??????????????????");
            } else {
                response = fs.mkDir(args[1]);
            }
        }

        return response;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clients.remove(ctx);
    }
}
