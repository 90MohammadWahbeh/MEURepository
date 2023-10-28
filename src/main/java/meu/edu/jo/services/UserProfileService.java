package meu.edu.jo.services;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.UserProfile;
import meu.edu.jo.repositories.UserProfileRepository;

@Service
public class UserProfileService {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserProfileService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<UserProfile> getAllUserProfile() {
		String sql = "SELECT * FROM user_profile";
		List<UserProfile> userProfiles = jdbcTemplate.query(sql, (rs, rowNum) -> {
			UserProfile userProfile = new UserProfile();
			userProfile.setId(rs.getLong("Id"));
			userProfile.setUserId(rs.getLong("User_Id"));
			userProfile.setArabicName(rs.getString("Arabic_Name"));
			userProfile.setEnglishName(rs.getString("English_Name"));
			userProfile.setImage(rs.getBytes("Image"));
			userProfile.setBirthDate(rs.getDate("Birth_Date"));
			userProfile.setNationality(rs.getString("Nationality"));
			userProfile.setJobNumber(rs.getInt("Job_Number"));
			userProfile.setJobDescription(rs.getString("Job_Description"));
			userProfile.setCollege(rs.getString("College"));
			userProfile.setDepartment(rs.getString("Department"));
			userProfile.setDegree(rs.getString("Degree"));
			userProfile.setDegreeYear(rs.getInt("Degree_Year"));
			userProfile.setHiringDate(rs.getDate("Hiring_date"));
			userProfile.setScientificDegree(rs.getString("Scientific_Degree"));
			userProfile.setGeneralMajor(rs.getString("General_Major"));
			userProfile.setSpecialMajor(rs.getString("Special_Major"));
			userProfile.setKnowledgeSector(rs.getString("Knowledge_Sector"));
			userProfile.setKnowledgeField(rs.getString("Knowledge_Field"));
			userProfile.setYearsOfExperience(rs.getInt("Years_of_experience"));
			userProfile.setUniversityYearsOfExperience(rs.getInt("University_Years_of_experience"));
			userProfile.setScopesName(rs.getString("Scopes_Name"));
			return userProfile;
		});

		if (userProfiles.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS);
		}
		return userProfiles;
	}

	public Optional<UserProfile> getUserProfileById(Long id) {
		String sql = "SELECT * FROM user_profile WHERE Id = ?";
		List<UserProfile> profiles = jdbcTemplate.query(sql, new Object[] { id }, (rs, rowNum) -> {
			UserProfile profile = new UserProfile();
			profile.setId(rs.getLong("Id"));
			profile.setUserId(rs.getLong("User_Id"));
			profile.setArabicName(rs.getString("Arabic_Name"));
			profile.setEnglishName(rs.getString("English_Name"));
			profile.setImage(rs.getBytes("Image"));
			profile.setBirthDate(rs.getDate("Birth_Date"));
			profile.setNationality(rs.getString("Nationality"));
			profile.setJobNumber(rs.getInt("Job_Number"));
			profile.setJobDescription(rs.getString("Job_Description"));
			profile.setCollege(rs.getString("College"));
			profile.setDepartment(rs.getString("Department"));
			profile.setDegree(rs.getString("Degree"));
			profile.setDegreeYear(rs.getInt("Degree_Year"));
			profile.setHiringDate(rs.getDate("Hiring_date"));
			profile.setScientificDegree(rs.getString("Scientific_Degree"));
			profile.setGeneralMajor(rs.getString("General_Major"));
			profile.setSpecialMajor(rs.getString("Special_Major"));
			profile.setKnowledgeSector(rs.getString("Knowledge_Sector"));
			profile.setKnowledgeField(rs.getString("Knowledge_Field"));
			profile.setYearsOfExperience(rs.getInt("Years_of_experience"));
			profile.setUniversityYearsOfExperience(rs.getInt("University_Years_of_experience"));
			profile.setScopesName(rs.getString("Scopes_Name"));
			return profile;
		});

		if (profiles.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS + id);
		}

		return Optional.of(profiles.get(0));
	}

	   public void deleteUserProfile(Long id) {
	        String sql = "DELETE FROM user_profile WHERE id = ?";
	        Object[] params = {id};

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
	public UserProfile createUserProfile(UserProfile userProfile) {
	    String sql = "INSERT INTO user_profile (User_Id, Arabic_Name, English_Name, Image, Birth_Date, Nationality, "
	            + "Job_Number, Job_Description, College, Department, Degree, Degree_Year, Hiring_date, "
	            + "Scientific_Degree, General_Major, Special_Major, Knowledge_Sector, Knowledge_Field, "
	            + "Years_of_experience, University_Years_of_experience, Scopes_Name) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    // Set parameters
	    Object[] params = {userProfile.getUserId(), userProfile.getArabicName(), userProfile.getEnglishName(),
	            userProfile.getImage(), userProfile.getBirthDate(), userProfile.getNationality(),
	            userProfile.getJobNumber(), userProfile.getJobDescription(), userProfile.getCollege(),
	            userProfile.getDepartment(), userProfile.getDegree(), userProfile.getDegreeYear(),
	            userProfile.getHiringDate(), userProfile.getScientificDegree(), userProfile.getGeneralMajor(),
	            userProfile.getSpecialMajor(), userProfile.getKnowledgeSector(), userProfile.getKnowledgeField(),
	            userProfile.getYearsOfExperience(), userProfile.getUniversityYearsOfExperience(),
	            userProfile.getScopesName() };

	    // Execute the INSERT statement and check the result
	    int result = jdbcTemplate.update(sql, params);

	    if (result == 1) {
	        return userProfile;
	    } else {
	        throw new CustomException(SystemMessages.OPERATION_FAILED);
	    }
	}


	public UserProfile updateUserProfile(Long id, UserProfile userProfile) {
	    String updateSql = "UPDATE user_profile SET User_Id = ?, Arabic_Name = ?, English_Name = ?, "
	            + "Image = ?, Birth_Date = ?, Nationality = ?, Job_Number = ?, "
	            + "Job_Description = ?, College = ?, Department = ?, Degree = ?, "
	            + "Degree_Year = ?, Hiring_date = ?, Scientific_Degree = ?, "
	            + "General_Major = ?, Special_Major = ?, Knowledge_Sector = ?, "
	            + "Knowledge_Field = ?, Years_of_experience = ?, University_Years_of_experience = ?, "
	            + "Scopes_Name = ? WHERE Id = ?";

	    int result = jdbcTemplate.update(updateSql, userProfile.getUserId(), userProfile.getArabicName(),
	            userProfile.getEnglishName(), userProfile.getImage(), userProfile.getBirthDate(),
	            userProfile.getNationality(), userProfile.getJobNumber(), userProfile.getJobDescription(),
	            userProfile.getCollege(), userProfile.getDepartment(), userProfile.getDegree(),
	            userProfile.getDegreeYear(), userProfile.getHiringDate(), userProfile.getScientificDegree(),
	            userProfile.getGeneralMajor(), userProfile.getSpecialMajor(), userProfile.getKnowledgeSector(),
	            userProfile.getKnowledgeField(), userProfile.getYearsOfExperience(),
	            userProfile.getUniversityYearsOfExperience(), userProfile.getScopesName(), id);

	    if (result == 1) {
	        // Fetch the updated UserProfile after the update
	        String selectSql = "SELECT * FROM user_profile WHERE Id = ?";
	        return jdbcTemplate.queryForObject(selectSql, new Object[]{id}, (rs, rowNum) -> {
	            UserProfile updatedUserProfile = new UserProfile();
	            updatedUserProfile.setId(rs.getLong("Id"));
	            updatedUserProfile.setUserId(rs.getLong("User_Id"));
	            updatedUserProfile.setArabicName(rs.getString("Arabic_Name"));
	            updatedUserProfile.setEnglishName(rs.getString("English_Name"));
	            updatedUserProfile.setImage(rs.getBytes("Image"));
	            updatedUserProfile.setBirthDate(rs.getDate("Birth_Date"));
	            updatedUserProfile.setNationality(rs.getString("Nationality"));
	            updatedUserProfile.setJobNumber(rs.getInt("Job_Number"));
	            updatedUserProfile.setJobDescription(rs.getString("Job_Description"));
	            updatedUserProfile.setCollege(rs.getString("College"));
	            updatedUserProfile.setDepartment(rs.getString("Department"));
	            updatedUserProfile.setDegree(rs.getString("Degree"));
	            updatedUserProfile.setDegreeYear(rs.getInt("Degree_Year"));
	            updatedUserProfile.setHiringDate(rs.getDate("Hiring_date"));
	            updatedUserProfile.setScientificDegree(rs.getString("Scientific_Degree"));
	            updatedUserProfile.setGeneralMajor(rs.getString("General_Major"));
	            updatedUserProfile.setSpecialMajor(rs.getString("Special_Major"));
	            updatedUserProfile.setKnowledgeSector(rs.getString("Knowledge_Sector"));
	            updatedUserProfile.setKnowledgeField(rs.getString("Knowledge_Field"));
	            updatedUserProfile.setYearsOfExperience(rs.getInt("Years_of_experience"));
	            updatedUserProfile.setUniversityYearsOfExperience(rs.getInt("University_Years_of_experience"));
	            updatedUserProfile.setScopesName(rs.getString("Scopes_Name"));
	            return updatedUserProfile;
	        });
	    } else {
	        throw new CustomException(SystemMessages.OPERATION_FAILED);
	    }
	}


}
