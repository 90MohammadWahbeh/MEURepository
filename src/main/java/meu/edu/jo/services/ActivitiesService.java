package meu.edu.jo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Activities;
import meu.edu.jo.repositories.ActivitiesRepository;

@Service
public class ActivitiesService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ActivitiesService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	ActivitiesRepository activitiesConferencesRepository;

	public List<Activities> getAllActivities() {
		String sql = "SELECT * FROM activities";
		List<Activities> activitiesConferences = jdbcTemplate.query(sql, (rs, rowNum) -> {
			Activities activitiesConference = new Activities();
			activitiesConference.setId(rs.getLong("Id"));
			activitiesConference.setUserId(rs.getLong("User_Id"));
			activitiesConference.setTheType(rs.getLong("The_Type"));
			activitiesConference.setTheStatus(rs.getLong("The_Status"));
			activitiesConference.setTheDate(rs.getDate("The_Date"));
			activitiesConference.setTheOrder(rs.getLong("The_Order"));
			activitiesConference.setTheLanguage(rs.getLong("The_Language"));
			activitiesConference.setTheIndex(rs.getLong("The_Index"));
			activitiesConference.setTitle(rs.getString("Title"));
			activitiesConference.setTheSource(rs.getString("The_Source"));
			activitiesConference.setActivityFile(rs.getBytes("Activity_File"));
			return activitiesConference;
		});

		if (activitiesConferences.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS);
		}
		return activitiesConferences;
	}

	public Optional<Activities> getActivitiesById(Long id) {
		String sql = "SELECT * FROM activities WHERE Id = ?";
		@SuppressWarnings("deprecation")
		List<Activities> activities = jdbcTemplate.query(sql, new Object[] { id }, (rs, rowNum) -> {
			Activities activity = new Activities();
			activity.setId(rs.getLong("Id"));
			activity.setUserId(rs.getLong("User_Id"));
			activity.setTheType(rs.getLong("The_Type"));
			activity.setTheStatus(rs.getLong("The_Status"));
			activity.setTheDate(rs.getDate("The_Date"));
			activity.setTheOrder(rs.getLong("The_Order"));
			activity.setTheLanguage(rs.getLong("The_Language"));
			activity.setTheIndex(rs.getLong("The_Index"));
			activity.setTitle(rs.getString("Title"));
			activity.setTheSource(rs.getString("The_Source"));
			activity.setActivityFile(rs.getBytes("Activity_File"));
			return activity;
		});

		if (activities.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS + id);
		}

		return Optional.of(activities.get(0));
	}

	public Activities createActivity(Activities activity) {
		String sql = "INSERT INTO activities " + "(User_Id, The_Type, The_Status, The_Date, The_Order, The_Language, "
				+ "The_Index, Title, The_Source, Activity_File) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// Set parameters
		Object[] params = { activity.getUserId(), activity.getTheType(), // Updated attribute name
				activity.getTheStatus(), // Updated attribute name
				activity.getTheDate(), activity.getTheOrder(), // Updated attribute name
				activity.getTheLanguage(), // Updated attribute name
				activity.getTheIndex(), // Updated attribute name
				activity.getTitle(), activity.getTheSource(), // Updated attribute name
				activity.getActivityFile() };

		// Execute the INSERT statement and check the result
		int result = jdbcTemplate.update(sql, params);

		if (result == 1) {
			return activity;
		} else {
			throw new CustomException(SystemMessages.OPERATION_FAILED);
		}
	}

	public Activities updateActivity(Long id, Activities activity) {
		String sql = "UPDATE activities " + "SET User_Id = ?, The_Type = ?, The_Status = ?, The_Date = ?, "
				+ "The_Order = ?, The_Language = ?, The_Index = ?, " + "Title = ?, The_Source = ?, Activity_File = ? "
				+ "WHERE Id = ?";

		// Set parameters
		Object[] params = { activity.getUserId(), activity.getTheType(), // Updated attribute name
				activity.getTheStatus(), // Updated attribute name
				activity.getTheDate(), activity.getTheOrder(), // Updated attribute name
				activity.getTheLanguage(), // Updated attribute name
				activity.getTheIndex(), // Updated attribute name
				activity.getTitle(), activity.getTheSource(), // Updated attribute name
				activity.getActivityFile(), id };

		// Execute the UPDATE statement and check the result
		int result = jdbcTemplate.update(sql, params);

		if (result == 1) {
			return activity;
		} else {
			throw new CustomException(SystemMessages.OPERATION_FAILED);
		}
	}

	public void deleteActivities(Long id) {
		String sql = "DELETE FROM activities WHERE id = ?";
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
