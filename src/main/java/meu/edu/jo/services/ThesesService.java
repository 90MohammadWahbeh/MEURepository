package meu.edu.jo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Theses;
import meu.edu.jo.repositories.ThesesRepository;

@Service
public class ThesesService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private ThesesRepository thesesRepository;

    @Autowired
    public ThesesService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Theses> getAllTheses() {
        String sql = "SELECT * FROM theses";
        List<Theses> theses = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Theses thesis = new Theses();
            thesis.setId(rs.getLong("Id"));
            thesis.setUserId(rs.getLong("User_Id"));
            thesis.setTheType(rs.getString("The_Type"));
            thesis.setLocation(rs.getString("Location"));
            thesis.setDescription(rs.getString("Description"));
            thesis.setThesesFile(rs.getBytes("Theses_File"));
            return thesis;
        });

        if (theses.isEmpty()) {
            throw new CustomException(SystemMessages.NO_RECORDS);
        }
        return theses;
    }

    public Optional<Theses> getThesesById(Long id) {
        String sql = "SELECT * FROM theses WHERE Id = ?";
        @SuppressWarnings("deprecation")
		List<Theses> theses = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            Theses thesis = new Theses();
            thesis.setId(rs.getLong("Id"));
            thesis.setUserId(rs.getLong("User_Id"));
            thesis.setTheType(rs.getString("The_Type"));
            thesis.setLocation(rs.getString("Location"));
            thesis.setDescription(rs.getString("Description"));
            thesis.setThesesFile(rs.getBytes("Theses_File"));
            return thesis;
        });

        if (theses.isEmpty()) {
            throw new CustomException(SystemMessages.NO_RECORDS + id);
        }

        return Optional.of(theses.get(0));
    }

    public void deleteTheses(Long id) {
        String sql = "DELETE FROM theses WHERE id = ?";
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

    public Theses createTheses(Theses theses) {
        String sql = "INSERT INTO theses " +
                "(User_Id, The_Type, Location, Description, Theses_File) " +
                "VALUES (?, ?, ?, ?, ?)";

        // Set parameters
        Object[] params = {
                theses.getUserId(),
                theses.getTheType(),
                theses.getLocation(),
                theses.getDescription(),
                theses.getThesesFile()
        };

        // Execute the INSERT statement and check the result
        int result = jdbcTemplate.update(sql, params);

        if (result == 1) {
            return theses;
        } else {
            throw new CustomException(SystemMessages.OPERATION_FAILED);
        }
    }

    public Theses updateTheses(Long id, Theses updatedTheses) {
        String updateSql = "UPDATE theses " +
                "SET User_Id = ?, The_Type = ?, Location = ?, Description = ?, Theses_File = ? " +
                "WHERE Id = ?";

        // Set parameters
        Object[] params = {
                updatedTheses.getUserId(),
                updatedTheses.getTheType(),
                updatedTheses.getLocation(),
                updatedTheses.getDescription(),
                updatedTheses.getThesesFile(),
                id
        };

        // Execute the UPDATE statement and check the result
        int result = jdbcTemplate.update(updateSql, params);

        if (result == 1) {
            // If the update was successful, return the updated Theses
            return updatedTheses;
        } else {
            throw new CustomException(SystemMessages.OPERATION_FAILED);
        }
    }

    public Theses saveTheses(Theses theses) {
        return thesesRepository.save(theses);
    }
}