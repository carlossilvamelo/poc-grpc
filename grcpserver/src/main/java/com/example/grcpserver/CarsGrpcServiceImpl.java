package com.example.grcpserver;

import interfaces.Car;
import interfaces.CarServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.Arrays;

@GrpcService
public class CarsGrpcServiceImpl extends CarServiceGrpc.CarServiceImplBase{

    @Override
    public void getCar(Car request, StreamObserver<Car> responseObserver) {

        Arrays.asList(1,2,3,4,5)
                .forEach(num ->{
                    Car car = Car.newBuilder()
                            .setId(num)
                            .setModel("FIAT")
                            .setName("UNO")
                            .addDoors("driver's door")
                            .addDoors("my passenger door")
                            .build();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    responseObserver.onNext(car);
                });

        responseObserver.onCompleted();
    }


}
