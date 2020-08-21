package com.example.xxc.nettydemo;

import com.example.xxc.nettydemo.client.NettyClient;
import com.example.xxc.nettydemo.server.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能描述：
 *
 * @author: 薛行晨(RoyalXC)
 * @date: 2020/8/21 17:48
 */
@SpringBootApplication
public class NettyDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        new Thread(() -> {
            try {
                new NettyServer().run(8088);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                new NettyClient().run("127.0.0.1", 8088);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
