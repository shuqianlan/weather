package com.ilifesmart.javaprogect.ExectorTestDrive;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class LifecycleWebServer {
	private final ExecutorService mExecutorService = Executors.newFixedThreadPool(100);

	public void start() throws IOException {
		ServerSocket socket = new ServerSocket(80);

		while (!mExecutorService.isShutdown()) {
			try {
				final Socket conn = socket.accept();
				mExecutorService.execute(new Runnable() {
					@Override
					public void run() {
						handleRequest(conn);
					}
				});
			} catch(RejectedExecutionException ex) {
				ex.printStackTrace();
				if (!mExecutorService.isShutdown()) {
					System.out.println("rejected .. ");
				}
			}
		}
	}

	public void stop() {
		mExecutorService.shutdown();
	}

	private void handleRequest(Socket conn) {
		//TODO:

		Executors.newScheduledThreadPool(100);
	}
}
