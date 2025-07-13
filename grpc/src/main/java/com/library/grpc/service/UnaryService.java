package com.library.grpc.service;

import com.example.grpc.UnaryRequest;
import com.example.grpc.UnaryResponse;
import com.example.grpc.UnaryServiceGrpc;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UnaryService extends UnaryServiceGrpc.UnaryServiceImplBase {



    @Override
    public void unaryCall(UnaryRequest request, StreamObserver<UnaryResponse> responseObserver) {
        System.out.println("Received unary call request with message:  " + request.getMessage());
        UnaryResponse response = UnaryResponse.newBuilder().setMessage("Unary response: " + request.getMessage()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        System.out.println("Unary response sent:  " + response.getMessage());
    }


}