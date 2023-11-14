package meu.edu.jo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.UniversitySociety;
import meu.edu.jo.repositories.UniversitySocietyRepository;

@Service
public class UniversitySocietyService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private UniversitySocietyRepository universitySocietyRepository;

    @Autowired
    public UniversitySocietyService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UniversitySociety> getAllUniversitySocieties() {
        String sql = "SELECT * FROM university_society_local_public_community";
        List<UniversitySociety> universitySocieties = jdbcTemplate.query(sql, (rs, rowNum) -> {
            UniversitySociety universitySociety = new UniversitySociety();
            universitySociety.setId(rs.getLong("Id"));
            universitySociety.setUserId(rs.getLong("User_Id"));
            universitySociety.setTitle(rs.getString("Title"));
            universitySociety.setTheType(rs.getString("The_Type"));
            universitySociety.setTheRole(rs.getString("The_Role"));
            universitySociety.setTheDate(rs.getDate("The_Date"));
            universitySociety.setLocation(rs.getString("Location"));
            universitySociety.setStudentsNumber(rs.getLong("Students_Number"));
            universitySociety.setAcademicStaffNumber(rs.getLong("Academic_Staff_Number"));
            universitySociety.setManagerialStaffNumber(rs.getLong("Managerial_Staff_Number"));
            universitySociety.setNumberOfHours(rs.getLong("Number_of_Hours"));
            universitySociety.setCategory(rs.getString("Category"));
            universitySociety.setDescription(rs.getString("Description"));
            universitySociety.setUniversitySocietyFile(rs.getBytes("UniversitySociety_File"));
            return universitySociety;
        });

        if (universitySocieties.isEmpty()) {
            throw new CustomException(SystemMessages.NO_RECORDS);
        }
        return universitySocieties;
    }

    public Optional<UniversitySociety> getUniversitySocietyById(Long id) {
        String sql = "SELECT * FROM university_society_local_public_community WHERE Id = ?";
        @SuppressWarnings("deprecation")
		List<UniversitySociety> universitySocieties = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            UniversitySociety universitySociety = new UniversitySociety();
            universitySociety.setId(rs.getLong("Id"));
            universitySociety.setUserId(rs.getLong("User_Id"));
            universitySociety.setTitle(rs.getString("Title"));
            universitySociety.setTheType(rs.getString("The_Type"));
            universitySociety.setTheRole(rs.getString("The_Role"));
            universitySociety.setTheDate(rs.getDate("The_Date"));
            universitySociety.setLocation(rs.getString("Location"));
            universitySociety.setStudentsNumber(rs.getLong("Students_Number"));
            universitySociety.setAcademicStaffNumber(rs.getLong("Academic_Staff_Number"));
            universitySociety.setManagerialStaffNumber(rs.getLong("Managerial_Staff_Number"));
            universitySociety.setNumberOfHours(rs.getLong("Number_of_Hours"));
            universitySociety.setCategory(rs.getString("Category"));
            universitySociety.setDescription(rs.getString("Description"));
            universitySociety.setUniversitySocietyFile(rs.getBytes("UniversitySociety_File"));
            return universitySociety;
        });

        if (universitySocieties.isEmpty()) {
            throw new CustomException(SystemMessages.NO_RECORDS + id);
        }

        return Optional.of(universitySocieties.get(0));
    }

    public void deleteUniversitySociety(Long id) {
        String sql = "DELETE FROM university_society_local_public_community WHERE id = ?";
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

    public UniversitySociety createUniversitySociety(UniversitySociety universitySociety) {
        String sql = "INSERT INTO university_society_local_public_community " +
                "(User_Id, Title, The_Type, The_Role, The_Date, Location, Students_Number, " +
                "Academic_Staff_Number, Managerial_Staff_Number, Number_of_Hours, Category, " +
                "Description, UniversitySociety_File) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Set parameters
        Object[] params = {
                universitySociety.getUserId(),
                universitySociety.getTitle(),
                universitySociety.getTheType(),
                universitySociety.getTheRole(),
                universitySociety.getTheDate(),
                universitySociety.getLocation(),
                universitySociety.getStudentsNumber(),
                universitySociety.getAcademicStaffNumber(),
                universitySociety.getManagerialStaffNumber(),
                universitySociety.getNumberOfHours(),
                universitySociety.getCategory(),
                universitySociety.getDescription(),
                universitySociety.getUniversitySocietyFile()
        };

        // Execute the INSERT statement and check the result
        int result = jdbcTemplate.update(sql, params);

        if (result == 1) {
            return universitySociety;
        } else {
            throw new CustomException(SystemMessages.OPERATION_FAILED);
        }
    }

    public UniversitySociety updateUniversitySociety(Long id, UniversitySociety updatedSociety) {
        String updateSql = "UPDATE university_society_local_public_community " +
                "SET User_Id = ?, Title = ?, The_Type = ?, The_Role = ?, The_Date = ?, " +
                "Location = ?, Students_Number = ?, Academic_Staff_Number = ?, " +
                "Managerial_Staff_Number = ?, Number_of_Hours = ?, Category = ?, " +
                "Description = ?, UniversitySociety_File = ? " +
                "WHERE Id = ?";

        // Set parameters
        Object[] params = {
                updatedSociety.getUserId(),
                updatedSociety.getTitle(),
                updatedSociety.getTheType(),
                updatedSociety.getTheRole(),
                updatedSociety.getTheDate(),
                updatedSociety.getLocation(),
                updatedSociety.getStudentsNumber(),
                updatedSociety.getAcademicStaffNumber(),
                updatedSociety.getManagerialStaffNumber(),
                updatedSociety.getNumberOfHours(),
                updatedSociety.getCategory(),
                updatedSociety.getDescription(),
                updatedSociety.getUniversitySocietyFile(),
                id
        };

        // Execute the UPDATE statement and check the result
        int result = jdbcTemplate.update(updateSql, params);

        if (result == 1) {
            // If the update was successful, return the updated UniversitySociety
            return updatedSociety;
        } else {
            throw new CustomException(SystemMessages.OPERATION_FAILED);
        }
    }

    public UniversitySociety saveUniversitySociety(UniversitySociety universitySociety) {
        return universitySocietyRepository.save(universitySociety);
    }
}
