/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.clades.networkcloudclientdefault;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author degelad
 */
public class ClientApp {
    
    private final int port;
    private final String host;
    
    public ClientApp(int port, String host) {
        this.port = port;
        this.host = host;
    }
    
    public static void main(String[] args) throws Exception {
        Message message = new Message("type", 5, new byte[]{1, 2, 3, 4, 5});
        new ClientApp(8189, "localhost");
    }
    
    private void run(Message message) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(workerGroup)
                .channel(NioSocketChannel.class);
            client.option(ChannelOption.SO_KEEPALIVE, true);
            client.handler(new ChannelInitializer<SocketChannel>(){
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new MessageEncoder(),
                            new LineBasedFrameDecoder(80),
                            new StringDecoder(StandardCharsets.UTF_8),
                            new ClientHandler(message)
                    );
                }
            });
            
            ChannelFuture future = client.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
