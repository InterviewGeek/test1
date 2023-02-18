package com.gofundme.recurringdonations.inputparser;

/**
 * @author kiran
 * This is a utility class that will parse the input to create Entities. 
 * Making it a final class because this should not be extended to create child classes; if any new utility function is needed, add it here
 * Making the constructor private because we dont want anyone to instantiate this class, 
 * because all the functions are utilities, and class does not have any local variables, no need to instantiate it, instead call the functions directly
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.gofundme.recurringdonations.entities.EntityType;
import com.gofundme.recurringdonations.entities.AbstractGfmDonationEntity;

public final class InputParser {
	private static final String ADD = "Add";
	private static final String DONOR = "Donor";
	private static final String CAMPAIGN = "Campaign";
	private static final String DONATE = "Donate";

	// private constructor to prevent anyone from instantiating this
	private InputParser() {
	}

	public static List<AbstractGfmDonationEntity> parseInputLinesInOrder(String[] s) throws IOException {
		List<AbstractGfmDonationEntity> abstractGfmDonationEntities = new LinkedList<AbstractGfmDonationEntity>();
		try {
			BufferedReader reader = s.length > 0 ? new BufferedReader(new FileReader(s[0]))
					: new BufferedReader(new InputStreamReader(System.in));
			String line;
			while ((line = reader.readLine()) != null && line.length() > 0) {
				String[] words = line.split("\\s+");
				AbstractGfmDonationEntity abstractGfmDonationEntity = null;
				switch (words[0]) {
				case ADD:
					if (words[1].equals(DONOR)) {
						abstractGfmDonationEntity = new InputEntityBuilder(EntityType.ADD_DONOR).donorName(words[2])
								.limit(Integer.parseInt(words[3].substring(1))).build();
					} else {
						abstractGfmDonationEntity = new InputEntityBuilder(EntityType.ADD_CAMPAIGN)
								.campaignName(words[2]).build();
					}
					break;
				case DONATE:
					abstractGfmDonationEntity = new InputEntityBuilder(EntityType.DONATE).donorName(words[1])
							.campaignName(words[2]).amount(Integer.parseInt(words[3].substring(1))).build();
					break;
				}
				abstractGfmDonationEntities.add(abstractGfmDonationEntity);
			}
			reader.close();
			return abstractGfmDonationEntities;
		} catch (IOException e) {
			throw e;
		}
	}
}