/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.clades.networkcloudserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author degelad
 */
public class ServerApp {
    private static final int PORT = 8189;

//    public ServerApp(int port) {
//        this.port = port;
//    }
    
    public static void main(String[] args) throws Exception{
//        new ServerApp(8189).run();
    }
    private void run() throws Exception{
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    
            try {
                ServerBootstrap server = new ServerBootstrap();
                server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                server.option(ChannelOption.SO_BACKLOG, 128);
//                server.childOption(ChannelOption.SO_KEEPALIVE, true);
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new MainInServerHandler()
//                            new MessageDecoder(),
//                            new StringEncoder(StandardCharsets.UTF_8),
//                            new ServerHandler()
                        
                        );
                   
                    }
                });
                   ChannelFuture future = server.bind(PORT).sync();
                   future.channel().closeFuture().sync();
            } catch(Exception e){
                e.printStackTrace();
            } 
            finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
    }
    
}
