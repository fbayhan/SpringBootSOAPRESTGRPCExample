package com.library.grpc.service;

import com.library.protos.BidirectionalStreamingRequest;
import com.library.protos.BidirectionalStreamingResponse;
import com.library.protos.BidirectionalStreamingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BidirectionalStreamingService extends BidirectionalStreamingServiceGrpc.BidirectionalStreamingServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(BidirectionalStreamingService.class);

    @Override
    public StreamObserver<BidirectionalStreamingRequest> bidirectionalStreamingCall(StreamObserver<BidirectionalStreamingResponse> responseObserver) {
        return new StreamObserver<BidirectionalStreamingRequest>() {
            @Override
            public void onNext(BidirectionalStreamingRequest value) {
                logger.info("Received bidirectional streaming request with message: {}", value.getMessage());
                BidirectionalStreamingResponse response = BidirectionalStreamingResponse.newBuilder().setMessage("Response: " + value.getMessage()).build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error in bidirectional streaming call", t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                logger.info("Bidirectional streaming completed.");
            }
        };
    }
}