package com.example.xxc.nettydemo.server;

import com.example.xxc.nettydemo.common.FileDecoder;
import com.example.xxc.nettydemo.common.FileEncoder;
import com.example.xxc.nettydemo.server.handler.UploadServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述：
 *
 * @author: 薛行晨(RoyalXC)
 * @date: 2020/8/21 17:48
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new FileEncoder());
        pipeline.addLast(new FileDecoder());
        pipeline.addLast(new UploadServerHandler());
    }

}
