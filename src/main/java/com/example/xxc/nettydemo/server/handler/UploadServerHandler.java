package com.example.xxc.nettydemo.server.handler;

import com.example.xxc.nettydemo.entities.FileData;
import com.example.xxc.nettydemo.utils.SerializableUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.io.RandomAccessFile;

/**
 * 功能描述：
 *
 * @author: 薛行晨(RoyalXC)
 * @date: 2020/8/21 17:48
 */
public class UploadServerHandler extends SimpleChannelInboundHandler<FileData> {

    String putPath = "D:\\to";

    int loss = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("超过5秒未收到");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                loss++;
                if (loss > 3) {
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FileData msg) throws Exception {
        loss = 0;
        System.out.println(msg.toString());

        if (!msg.isEnd()) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(putPath + "//" + msg.getFileName(), "rw");
            randomAccessFile.seek(msg.getLast());
            randomAccessFile.write(msg.getData());

            randomAccessFile.close();

            msg.initData(true);
            ctx.writeAndFlush(SerializableUtil.objectToBytes(msg));
        }
    }
}
