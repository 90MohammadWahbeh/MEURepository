package meu.edu.jo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Conference;
import meu.edu.jo.repositories.ConferenceRepository;

@Service
public class ConferenceService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Conference> getAllConferences() {
        String sql = "SELECT * FROM conference";
        List<Conference> conferences = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Conference conference = new Conference();
            conference.setId(rs.getLong("Id"));
            conference.setUserId(rs.getLong("User_Id"));
            conference.setLocation(rs.getString("Location"));
            conference.setTheDate(rs.getDate("The_Date"));
            conference.setTheType(rs.getInt("The_Type"));
            conference.setTitle(rs.getString("Title"));
            conference.setConferenceFile(rs.getBytes("Conference_File"));
            return conference;
        });

        if (conferences.isEmpty()) {
            throw new CustomException(SystemMessages.NO_RECORDS);
        }
        return conferences;
    }

    public Optional<Conference> getConferenceById(Long id) {
        String sql = "SELECT * FROM conference WHERE Id = ?";
        @SuppressWarnings("deprecation")
		List<Conference> conferences = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            Conference conference = new Conference();
            conference.setId(rs.getLong("Id"));
            conference.setUserId(rs.getLong("User_Id"));
            conference.setLocation(rs.getString("Location"));
            conference.setTheDate(rs.getDate("The_Date"));
            conference.setTheType(rs.getInt("The_Type"));
            conference.setTitle(rs.getString("Title"));
            conference.setConferenceFile(rs.getBytes("Conference_File"));
            return conference;
        });

        if (conferences.isEmpty()) {
            throw new CustomException(SystemMessages.NO_RECORDS + id);
        }

        return Optional.of(conferences.get(0));
    }

    public void deleteConference(Long id) {
        String sql = "DELETE FROM conference WHERE id = ?";
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

    public Conference createConference(Conference conference) {
        String sql = "INSERT INTO conference " +
                "(User_Id, Location, The_Date, The_Type, Title, Conference_File) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        // Set parameters
        Object[] params = {
                conference.getUserId(),
                conference.getLocation(),
                conference.getTheDate(),
                conference.getTheType(),
                conference.getTitle(),
                conference.getConferenceFile()
        };

        // Execute the INSERT statement and check the result
        int result = jdbcTemplate.update(sql, params);

        if (result == 1) {
            return conference;
        } else {
            throw new CustomException(SystemMessages.OPERATION_FAILED);
        }
    }

    public Conference updateConference(Long id, Conference updatedConference) {
        String updateSql = "UPDATE conference " +
                "SET User_Id = ?, Location = ?, The_Date = ?, The_Type = ?, Title = ?, Conference_File = ? " +
                "WHERE Id = ?";

        // Set parameters
        Object[] params = {
                updatedConference.getUserId(),
                updatedConference.getLocation(),
                updatedConference.getTheDate(),
                updatedConference.getTheType(),
                updatedConference.getTitle(),
                updatedConference.getConferenceFile(),
                id
        };

        // Execute the UPDATE statement and check the result
        int result = jdbcTemplate.update(updateSql, params);

        if (result == 1) {
            // If the update was successful, return the updated Conference
            return updatedConference;
        } else {
            throw new CustomException(SystemMessages.OPERATION_FAILED);
        }
    }

    public Conference saveConference(Conference conference) {
        return conferenceRepository.save(conference);
    }
}
