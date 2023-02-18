package com.gofundme.recurringdonations.entities;

/**
 * @author kiran Entity for a Donor data. Apart from local variables and
 *         getters, it also contains a logic-method to do the donations. Ideally
 *         in production code, this class should contain only the Donor data and
 *         no logic; the logic-method should be in a child class. But creating a
 *         child-class seem like an overkill for this exercise, so adding the
 *         logic-method here itself.
 */

public class Donor extends AbstractGfmDonationEntity {
	private final String name;
	private final int limit;
	private int numberOfDonations;
	private int totalDonation;

	public Donor(String name, int limit) {
		this.name = name;
		this.limit = limit;
	}

	public String getName() {
		return name;
	}

	public int getTotalDonation() {
		return totalDonation;
	}

	public int getNumberOfDonations() {
		return numberOfDonations;
	}

	public Integer getLimit() {
		return limit;
	}

	public boolean donateIfAllowed(int amount) {
		if (totalDonation + amount <= limit) {
			totalDonation += amount;
			numberOfDonations++;
			return true;
		} else {
			return false;
		}
	}
}
