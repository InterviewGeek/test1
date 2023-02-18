package com.gofundme.recurringdonations.executors;

/**
 * @author kiran This is the main class which will start the excecution
 */

public class Main {
	public static void main(String[] args) {
		try {
			GfmDonationsExecutor gfmDonationsExecutor = new GfmDonationsExecutor();
			gfmDonationsExecutor.execute(args);
		} catch (Exception e) {
			// construct error response to the client here and throw
		}
	}
}