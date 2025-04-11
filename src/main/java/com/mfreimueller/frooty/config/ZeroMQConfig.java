package com.mfreimueller.frooty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

@Configuration
public class ZeroMQConfig {

    private final String address;
    private final ZContext context;

    public ZeroMQConfig(@Value("${zeromq.address}") String address) {
        this.address = address;
        this.context = new ZContext();

    }

    public String getAddress() {
        return address;
    }

    @Bean
    public ZMQ.Socket socket() {
        ZMQ.Socket socket = context.createSocket(SocketType.REP);
        socket.connect(address);

        return socket;
    }
}
