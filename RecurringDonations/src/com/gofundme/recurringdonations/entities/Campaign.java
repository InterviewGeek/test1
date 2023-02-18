package com.gofundme.recurringdonations.entities;

/**
 * @author kiran Entity for a Campaign data. Apart from local variables and
 *         getters, it also contains a logic-method to accept donations. Ideally
 *         in production code, this class should contain only the Campaign data
 *         and no logic; the logic-method should be in a child class. But
 *         creating a child-class seem like an overkill for this exercise, so
 *         adding the logic-method here itself.
 */

public class Campaign extends AbstractGfmDonationEntity {
	private final String name;
	private int totalDonation;

	public Campaign(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getTotalDonation() {
		return totalDonation;
	}

	public void acceptDonation(int amount) {
		totalDonation += amount;
	}
}