package com.example.xxc.nettydemo.common;


import com.example.xxc.nettydemo.entities.FileData;
import com.example.xxc.nettydemo.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 功能描述：
 *
 * @author: 薛行晨(RoyalXC)
 * @date: 2020/8/21 17:47
 */
public class FileDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int HEAD_SIZE = 4;
        if (in.readableBytes() < HEAD_SIZE) {
            System.out.println("长度小于4");
            return;
        }

        in.markReaderIndex();

        byte[] head = new byte[4];
        head[0] = in.getByte(0);
        head[1] = in.getByte(1);
        head[2] = in.getByte(2);
        head[3] = in.getByte(3);

        int dataSize = ByteUtil.byteToInt(head, 0);

        if (in.readableBytes() < dataSize) {
            in.resetReaderIndex();
            System.out.println("内容不足:" + dataSize);
            return;
        }

        byte[] data = new byte[dataSize + 4];
        in.readBytes(data);

        FileData fileData = FileData.getByBytes(data);
        out.add(fileData);

        in.discardReadBytes();
    }
}
