package com.gofundme.recurringdonations.executors;

/**
 * @author kiran
 * A class to hold the response of executing all the input lines
 */

import java.util.Map;

import com.gofundme.recurringdonations.entities.Campaign;
import com.gofundme.recurringdonations.entities.Donor;

public class InputEntitiesExecutionResult {
	private Map<String, Donor> donorDonations;
	private Map<String, Campaign> campaignDonations;

	InputEntitiesExecutionResult(Map<String, Donor> donorDonations, Map<String, Campaign> campaignDonations) {
		this.donorDonations = donorDonations;
		this.campaignDonations = campaignDonations;
	}

	public Map<String, Donor> getDonorDonations() {
		return donorDonations;
	}

	public Map<String, Campaign> getCampaignDonations() {
		return campaignDonations;
	}
}
