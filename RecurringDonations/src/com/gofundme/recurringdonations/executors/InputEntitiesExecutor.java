package com.gofundme.recurringdonations.executors;

/**
 * @author kiran
 * This is a utility class that contains utilities to 1)Execute the input lines and 2)Print the result
 * The execute method will also ensure that 1)Donor and Campaign are already added and 2)Total donation does not exceed the limit
 */

import java.util.HashMap;

/**
 * @author kiran
 * This is a utility class that contains functions that 1)execute actions as per the entity type and data,  2)print the final output 
 * Making it a final class because this should not be extended to create child classes; if any new utility function is needed, add it here
 * Making the constructor private because we dont want anyone to instantiate this class, 
 * because all the functions are utilities, and class does not have any local variables, no need to instantiate it, instead call the functions directly
 */

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.gofundme.recurringdonations.entities.Campaign;
import com.gofundme.recurringdonations.entities.Donation;
import com.gofundme.recurringdonations.entities.Donor;
import com.gofundme.recurringdonations.entities.AbstractGfmDonationEntity;

public final class InputEntitiesExecutor {
	// private constructor so that no one will instantiate this utility
	private InputEntitiesExecutor() {
	}

	public static InputEntitiesExecutionResult execute(List<AbstractGfmDonationEntity> abstractGfmDonationEntities) {
		Map<String, Donor> donorDonations = new TreeMap<String, Donor>();
		Map<String, Campaign> campaignDonations = new TreeMap<String, Campaign>();
		for (AbstractGfmDonationEntity abstractGfmDonationEntity : abstractGfmDonationEntities) {
			if (abstractGfmDonationEntity instanceof Donor) {
				Donor donor = (Donor) abstractGfmDonationEntity;
				donorDonations.put(donor.getName(), donor);
			} else if (abstractGfmDonationEntity instanceof Campaign) {
				Campaign campaign = (Campaign) abstractGfmDonationEntity;
				campaignDonations.put(campaign.getName(), campaign);
			} else {
				// this has to be a donation
				Donation donation = (Donation) abstractGfmDonationEntity;
				if (isValidDonorAndCampaign(donation, donorDonations, campaignDonations)) {
					Donor donor = donorDonations.get(donation.getDonorName());
					if (donor.donateIfAllowed(donation.getAmount())) {
						campaignDonations.get(donation.getCampaignName()).acceptDonation(donation.getAmount());
					}
				}
			}
		}
		return new InputEntitiesExecutionResult(donorDonations, campaignDonations);
	}

	public static void printExecutionResult(InputEntitiesExecutionResult executionResult) {
		System.out.println("Donors:");
		for (String donorName : executionResult.getDonorDonations().keySet()) {
			Donor donor = executionResult.getDonorDonations().get(donorName);
			double average = 0;
			if (donor.getNumberOfDonations() != 0) {
				average = donor.getTotalDonation() / donor.getNumberOfDonations(); 
			}
			System.out.println(donorName + ": Total: $" + donor.getTotalDonation() + " Average: $" + average);
		}
		System.out.println("");
		System.out.println("Campaigns:");
		for (String campaignName : executionResult.getCampaignDonations().keySet()) {
			System.out.println(campaignName + ": Total: $"
					+ executionResult.getCampaignDonations().get(campaignName).getTotalDonation());
		}
	}

	private static boolean isValidDonorAndCampaign(Donation donation, Map<String, Donor> donorDonations,
			Map<String, Campaign> campaignDonations) {
		if (donorDonations.containsKey(donation.getDonorName())
				&& campaignDonations.containsKey(donation.getCampaignName())) {
			return true;
		} else {
			return false;
		}
	}

}