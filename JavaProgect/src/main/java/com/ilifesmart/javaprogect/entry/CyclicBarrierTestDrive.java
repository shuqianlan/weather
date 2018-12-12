package com.ilifesmart.javaprogect.entry;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTestDrive {
	private final MOBaseThreadSafe mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;

	public CyclicBarrierTestDrive(MOBaseThreadSafe board) {
		mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();
		barrier = new CyclicBarrier(count, new Runnable() {
			@Override
			public void run() {
				mainBoard.commitNewValues();
			}
		});

		this.workers = new Worker[count];

		for (int i = 0; i < count; i++) {
//			workers[i] = new Worker(mainBoard.getName());
		}
	}

	private class Worker implements Runnable {

		private final MOBaseThreadSafe board;

		public Worker(MOBaseThreadSafe mo) {
			this.board = mo;
		}

		@Override
		public void run() {

		}
	}

}
