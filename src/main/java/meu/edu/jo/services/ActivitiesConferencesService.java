package meu.edu.jo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.ActivitiesConferences;
import meu.edu.jo.entities.UserProfile;
import meu.edu.jo.repositories.ActivitiesConferencesRepository;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ActivitiesConferencesService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ActivitiesConferencesService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	ActivitiesConferencesRepository activitiesConferencesRepository;

	public List<ActivitiesConferences> getAllActivitiesConferences() {
		String sql = "SELECT * FROM activities_conferences";
		List<ActivitiesConferences> activitiesConferences = jdbcTemplate.query(sql, (rs, rowNum) -> {
			ActivitiesConferences activitiesConference = new ActivitiesConferences();
			activitiesConference.setId(rs.getLong("Id"));
			activitiesConference.setUserId(rs.getLong("User_Id"));
			activitiesConference.setSector(rs.getLong("Sector"));
			activitiesConference.setActivityDescription(rs.getString("activity_description"));
			activitiesConference.setNumberOfHours(rs.getLong("Number_Of_Hours"));
			activitiesConference.setFromDate(rs.getDate("From_Date"));
			activitiesConference.setToDate(rs.getDate("To_Date"));
			activitiesConference.setCountry(rs.getLong("Country"));
			activitiesConference.setStatus(rs.getLong("Status"));
			activitiesConference.setActivityFile(rs.getBytes("Activity_File"));
			return activitiesConference;
		});

		if (activitiesConferences.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS);
		}
		return activitiesConferences;
	}

	public Optional<ActivitiesConferences> getActivitiesConferencesById(Long id) {
		String sql = "SELECT * FROM activities_conferences WHERE Id = ?";
		List<ActivitiesConferences> activitiesConferences = jdbcTemplate.query(sql, new Object[] { id },
				(rs, rowNum) -> {
					ActivitiesConferences activitiesConference = new ActivitiesConferences();
					activitiesConference.setId(rs.getLong("Id"));
					activitiesConference.setUserId(rs.getLong("User_Id"));
					activitiesConference.setSector(rs.getLong("Sector"));
					activitiesConference.setActivityDescription(rs.getString("activity_description"));
					activitiesConference.setNumberOfHours(rs.getLong("Number_Of_Hours"));
					activitiesConference.setFromDate(rs.getDate("From_Date"));
					activitiesConference.setToDate(rs.getDate("To_Date"));
					activitiesConference.setCountry(rs.getLong("Country"));
					activitiesConference.setStatus(rs.getLong("Status"));
					activitiesConference.setActivityFile(rs.getBytes("Activity_File"));
					return activitiesConference;
				});

		if (activitiesConferences.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS + id);
		}

		return Optional.of(activitiesConferences.get(0));
	}

	public ActivitiesConferences createActivityConference(ActivitiesConferences activityConference) {
		String sql = "INSERT INTO activities_conferences "
				+ "(user_id, type, sector, activity_description, number_of_hours, from_date, to_date, "
				+ "country, status, activity_file) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// Set parameters
		Object[] params = { activityConference.getUserId(), activityConference.getType(),
				activityConference.getSector(), activityConference.getActivityDescription(),
				activityConference.getNumberOfHours(), activityConference.getFromDate(), activityConference.getToDate(),
				activityConference.getCountry(), activityConference.getStatus(), activityConference.getActivityFile() };

		// Execute the INSERT statement and check the result
		int result = jdbcTemplate.update(sql, params);

		if (result == 1) {
			return activityConference;
		} else {
			throw new CustomException(SystemMessages.OPERATION_FAILED);
		}
	}

	public ActivitiesConferences updateActivityConference(Long id, ActivitiesConferences activityConference) {
		String sql = "UPDATE activities_conferences "
				+ "SET user_id = ?, type = ?, sector = ?, activity_description = ?, "
				+ "number_of_hours = ?, from_date = ?, to_date = ?, country = ?, status = ?, " + "activity_file = ? "
				+ "WHERE id = ?";

		// Set parameters
		Object[] params = { activityConference.getUserId(), activityConference.getType(),
				activityConference.getSector(), activityConference.getActivityDescription(),
				activityConference.getNumberOfHours(), activityConference.getFromDate(), activityConference.getToDate(),
				activityConference.getCountry(), activityConference.getStatus(), activityConference.getActivityFile(),
				id };

		// Execute the UPDATE statement and check the result
		int result = jdbcTemplate.update(sql, params);

		if (result == 1) {
			return activityConference;
		} else {
			throw new CustomException(SystemMessages.OPERATION_FAILED);
		}
	}

	public void deleteActivitiesConferences(Long id) {
		String sql = "DELETE FROM activities_conferences WHERE id = ?";
		Object[] params = { id };

		try {
			int rowsAffected = jdbcTemplate.update(sql, params);

			if (rowsAffected > 0) {
				System.out.println(SystemMessages.DELETE_MSG);
			} else {
				throw new CustomException(SystemMessages.NO_RECORDS + id);
			}
		} catch (DataAccessException e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}
}
