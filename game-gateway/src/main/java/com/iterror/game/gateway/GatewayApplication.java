package com.iterror.game.gateway;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tony.yan on 2017/8/31.
 */
@Configuration
@ComponentScan
public class GatewayApplication {

    public static void main(String[] args) {
        System.setProperty("io.netty.noUnsafe","true");
        System.out.print("server start .......");
        GatewayServer.start();
        System.out.print("server done .......");

    }
}
