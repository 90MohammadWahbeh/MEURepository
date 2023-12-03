package meu.edu.jo.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
			userProfile.setImage(rs.getBytes("Image"));
			userProfile.setAttType(rs.getString("att_type"));
			return userProfile;
		});

		if (userProfiles.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS);
		}

		mapValues(userProfiles);

		return userProfiles;
	}

	public List<PersonalInfo> getUserProfileByUserId(Long userId) {
	    String sql = "SELECT * FROM personal_info WHERE User_Id = ?";
	    
	    @SuppressWarnings("deprecation")
		List<PersonalInfo> userProfiles = jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
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
	        userProfile.setImage(rs.getBytes("Image"));
			userProfile.setAttType(rs.getString("att_type"));
	        return userProfile;
	    });

	    if (userProfiles.isEmpty()) {
	        throw new CustomException(SystemMessages.NO_RECORDS);
	    }

	    mapValues(userProfiles);

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
			profile.setImage(rs.getBytes("Image"));
			profile.setAttType(rs.getString("att_type"));
			return profile;
		});

		if (profiles.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS + id);
		}

		mapValues(profiles);

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
				+ "activity_period, academic_year, image,att_type) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

		// Set parameters
		Object[] params = { userProfile.getUserId(), userProfile.getFullName(), userProfile.getJobNumber(),
				userProfile.getRanking(), userProfile.getDepartment(), userProfile.getProgram(),
				userProfile.getActivityPeriod(), userProfile.getAcademicYear(), userProfile.getImage(),userProfile.getAttType() };

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
				+ "department = ?, program = ?, activity_period = ?, academic_year = ?, image = ?, att_type = ? " + "WHERE id = ?";

		// Set parameters
		Object[] params = { updatedInfo.getUserId(), updatedInfo.getFullName(), updatedInfo.getJobNumber(),
				updatedInfo.getRanking(), updatedInfo.getDepartment(), updatedInfo.getProgram(),
				updatedInfo.getActivityPeriod(), updatedInfo.getAcademicYear(), updatedInfo.getImage(),updatedInfo.getAttType(), id };

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

	private void mapValues(List<PersonalInfo> userProfiles) {
		userProfiles.forEach(profile -> {
			profile.setAcademicYearDescription(mapAcademicYear(profile.getAcademicYear()));
			profile.setDepartmentDescription(mapDepartment(profile.getDepartment()));
			profile.setDegreeDescription(mapDegree(profile.getRanking()));
			profile.setProgramDescription(mapProgram(profile.getProgram()));
			profile.setActivityPeriodDescription(mapActivityPeriod(profile.getActivityPeriod()));
		});
	}

	private String mapAcademicYear(Long academicYearValue) {
		switch (academicYearValue.intValue()) {
		case 1:
			return "2023/2022";
		case 2:
			return "2023/2024";
		case 3:
			return "2025/2024";

		default:
			return null; // Or throw an exception for an unexpected value
		}
	}

	private String mapActivityPeriod(Long activityPeriod) {
		switch (activityPeriod.intValue()) {
		case 1:
			return "الربع الأول (شهر 9 - شهر 11)";
		case 2:
			return "الربع الأول (شهر 12 - شهر 2)";
		case 3:
			return "الربع الأول (شهر 3 - شهر 5)";
		case 4:
			return "الربع الأول (شهر 6 - شهر 8)";

		default:
			return null;
		}
	}

	private String mapProgram(Long program) {
		switch (program.intValue()) {
		case 1:
			return "الإدارة والقيادة التربوية";
		case 2:
			return "المناهج وطرق التدريس";
		case 3:
			return "تكنولوجيا التعليم";
		case 4:
			return "اللغة العربية";
		case 5:
			return "العلوم  الأساسية الإنسانية";
		case 6:
			return "العلوم الأساسية العلمية";

		default:
			return null;
		}
	}
	
	private String mapDegree(Long degree) {
		switch (degree.intValue()) {
		case 1:
			return "أستاذ";
		case 2:
			return "أستاذ مشارك";
		case 3:
			return "أستاذ مساعد";
		case 4:
			return "محاضر";
		default:
			return null;
		}
	}
	
	private String mapDepartment(Long department) {
		switch (department.intValue()) {
		case 1:
			return "الإدارة والمناهج";
		case 2:
			return "تكنولوجيا التعليم";
		case 3:
			return "اللغة الإنجليزيةوآدابها";
		case 4:
			return "اللغة العربية";
		case 5:
			return "العلوم الإنسانية";
		case 6:
			return "العلوم الأساسية العلمية";
		default:
			return null;
		}
	}
	
}
