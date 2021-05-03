package com.example.grcpclient.service;

import interfaces.Car;
import interfaces.CarServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class CarClientService {


    @GrpcClient("server-service")
    private CarServiceGrpc.CarServiceBlockingStub CarServiceBlockingStub;

    @Autowired
    private CarClientService2 carClientService2;

    public List<Car> getCars() {
        Iterator<Car> carIterator =
                CarServiceBlockingStub.getCar(Car.newBuilder().build());

        Iterable<Car> iterable = () -> carIterator;
        Stream<Car> carStream = StreamSupport
                .stream(iterable.spliterator(), false);

        return carStream.collect(Collectors.toList());
    }

//    public List<Car> getCars2() {
//        return carClientService2.getCars();
//    }


}

