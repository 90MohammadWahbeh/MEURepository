package meu.edu.jo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.PersonalInfo;
import meu.edu.jo.repositories.PersonalInfoRepository;

@Service
public class PersonalInfoService {
	public final JdbcTemplate jdbcTemplate;

	@Autowired
	PersonalInfoRepository personalInfoRepository;

	@Autowired
	public PersonalInfoService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<PersonalInfo> getAllUserProfile() {
		String sql = "SELECT * FROM personal_info";
		List<PersonalInfo> userProfiles = jdbcTemplate.query(sql, (rs, rowNum) -> {
			PersonalInfo userProfile = new PersonalInfo();
			userProfile.setId(rs.getLong("Id"));
			userProfile.setUserId(rs.getLong("User_Id"));
			userProfile.setFullName(rs.getString("Full_Name"));
			userProfile.setJobNumber(rs.getLong("Job_Number"));
			userProfile.setRanking(rs.getLong("Ranking"));
			userProfile.setDepartment(rs.getLong("Department"));
			userProfile.setProgram(rs.getLong("Program"));
			userProfile.setActivityPeriod(rs.getLong("Activity_Period"));
			userProfile.setAcademicYear(rs.getLong("Academic_Year"));
			return userProfile;
		});

		if (userProfiles.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS);
		}
		return userProfiles;
	}

	public Optional<PersonalInfo> getUserProfileById(Long id) {
		String sql = "SELECT * FROM personal_info WHERE Id = ?";
		@SuppressWarnings("deprecation")
		List<PersonalInfo> profiles = jdbcTemplate.query(sql, new Object[] { id }, (rs, rowNum) -> {
			PersonalInfo profile = new PersonalInfo();
			profile.setId(rs.getLong("Id"));
			profile.setUserId(rs.getLong("User_Id"));
			profile.setFullName(rs.getString("Full_Name"));
			profile.setJobNumber(rs.getLong("Job_Number"));
			profile.setRanking(rs.getLong("Ranking"));
			profile.setDepartment(rs.getLong("Department"));
			profile.setProgram(rs.getLong("Program"));
			profile.setActivityPeriod(rs.getLong("Activity_Period"));
			profile.setAcademicYear(rs.getLong("Academic_Year"));

			return profile;
		});

		if (profiles.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS + id);
		}

		return Optional.of(profiles.get(0));
	}

	public void deleteUserProfile(Long id) {
		String sql = "DELETE FROM personal_info WHERE id = ?";
		Object[] params = { id };

		try {
			int rowsAffected = jdbcTemplate.update(sql, params);

			if (rowsAffected > 0) {
				System.out.println("Record deleted successfully.");
			} else {
				throw new CustomException(SystemMessages.NO_RECORDS + id);
			}
		} catch (DataAccessException e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}

	public PersonalInfo createUserProfile(PersonalInfo userProfile) {
	    String sql = "INSERT INTO personal_info (user_id, FULL_NAME, job_number, ranking, department, program, "
	            + "activity_period, academic_year, image) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    // Set parameters
	    Object[] params = { userProfile.getUserId(), userProfile.getFullName(),
	            userProfile.getJobNumber(), userProfile.getRanking(), userProfile.getDepartment(),
	            userProfile.getProgram(), userProfile.getActivityPeriod(), userProfile.getAcademicYear(),
	            userProfile.getImage() };

	    // Execute the INSERT statement and check the result
	    int result = jdbcTemplate.update(sql, params);

	    if (result == 1) {
	        return userProfile;
	    } else {
	        throw new CustomException(SystemMessages.OPERATION_FAILED);
	    }
	}

	public PersonalInfo updatePersonalInfo(Long id, PersonalInfo updatedInfo) {
	    String updateSql = "UPDATE personal_info SET user_id = ?, FULL_NAME = ?, job_number = ?, ranking = ?, "
	            + "department = ?, program = ?, activity_period = ?, academic_year = ?, image = ? "
	            + "WHERE id = ?";

	    // Set parameters
	    Object[] params = { updatedInfo.getUserId(), updatedInfo.getFullName(),
	            updatedInfo.getJobNumber(), updatedInfo.getRanking(), updatedInfo.getDepartment(),
	            updatedInfo.getProgram(), updatedInfo.getActivityPeriod(), updatedInfo.getAcademicYear(),
	            updatedInfo.getImage(), id };

	    // Execute the UPDATE statement and check the result
	    int result = jdbcTemplate.update(updateSql, params);

	    if (result == 1) {
	        // If the update was successful, return the updated PersonalInfo
	        return updatedInfo;
	    } else {
	        throw new CustomException(SystemMessages.OPERATION_FAILED);
	    }
	}

	public PersonalInfo saveUserProfile(PersonalInfo userProfile) {
		return personalInfoRepository.save(userProfile);
	}

}
