package com.example.grcpclient.grpctests;

import interfaces.Car;
import interfaces.CarServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.grpc.testing.GrpcCleanupRule;

import java.util.Arrays;

import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.mock;


public class ClientTests {

    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    private CarServiceGrpc.CarServiceBlockingStub client;

    private final CarServiceGrpc.CarServiceImplBase serviceImpl = getMockClient();


    @BeforeEach
    public void setUp() throws Exception {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(serviceImpl).build().start());

        // Create a client channel and register for automatic graceful shutdown.
        ManagedChannel channel = grpcCleanup.register(
                InProcessChannelBuilder.forName(serverName).directExecutor().build());

        client = CarServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void testExample() {
        client.getCar(Car.newBuilder().build())
                .forEachRemaining(messageResponse -> {
                    System.out.println(messageResponse);
                });
    }


    private CarServiceGrpc.CarServiceImplBase getMockClient() {
        return mock(CarServiceGrpc.CarServiceImplBase.class, delegatesTo(
                new CarServiceGrpc.CarServiceImplBase() {

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

                                    responseObserver.onNext(car);
                                });
                        responseObserver.onCompleted();
                    }
                }));
    }
}
