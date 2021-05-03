package com.example.grcpclient.service;

import com.example.grcpclient.dto.CarDto;
import com.example.grcpclient.config.ClientBeans;
import interfaces.Car;
import interfaces.CarServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Service
public class CarClientService2 {

    @Autowired
    private ManagedChannel channel;

    public List<CarDto> getCars() {

        CarServiceGrpc.CarServiceBlockingStub stub
                = CarServiceGrpc.newBlockingStub(channel);

        List<CarDto> cars = new ArrayList();

        stub.getCar(Car.newBuilder().build())
                .forEachRemaining(messageResponse -> {
                    System.out.println(messageResponse);
                    cars.add(CarDto.fromProto(messageResponse));
                });

        channel.shutdown();

        return cars;
    }
}