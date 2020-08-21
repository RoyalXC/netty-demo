package com.example.xxc.nettydemo.entities;

import com.example.xxc.nettydemo.utils.SerializableUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述：
 *
 * @author: 薛行晨(RoyalXC)
 * @date: 2020/8/21 17:48
 */
@Data
public class FileData implements Serializable {

    private String fileName;

    private long totalSize;

    private byte[] data;

    private long index = 0;

    private long last = 0;

    private boolean end = false;

    public void initData(boolean empty) {
        if (empty) {
            this.data = new byte[0];
        } else {
            if (this.totalSize > 500 * 1024 * 1024) {
                this.data = new byte[10 * 1024 * 1024];
            } else if (this.totalSize < 150 * 1024 * 1024) {
                this.data = new byte[3 * 1024 * 1024];
            } else {
                this.data = new byte[(int) Math.ceil(this.totalSize / 50.0)];
            }
        }
    }

    public void addIndex(int size) {
        this.index += size;
    }

    public static FileData getByBytes(byte[] data) {
        byte[] newData = new byte[data.length - 4];
        System.arraycopy(data, 4, newData, 0, data.length - 4);
        return (FileData) SerializableUtil.bytesToObject(newData);
    }
}
