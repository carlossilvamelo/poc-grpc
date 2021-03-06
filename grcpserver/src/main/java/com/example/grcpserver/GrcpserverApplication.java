package com.example.grcpserver;

import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class GrcpserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrcpserverApplication.class, args);
	}

	@Bean
	public GrpcServerConfigurer keepAliveServerConfigurer() {
		return serverBuilder -> {
			if (serverBuilder instanceof NettyServerBuilder) {
				((NettyServerBuilder) serverBuilder).keepAliveTime(30, TimeUnit.SECONDS)
						.keepAliveTimeout(5, TimeUnit.SECONDS).permitKeepAliveWithoutCalls(true);
			}
		};
	}

//	@Bean
//	public Server server(){
//		return ServerBuilder.forPort(9090)
//				.addService(new CarsGrpcServiceImpl()).build();
//	}
}
