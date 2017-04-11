package com.winter.horobot.scheduler;

/**
 * ayy it repeats
 * <br>
 * Created by Arsen on 20.9.16..
 */
public abstract class HoroTask implements Runnable {

	private String taskName;

	private HoroTask() {
	}

	public HoroTask(String taskName) {
		this.taskName = taskName;
	}

	public boolean repeat(long delay, long interval) {
		return Scheduler.scheduleRepeating(this, taskName, delay, interval);
	}

	public void delay(long delay) {
		Scheduler.delayTask(this, delay);
	}

	public boolean cancel() {
		return Scheduler.cancelTask(taskName);
	}
}