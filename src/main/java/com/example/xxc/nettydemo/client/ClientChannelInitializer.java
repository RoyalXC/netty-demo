package com.example.xxc.nettydemo.client;

import com.example.xxc.nettydemo.client.handler.UploadClientHandler;
import com.example.xxc.nettydemo.common.FileDecoder;
import com.example.xxc.nettydemo.common.FileEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述：
 *
 * @author: 薛行晨(RoyalXC)
 * @date: 2020/8/21 17:47
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //增加心跳机制
        pipeline.addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
        pipeline.addLast(new FileDecoder());
        pipeline.addLast(new FileEncoder());
        pipeline.addLast(new UploadClientHandler());

    }

}
