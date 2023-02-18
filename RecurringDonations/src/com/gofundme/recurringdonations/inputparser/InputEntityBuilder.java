package com.gofundme.recurringdonations.inputparser;

/**
 * @author kiran
 * This is a builder used to build various types of AbstractGfmDonationEntity objects
 */

import com.gofundme.recurringdonations.entities.Campaign;
import com.gofundme.recurringdonations.entities.Donation;
import com.gofundme.recurringdonations.entities.Donor;
import com.gofundme.recurringdonations.entities.EntityType;
import com.gofundme.recurringdonations.entities.AbstractGfmDonationEntity;
import com.gofundme.recurringdonations.executors.GfmDonationsException;
import com.gofundme.recurringdonations.executors.GfmDonationsExceptionCode;

public class InputEntityBuilder {
	private final EntityType entityType;
	private String donorName;
	private String campaignName;
	private int amount;
	private int limit;

	public InputEntityBuilder(EntityType entityType) {
		this.entityType = entityType;
	}

	public InputEntityBuilder donorName(String donorName) {
		this.donorName = donorName;
		return this;
	}

	public InputEntityBuilder campaignName(String campaignName) {
		this.campaignName = campaignName;
		return this;
	}

	public InputEntityBuilder limit(int limit) {
		this.limit = limit;
		return this;
	}

	public InputEntityBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}

	public AbstractGfmDonationEntity build() {
		switch (this.entityType) {
		case ADD_DONOR:
			return new Donor(this.donorName, this.limit);
		case ADD_CAMPAIGN:
			return new Campaign(this.campaignName);
		case DONATE:
			return new Donation(this.donorName, this.campaignName, this.amount);
		default:
			throw new GfmDonationsException(GfmDonationsExceptionCode.INPUT_FILE_PARSING_ERROR,
					"Encountered incorrect type of line");
		}
	}
}
