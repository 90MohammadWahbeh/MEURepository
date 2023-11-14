package meu.edu.jo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Achievements;
import meu.edu.jo.repositories.AchievementsRepository;

@Service
public class AchievementsService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private AchievementsRepository achievementsRepository;

    @Autowired
    public AchievementsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Achievements> getAllAchievements() {
        String sql = "SELECT * FROM achievements";
        List<Achievements> achievements = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Achievements achievement = new Achievements();
            achievement.setId(rs.getLong("Id"));
            achievement.setUserId(rs.getLong("User_Id"));
            achievement.setDescription(rs.getString("Description"));
            achievement.setAchievementsFile(rs.getBytes("Achievements_File"));
            return achievement;
        });

        if (achievements.isEmpty()) {
            throw new CustomException(SystemMessages.NO_RECORDS);
        }
        return achievements;
    }

    public Optional<Achievements> getAchievementsById(Long id) {
        String sql = "SELECT * FROM achievements WHERE Id = ?";
        @SuppressWarnings("deprecation")
		List<Achievements> achievements = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            Achievements achievement = new Achievements();
            achievement.setId(rs.getLong("Id"));
            achievement.setUserId(rs.getLong("User_Id"));
            achievement.setDescription(rs.getString("Description"));
            achievement.setAchievementsFile(rs.getBytes("Achievements_File"));
            return achievement;
        });

        if (achievements.isEmpty()) {
            throw new CustomException(SystemMessages.NO_RECORDS + id);
        }

        return Optional.of(achievements.get(0));
    }

    public void deleteAchievements(Long id) {
        String sql = "DELETE FROM achievements WHERE id = ?";
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

    public Achievements createAchievements(Achievements achievements) {
        String sql = "INSERT INTO achievements " +
                "(User_Id, Description, Achievements_File) " +
                "VALUES (?, ?, ?)";

        // Set parameters
        Object[] params = {
                achievements.getUserId(),
                achievements.getDescription(),
                achievements.getAchievementsFile()
        };

        // Execute the INSERT statement and check the result
        int result = jdbcTemplate.update(sql, params);

        if (result == 1) {
            return achievements;
        } else {
            throw new CustomException(SystemMessages.OPERATION_FAILED);
        }
    }

    public Achievements updateAchievements(Long id, Achievements updatedAchievements) {
        String updateSql = "UPDATE achievements " +
                "SET User_Id = ?, Description = ?, Achievements_File = ? " +
                "WHERE Id = ?";

        // Set parameters
        Object[] params = {
                updatedAchievements.getUserId(),
                updatedAchievements.getDescription(),
                updatedAchievements.getAchievementsFile(),
                id
        };

        // Execute the UPDATE statement and check the result
        int result = jdbcTemplate.update(updateSql, params);

        if (result == 1) {
            // If the update was successful, return the updated Achievements
            return updatedAchievements;
        } else {
            throw new CustomException(SystemMessages.OPERATION_FAILED);
        }
    }

    public Achievements saveAchievements(Achievements achievements) {
        return achievementsRepository.save(achievements);
    }
}
