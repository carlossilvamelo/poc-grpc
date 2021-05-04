package com.example.grcpserver;

import interfaces.Car;
import interfaces.CarServiceGrpc;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Arrays;

import com.google.protobuf.Int32Value;

@GrpcService
public class CarsGrpcServiceImpl extends CarServiceGrpc.CarServiceImplBase {

    @Override
    public void getCar(Car request, StreamObserver<Car> responseObserver) {

        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(num -> {
                    Car car = Car
                            .newBuilder()
                            .setId(num)
                            .setModel("FIAT")
                            .setName("UNO")
                            .addDoors("driver's door")
                            .addDoors("my passenger door")
                            .build();

//                    throwException(responseObserver);
                    responseObserver.onNext(car);
                });
        responseObserver.onCompleted();
    }

    @Override
    public void getCarById(Int32Value request, StreamObserver<Car> responseObserver) {
        super.getCarById(request, responseObserver);

    }

    private Status buildStatus(Status status, String description, Throwable cause) {
        return status
                .withDescription(description)
                .withCause(cause);
    }

    private StatusRuntimeException getException(Status status, String description
            , Throwable cause, Metadata metadata) {
        Status statusConfig = buildStatus(status,
                description,
                cause);

        return new StatusRuntimeException(statusConfig, metadata);
    }

    private Metadata getMetadata() {
        Metadata metadata = new Metadata();
        Metadata.Key<String> key = Metadata.Key
                .of("key_name", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(key, "value string");
        return metadata;
    }

    private void throwException(StreamObserver<Car> responseObserver) {
        StatusRuntimeException throwableError = getException(
                Status.NOT_FOUND,
                "Description message error!",
                new RuntimeException(),
                getMetadata());

        responseObserver
                .onError(throwableError);
    }

}
