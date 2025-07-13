package com.library.grpc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.library.grpc.config.GrpcServerConfigProperties;
import com.library.grpc.service.BidirectionalStreamingService;
import com.library.grpc.service.ClientStreamingService;
import com.library.grpc.service.HelloSercive;
import com.library.grpc.service.LibraryService;
import com.library.grpc.service.ServerStreamingService;
import com.library.grpc.service.UnaryService;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class GrpcApplication implements CommandLineRunner {

	private final GrpcServerConfigProperties grpcServerConfigProperties;
	private final LibraryService libraryService;
	private final UnaryService unaryService;
	private final ServerStreamingService serverStreamingService;
	private final ClientStreamingService clientStreamingService;
	private final BidirectionalStreamingService bidirectionalStreamingService;
	private final HelloSercive helloSercive;

	public static void main(String[] args) {
		SpringApplication.run(GrpcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Configure the gRPC server to listen on the specified port and add services.
		Server server = ServerBuilder.forPort(grpcServerConfigProperties.getPort())
				.addService(unaryService)
				.addService(serverStreamingService)
				.addService(clientStreamingService)
				.addService(bidirectionalStreamingService)
				.addService(helloSercive)
				.addService(libraryService)
				.build();

		server.start(); // Start the gRPC server
		System.out.println("🚀 gRPC Server has successfully started on port 9091.");

		// Add a shutdown hook to gracefully stop the gRPC server when the JVM is shutting down
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("⚠️ Initiating shutdown of gRPC server...");
			server.shutdown();
			System.out.println("✅ gRPC Server has been shut down gracefully.");
		}));

		// Keep the server running
		System.out.println("💡 gRPC Server is running and awaiting termination...");
		server.awaitTermination();
	}

}
