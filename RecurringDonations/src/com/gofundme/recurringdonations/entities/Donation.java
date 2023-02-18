package com.gofundme.recurringdonations.entities;

/**
 * @author kiran Entity to store the donation data.
 */

public class Donation extends AbstractGfmDonationEntity {
	private final String donorName;
	private final String campaignName;
	private final int amount;

	public Donation(String donorName, String campaignName, int amount) {
		this.donorName = donorName;
		this.campaignName = campaignName;
		this.amount = amount;
	}

	public String getDonorName() {
		return donorName;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public int getAmount() {
		return amount;
	}
}