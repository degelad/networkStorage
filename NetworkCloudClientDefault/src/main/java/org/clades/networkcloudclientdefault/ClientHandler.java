/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.clades.networkcloudclientdefault;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author degelad
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private Message message;

    public ClientHandler(Message message) {
        this.message = message;
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(message);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String response = (String) msg;
        System.out.println("response = " + response);
    }
}