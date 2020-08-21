package com.example.xxc.nettydemo.common;

import com.example.xxc.nettydemo.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 功能描述：
 *
 * @author: 薛行晨(RoyalXC)
 * @date: 2020/8/21 17:48
 */
public class FileEncoder extends MessageToByteEncoder<byte[]> {

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {

        byte[] lenBytes = ByteUtil.intToByte(msg.length);
        byte[] data = ByteUtil.concat(lenBytes, msg);

        out.writeBytes(data);
    }

}
