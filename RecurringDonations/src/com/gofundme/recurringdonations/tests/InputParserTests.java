package com.gofundme.recurringdonations.tests;

/**
 * @author kiran
 * This unit test file tests that we read the input data correctly.
 * It does not test if the data is processed correctly after it is read
 * It contain tests to read input from both a file and from cmd line
 * It does not contain any tests to validate the input data itself 
 * because input data is going to be correct according to the description
 */

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.gofundme.recurringdonations.entities.Campaign;
import com.gofundme.recurringdonations.entities.Donation;
import com.gofundme.recurringdonations.entities.Donor;
import com.gofundme.recurringdonations.entities.AbstractGfmDonationEntity;
import com.gofundme.recurringdonations.inputparser.InputParser;

class InputParserTests {

	@Test
	void testParseSimpleInputFile() throws IOException {
		String filePath = "./src/com/gofundme/recurringdonations/tests/input1.txt";
		String[] args = new String[1];
		args[0] = filePath;
		List<AbstractGfmDonationEntity> abstractGfmDonationEntities = InputParser.parseInputLinesInOrder(args);
		assertEquals(abstractGfmDonationEntities.size(), 7);
		Donor donor = (Donor) abstractGfmDonationEntities.get(0);
		assertEquals(donor.getName(), "Greg");
		assertEquals(donor.getLimit(), 1000);
		Campaign campaign = (Campaign) abstractGfmDonationEntities.get(2);
		assertEquals(campaign.getName(), "SaveTheDogs");
		Donation donation = (Donation) abstractGfmDonationEntities.get(4);
		assertEquals(donation.getDonorName(), "Greg");
		assertEquals(donation.getCampaignName(), "SaveTheDogs");
		assertEquals(donation.getAmount(), 100);
	}

	@Test
	void testParseFileWithNamesSameAsCommands() throws IOException {
		String filePath = "./src/com/gofundme/recurringdonations/tests/input2.txt";
		String[] args = new String[1];
		args[0] = filePath;
		List<AbstractGfmDonationEntity> abstractGfmDonationEntities = InputParser.parseInputLinesInOrder(args);
		assertEquals(abstractGfmDonationEntities.size(), 6);
		Donor donor1 = (Donor) abstractGfmDonationEntities.get(0);
		assertEquals(donor1.getName(), "Donor");
		Donor donor2 = (Donor) abstractGfmDonationEntities.get(1);
		assertEquals(donor2.getName(), "Campaign");
		Campaign campaign1 = (Campaign) abstractGfmDonationEntities.get(2);
		assertEquals(campaign1.getName(), "Donor");
		Campaign campaign2 = (Campaign) abstractGfmDonationEntities.get(3);
		assertEquals(campaign2.getName(), "Campaign");
		Donor donor3 = (Donor) abstractGfmDonationEntities.get(4);
		assertEquals(donor3.getName(), "Donate");
		Donation donation = (Donation) abstractGfmDonationEntities.get(5);
		assertEquals(donation.getDonorName(), "Donate");
		assertEquals(donation.getCampaignName(), "Donor");
		assertEquals(donation.getAmount(), 50);
	}

	@Test
	void testInputLinesFromTerminal() throws IOException {
		String input = "Add Donor Greg $1000\nAdd Campaign SaveTheDogs\nAdd Campaign HelpTheKids\nDonate Greg SaveTheDogs $100\n";
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		List<AbstractGfmDonationEntity> abstractGfmDonationEntities = InputParser
				.parseInputLinesInOrder(new String[] { "notxt" });
		assertEquals(abstractGfmDonationEntities.size(), 4);
		Donor donor1 = (Donor) abstractGfmDonationEntities.get(0);
		assertEquals(donor1.getName(), "Greg");
		assertEquals(donor1.getLimit(), 1000);
		Campaign campaign1 = (Campaign) abstractGfmDonationEntities.get(1);
		assertEquals(campaign1.getName(), "SaveTheDogs");
		Campaign campaign2 = (Campaign) abstractGfmDonationEntities.get(2);
		assertEquals(campaign2.getName(), "HelpTheKids");
		Donation donation = (Donation) abstractGfmDonationEntities.get(3);
		assertEquals(donation.getDonorName(), "Greg");
		assertEquals(donation.getCampaignName(), "SaveTheDogs");
		assertEquals(donation.getAmount(), 100);
	}
}
