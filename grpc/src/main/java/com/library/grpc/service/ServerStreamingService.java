package com.library.grpc.service;

import com.example.grpc.ServerStreamingRequest;
import com.example.grpc.ServerStreamingResponse;
import com.example.grpc.ServerStreamingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServerStreamingService extends ServerStreamingServiceGrpc.ServerStreamingServiceImplBase {



    @Override
    public void serverStreamingCall(ServerStreamingRequest request, StreamObserver<ServerStreamingResponse> responseObserver) {
        System.out.println("Received server streaming call request with message:  "+ request.getMessage());

        for (int i = 1; i <= 5; i++) {
            ServerStreamingResponse response = ServerStreamingResponse.newBuilder().setMessage("Server streaming response " + i + ": " + request.getMessage()).build();
            responseObserver.onNext(response);
            System.out.println("Sent server streaming response:  "+ response.getMessage());
        }

        responseObserver.onCompleted();
        System.out.println("Server streaming completed.");
    }
}