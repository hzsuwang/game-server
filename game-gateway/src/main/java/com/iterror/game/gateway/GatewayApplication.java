package com.iterror.game.gateway;
import com.iterror.game.gateway.tcp.handler.TcpServerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by tony.yan on 2017/8/31.
 */
@Configuration
@ComponentScan
//@ImportResource(locations={"classpath:application-bean.xml"})
public class GatewayApplication {

    public static void main(String[] args) {
        System.setProperty("io.netty.noUnsafe","true");
        System.out.print("server start .......");
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:application-bean.xml");
        GatewayServer gatewayServer = (GatewayServer)context.getBean("gatewayServer");
        gatewayServer.start();
        System.out.print("server done .......");
    }
}
