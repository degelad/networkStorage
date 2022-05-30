/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.clades.networkcloudclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;



/**
 *
 * @author degelad
 */
public class Network {
    private SocketChannel channel;
    private static final String HOST = "localhost";
    private static final int PORT = 8189;
    

    public Network() {
        new Thread (()-> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap client = new Bootstrap();
                client.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                           channel = socketChannel; 
                           socketChannel.pipeline().addLast(
                                   new StringDecoder(),
                                   new StringEncoder()
                           
                           );
                        }
                        });
                ChannelFuture future = client.connect(HOST, PORT).sync();
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
            workerGroup.shutdownGracefully();
            }
        }).start();
    }
 
    public void sendMessage(String str){
        channel.writeAndFlush(str);
    }
}
