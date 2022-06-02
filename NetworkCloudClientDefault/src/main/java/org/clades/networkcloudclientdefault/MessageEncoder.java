/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.clades.networkcloudclientdefault;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author degelad
 */
public class MessageEncoder extends MessageToByteEncoder<Message>{
    
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception{
        out.writeInt(msg.getType().length());
        out.writeCharSequence(msg.getType(), StandardCharsets.UTF_8);
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getData());
    }
}