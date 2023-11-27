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
import meu.edu.jo.entities.WorkshopLectureSeminar;
import meu.edu.jo.repositories.WorkshopLectureSeminarRepository;

@Service
public class WorkshopLectureSeminarService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	private WorkshopLectureSeminarRepository workshopRepository;

	@Autowired
	public WorkshopLectureSeminarService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<WorkshopLectureSeminar> getAllWorkshops() {
		String sql = "SELECT * FROM workshops_lectures_seminars";
		List<WorkshopLectureSeminar> workshops = jdbcTemplate.query(sql, (rs, rowNum) -> {
			WorkshopLectureSeminar workshop = new WorkshopLectureSeminar();
			workshop.setId(rs.getLong("Id"));
			workshop.setUserId(rs.getLong("User_Id"));
			workshop.setCategory(rs.getString("Category"));
			workshop.setFemaleNumber(rs.getLong("Female_Number"));
			workshop.setMaleNumber(rs.getLong("Male_Number"));
			workshop.setLocation(rs.getString("Location"));
			workshop.setTheDate(rs.getDate("The_Date"));
			workshop.setTheRole(rs.getLong("The_Role"));
			workshop.setTitle(rs.getString("Title"));
			workshop.setTheFile(rs.getBytes("The_File"));
			return workshop;
		});

		if (workshops.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS);
		}
		mapValues(workshops);
		return workshops;
	}

	public Optional<WorkshopLectureSeminar> getWorkshopById(Long id) {
		String sql = "SELECT * FROM workshops_lectures_seminars WHERE Id = ?";
		@SuppressWarnings("deprecation")
		List<WorkshopLectureSeminar> workshops = jdbcTemplate.query(sql, new Object[] { id }, (rs, rowNum) -> {
			WorkshopLectureSeminar workshop = new WorkshopLectureSeminar();
			workshop.setId(rs.getLong("Id"));
			workshop.setUserId(rs.getLong("User_Id"));
			workshop.setCategory(rs.getString("Category"));
			workshop.setFemaleNumber(rs.getLong("Female_Number"));
			workshop.setMaleNumber(rs.getLong("Male_Number"));
			workshop.setLocation(rs.getString("Location"));
			workshop.setTheDate(rs.getDate("The_Date"));
			workshop.setTheRole(rs.getLong("The_Role"));
			workshop.setTitle(rs.getString("Title"));
			workshop.setTheFile(rs.getBytes("The_File"));
			return workshop;
		});

		if (workshops.isEmpty()) {
			throw new CustomException(SystemMessages.NO_RECORDS + id);
		}
		mapValues(workshops);
		return Optional.of(workshops.get(0));
	}

	public void deleteWorkshop(Long id) {
		String sql = "DELETE FROM workshops_lectures_seminars WHERE id = ?";
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

	public WorkshopLectureSeminar createWorkshop(WorkshopLectureSeminar workshop) {
		String sql = "INSERT INTO workshops_lectures_seminars "
				+ "(User_Id, Category, Female_Number, Male_Number, Location, The_Date, The_Role, Title, The_File) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// Set parameters
		Object[] params = { workshop.getUserId(), workshop.getCategory(), workshop.getFemaleNumber(),
				workshop.getMaleNumber(), workshop.getLocation(), workshop.getTheDate(), workshop.getTheRole(),
				workshop.getTitle(), workshop.getTheFile() };

		// Execute the INSERT statement and check the result
		int result = jdbcTemplate.update(sql, params);

		if (result == 1) {
			return workshop;
		} else {
			throw new CustomException(SystemMessages.OPERATION_FAILED);
		}
	}

	public WorkshopLectureSeminar updateWorkshop(Long id, WorkshopLectureSeminar updatedWorkshop) {
		String updateSql = "UPDATE workshops_lectures_seminars "
				+ "SET User_Id = ?, Category = ?, Female_Number = ?, Male_Number = ?, Location = ?, "
				+ "The_Date = ?, The_Role = ?, Title = ?, The_File = ? " + "WHERE Id = ?";

		// Set parameters
		Object[] params = { updatedWorkshop.getUserId(), updatedWorkshop.getCategory(),
				updatedWorkshop.getFemaleNumber(), updatedWorkshop.getMaleNumber(), updatedWorkshop.getLocation(),
				updatedWorkshop.getTheDate(), updatedWorkshop.getTheRole(), updatedWorkshop.getTitle(),
				updatedWorkshop.getTheFile(), id };

		// Execute the UPDATE statement and check the result
		int result = jdbcTemplate.update(updateSql, params);

		if (result == 1) {
			// If the update was successful, return the updated WorkshopLectureSeminar
			return updatedWorkshop;
		} else {
			throw new CustomException(SystemMessages.OPERATION_FAILED);
		}
	}

	public WorkshopLectureSeminar saveWorkshop(WorkshopLectureSeminar workshop) {
		return workshopRepository.save(workshop);
	}
	
	
	
	private void mapValues(List<WorkshopLectureSeminar> workshopLectureSeminar) {
		workshopLectureSeminar.forEach(conference -> {
			conference.setTheRoleDescription(mapRole(conference.getTheRole()));
		});
	}

	private String mapRole(Long theRole) {
		switch (theRole.intValue()) {
		case 1:
			return "مقدم الندوة * المحاضرة / الورشة";
		case 2:
			return "مشاركة بالحضور فقط";
		default:
			return null; // Or throw an exception for an unexpected value
		}
	}
}
