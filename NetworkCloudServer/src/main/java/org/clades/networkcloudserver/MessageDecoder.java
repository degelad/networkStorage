/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.clades.networkcloudserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author degelad
 */
public class MessageDecoder extends ReplayingDecoder<Message>{
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
//        int typeLen = in.readInt();
//        String type = in.readCharSequence(typeLen, StandardCharsets.UTF_8).toString();
//        int length = in.readInt();
//        byte[] data = new byte[length];
//        in.readBytes(data, 0, length);
//        
//        Message message = new Message(type, length, data);
//        out.add(message);
    }
}
