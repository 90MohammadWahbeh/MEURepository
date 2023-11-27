package meu.edu.jo.entities;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PERSONAL_INFO")
@Data
public class PersonalInfo {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "JOB_NUMBER")
	private Long jobNumber;

	@Column(name = "RANKING")
	private Long ranking;

	@Column(name = "DEPARTMENT")
	private Long department;

	@Column(name = "PROGRAM")
	private Long program;

	@Column(name = "ACTIVITY_PERIOD")
	private Long activityPeriod;

	@Column(name = "ACADEMIC_YEAR")
	private Long academicYear;

	@Lob
	@Column(name = "IMAGE", columnDefinition = "bytea")
	private byte[] image;
	
	private String academicYearDescription;
	private String degreeDescription;
	private String departmentDescription;
	private String programDescription;
	private String activityPeriodDescription;

}
