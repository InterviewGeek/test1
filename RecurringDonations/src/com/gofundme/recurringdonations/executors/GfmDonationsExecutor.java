package com.gofundme.recurringdonations.executors;

/**
 * @author kiran
 * This class is the starting point for the application
 * It acts as an orchestrator which will call utilities that will 1) read the input file 2) execute the donations
 * and 3) print results
 * As this is the starting point of the app, it will also handle any exceptions thrown by the app
 */

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.gofundme.recurringdonations.entities.AbstractGfmDonationEntity;
import com.gofundme.recurringdonations.inputparser.InputParser;

public class GfmDonationsExecutor {

	public void execute(String[] s) throws Exception {
		try {
			List<AbstractGfmDonationEntity> abstractGfmDonationEntities = InputParser.parseInputLinesInOrder(s);
			InputEntitiesExecutionResult inputEntitiesExecutionResult = InputEntitiesExecutor
					.execute(abstractGfmDonationEntities);
			InputEntitiesExecutor.printExecutionResult(inputEntitiesExecutionResult);
		} catch (Exception e) {
			handleException(e);
		}
	}

	/*
	 * This method will handle any exception thrown by our application. We will use
	 * this method as a place to log the error data into a logger (like Logtail) for
	 * debugging and error-monitoring As we currently dont have a dedicated logger,
	 * this method is logging to terminal for now
	 */
	private void handleException(Exception ex) throws Exception {
		try {
			throw ex;
		} catch (GfmDonationsException e) {
			System.out.println("ErrorCode: " + e.getCode() + ", ErrorMessage: " + e.getMessage());
			Map<String, String> params = e.getParams();
			if (params != null) {
				for (String key : params.keySet()) {
					System.out.print(key + " : " + params.get(key));
				}
			}
			throw e;
		} catch (IOException ioException) {
			System.out.println("Caught IOException. Message: " + ioException.getMessage());
			throw ioException;
		} catch (Exception unknownException) {
			System.out
					.println("Caught exception of incorrect type. Exception message: " + unknownException.getMessage());
			throw unknownException;
		}
	}
}
