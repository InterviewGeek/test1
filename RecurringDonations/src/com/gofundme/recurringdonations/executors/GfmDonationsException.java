package com.gofundme.recurringdonations.executors;

/**
 * @author kiran
 * This file contains an exception class for this application
 */

import java.util.Map;

public class GfmDonationsException extends RuntimeException {
	private final GfmDonationsExceptionCode code;
	private final String message;
	private final Map<String, String> params;

	public GfmDonationsException(GfmDonationsExceptionCode code, String message, Map<String, String> params) {
		this.code = code;
		this.message = message;
		this.params = params;
	}

	public GfmDonationsException(GfmDonationsExceptionCode code, String message) {
		this.code = code;
		this.message = message;
		this.params = null;
	}

	public GfmDonationsExceptionCode getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, String> getParams() {
		return params;
	}
}