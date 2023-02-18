package com.gofundme.recurringdonations.tests;

/**
 * @author kiran
 * This file is the unit test for InputEntitiesExecutor
 * It contains one happy-path test and other tests to test the edge cases
 * The print method in the InputEntitiesExecutor will calculate the average, this file does not validate the average
 * However it verifies the number of donations and total donation which is used for calculating average
 * Also this file does not test the actual printed output
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.gofundme.recurringdonations.entities.Campaign;
import com.gofundme.recurringdonations.entities.Donation;
import com.gofundme.recurringdonations.entities.Donor;
import com.gofundme.recurringdonations.entities.AbstractGfmDonationEntity;
import com.gofundme.recurringdonations.executors.InputEntitiesExecutionResult;
import com.gofundme.recurringdonations.executors.InputEntitiesExecutor;

class InputLineExecutorTests {

	@Test
	void testWhenUsersAreWithinLimit() {
		List<AbstractGfmDonationEntity> abstractGfmDonationEntities = new LinkedList<AbstractGfmDonationEntity>();
		AbstractGfmDonationEntity donorLine1 = new Donor("Jeanine", 1000);
		abstractGfmDonationEntities.add(donorLine1);
		AbstractGfmDonationEntity donorLine2 = new Donor("Greg", 1000);
		abstractGfmDonationEntities.add(donorLine2);
		AbstractGfmDonationEntity campaign1 = new Campaign("DonateToCancer");
		abstractGfmDonationEntities.add(campaign1);
		AbstractGfmDonationEntity campaign2 = new Campaign("DonateToDogs");
		abstractGfmDonationEntities.add(campaign2);
		AbstractGfmDonationEntity donate1 = new Donation("Jeanine", "DonateToCancer", 400);
		abstractGfmDonationEntities.add(donate1);
		AbstractGfmDonationEntity donate2 = new Donation("Greg", "DonateToCancer", 999);
		abstractGfmDonationEntities.add(donate2);
		AbstractGfmDonationEntity donate3 = new Donation("Jeanine", "DonateToCancer", 450);
		abstractGfmDonationEntities.add(donate3);
		InputEntitiesExecutionResult result = InputEntitiesExecutor.execute(abstractGfmDonationEntities);
		Map<String, Donor> donorDonations = result.getDonorDonations();
		assertTrue(donorDonations.containsKey("Jeanine"));
		assertTrue(donorDonations.containsKey("Greg"));

		Donor jeanineDonationDetails = donorDonations.get("Jeanine");
		assertEquals(jeanineDonationDetails.getTotalDonation(), 850);
		assertEquals(jeanineDonationDetails.getNumberOfDonations(), 2);

		Donor gregDonorDetails = donorDonations.get("Greg");
		assertEquals(gregDonorDetails.getTotalDonation(), 999);
		assertEquals(gregDonorDetails.getNumberOfDonations(), 1);

		// sanity check on print method
		InputEntitiesExecutor.printExecutionResult(result);
	}

	@Test
	void testWhenDonationLimitExceeds() {
		List<AbstractGfmDonationEntity> abstractGfmDonationEntities = new LinkedList<AbstractGfmDonationEntity>();
		AbstractGfmDonationEntity donorLine = new Donor("Jeanine", 1000);
		abstractGfmDonationEntities.add(donorLine);
		AbstractGfmDonationEntity campaig1 = new Campaign("DonateToCancer");
		abstractGfmDonationEntities.add(campaig1);
		AbstractGfmDonationEntity campaign2 = new Campaign("SaveTheDogs");
		abstractGfmDonationEntities.add(campaign2);
		AbstractGfmDonationEntity donate1 = new Donation("Jeanine", "DonateToCancer", 800);
		abstractGfmDonationEntities.add(donate1);
		AbstractGfmDonationEntity donate2 = new Donation("Jeanine", "SaveTheDogs", 400);
		abstractGfmDonationEntities.add(donate2);
		AbstractGfmDonationEntity donate3 = new Donation("Jeanine", "DonateToCancer", 100);
		abstractGfmDonationEntities.add(donate3);
		AbstractGfmDonationEntity donate4 = new Donation("Jeanine", "SaveTheDogs", 100);
		abstractGfmDonationEntities.add(donate4);

		InputEntitiesExecutionResult result = InputEntitiesExecutor.execute(abstractGfmDonationEntities);
		Map<String, Donor> donorDonations = result.getDonorDonations();
		assertTrue(donorDonations.containsKey("Jeanine"));

		Donor jeanineDonationDetails = donorDonations.get("Jeanine");
		assertEquals(jeanineDonationDetails.getTotalDonation(), 1000);
		assertEquals(jeanineDonationDetails.getNumberOfDonations(), 3);

		Map<String, Campaign> campaignDonations = result.getCampaignDonations();
		assertTrue(campaignDonations.containsKey("DonateToCancer"));
		assertTrue(campaignDonations.containsKey("SaveTheDogs"));

		assertEquals(campaignDonations.get("DonateToCancer").getTotalDonation(), 900);
		assertEquals(campaignDonations.get("SaveTheDogs").getTotalDonation(), 100);

		// sanity check on print method
		InputEntitiesExecutor.printExecutionResult(result);
	}

	@Test
	void testWhenCampaignComesAfterDonation() {
		List<AbstractGfmDonationEntity> abstractGfmDonationEntities = new LinkedList<AbstractGfmDonationEntity>();
		AbstractGfmDonationEntity donorLine = new Donor("Jeanine", 1000);
		abstractGfmDonationEntities.add(donorLine);
		AbstractGfmDonationEntity donate1 = new Donation("Jeanine", "DonateToCancer", 800);
		abstractGfmDonationEntities.add(donate1);
		AbstractGfmDonationEntity campaign = new Campaign("DonateToCancer");
		abstractGfmDonationEntities.add(campaign);
		AbstractGfmDonationEntity donate2 = new Donation("Jeanine", "DonateToCancer", 400);
		abstractGfmDonationEntities.add(donate2);
		InputEntitiesExecutionResult result = InputEntitiesExecutor.execute(abstractGfmDonationEntities);
		Map<String, Donor> donorDonations = result.getDonorDonations();
		assertTrue(donorDonations.containsKey("Jeanine"));

		// Only donation2 should show up
		Donor jeanineDonationDetails = donorDonations.get("Jeanine");
		assertEquals(jeanineDonationDetails.getTotalDonation(), 400);
		assertEquals(jeanineDonationDetails.getNumberOfDonations(), 1);

		// sanity check on print method
		InputEntitiesExecutor.printExecutionResult(result);
	}

	@Test
	void testWhenDonorComesAfterDonation() {
		List<AbstractGfmDonationEntity> abstractGfmDonationEntities = new LinkedList<AbstractGfmDonationEntity>();
		AbstractGfmDonationEntity campaign = new Campaign("DonateToCancer");
		abstractGfmDonationEntities.add(campaign);
		AbstractGfmDonationEntity donate1 = new Donation("Jeanine", "DonateToCancer", 800);
		abstractGfmDonationEntities.add(donate1);
		AbstractGfmDonationEntity donorLine = new Donor("Jeanine", 1000);
		abstractGfmDonationEntities.add(donorLine);
		AbstractGfmDonationEntity donate2 = new Donation("Jeanine", "DonateToCancer", 400);
		abstractGfmDonationEntities.add(donate2);

		InputEntitiesExecutionResult result = InputEntitiesExecutor.execute(abstractGfmDonationEntities);
		Map<String, Donor> donorDonations = result.getDonorDonations();
		assertTrue(donorDonations.containsKey("Jeanine"));

		// Only donation2 should show up
		Donor jeanineDonationDetails = donorDonations.get("Jeanine");
		assertEquals(jeanineDonationDetails.getTotalDonation(), 400);
		assertEquals(jeanineDonationDetails.getNumberOfDonations(), 1);

		// sanity check on print method
		InputEntitiesExecutor.printExecutionResult(result);
	}

	@Test
	void testAddDuplicateDonorAndCampaign() {
		List<AbstractGfmDonationEntity> abstractGfmDonationEntities = new LinkedList<AbstractGfmDonationEntity>();
		AbstractGfmDonationEntity campaign1 = new Campaign("DonateToCancer");
		abstractGfmDonationEntities.add(campaign1);
		AbstractGfmDonationEntity campaign2 = new Campaign("DonateToCancer");
		abstractGfmDonationEntities.add(campaign2);
		AbstractGfmDonationEntity donorLine1 = new Donor("Jeanine", 1000);
		abstractGfmDonationEntities.add(donorLine1);
		AbstractGfmDonationEntity donorLine2 = new Donor("Jeanine", 1000);
		abstractGfmDonationEntities.add(donorLine2);
		AbstractGfmDonationEntity donate1 = new Donation("Jeanine", "DonateToCancer", 400);
		abstractGfmDonationEntities.add(donate1);
		AbstractGfmDonationEntity donate2 = new Donation("Jeanine", "DonateToCancer", 450);
		abstractGfmDonationEntities.add(donate2);

		InputEntitiesExecutionResult result = InputEntitiesExecutor.execute(abstractGfmDonationEntities);
		Map<String, Donor> donorDonations = result.getDonorDonations();
		assertTrue(donorDonations.containsKey("Jeanine"));

		Donor jeanineDonationDetails = donorDonations.get("Jeanine");
		assertEquals(jeanineDonationDetails.getTotalDonation(), 850);
		assertEquals(jeanineDonationDetails.getNumberOfDonations(), 2);

		// sanity check on print method
		InputEntitiesExecutor.printExecutionResult(result);
	}
}
