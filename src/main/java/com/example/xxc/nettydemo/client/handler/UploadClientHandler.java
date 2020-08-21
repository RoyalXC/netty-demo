package com.example.xxc.nettydemo.client.handler;

import com.example.xxc.nettydemo.entities.FileData;
import com.example.xxc.nettydemo.utils.SerializableUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 功能描述：
 *
 * @author: 薛行晨(RoyalXC)
 * @date: 2020/8/21 17:47
 */
public class UploadClientHandler extends SimpleChannelInboundHandler<FileData> {

    String filePath = "D:\\from";

    Queue<File> fileName = new ArrayDeque<>();

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        System.out.println("发送心跳文件");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                FileData fileData = new FileData();
                fileData.setEnd(true);
                ctx.writeAndFlush(SerializableUtil.objectToBytes(fileData));
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        File folder = new File(filePath);
        File[] files = folder.listFiles(File::isFile);
        if (files != null) {
            for (File file : files) {
                fileName.offer(file);
            }
        }

        byte[] data = getFileBytes();
        if (data != null) {
            ctx.writeAndFlush(data);
        }

        super.channelActive(ctx);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    private void getFileContent(FileData fileData) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath + "\\" + fileData.getFileName(), "r");
        randomAccessFile.seek(fileData.getIndex());

        fileData.initData(false);
        int readIndex = randomAccessFile.read(fileData.getData());

        if (readIndex != -1) {
            fileData.setLast(fileData.getIndex());
            fileData.addIndex(readIndex);
        } else {
            fileData.setEnd(true);
        }

        randomAccessFile.close();
    }

    private byte[] getFileBytes() throws Exception {
        File file = fileName.poll();
        if (file != null) {
            FileData fileData = new FileData();
            fileData.setFileName(file.getName());
            fileData.setTotalSize(file.length());

            getFileContent(fileData);

            return SerializableUtil.objectToBytes(fileData);
        }
        System.out.println("目录下已经没有文件");
        return null;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FileData msg) throws Exception {
        System.out.println("client收到:" + msg.toString());

        getFileContent(msg);

        if (!msg.isEnd()) {
            ctx.writeAndFlush(SerializableUtil.objectToBytes(msg));
        } else {
            System.out.println("文件传输结束，开始下一个");
            byte[] data = getFileBytes();
            if (data != null) {
                ctx.writeAndFlush(data);
            }
        }
    }
}
