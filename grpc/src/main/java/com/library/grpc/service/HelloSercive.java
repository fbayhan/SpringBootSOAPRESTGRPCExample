package com.library.grpc.service;

import com.library.grpc.HelloRequest;
import com.library.grpc.HelloResponse;
import com.library.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class HelloSercive extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        System.out.println("Hello " + request.getName() + " say hello");
    }
}
