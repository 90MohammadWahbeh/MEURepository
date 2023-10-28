package meu.edu.jo.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_profile")
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "arabic_name")
	private String arabicName;

	@Column(name = "english_name")
	private String englishName;

	@Column(name = "scopes_name")
	private String scopesName;

	@Column(name = "birth_date")
	private Date birthDate;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "job_number")
	private Integer jobNumber;

	@Column(name = "job_description")
	private String jobDescription;

	@Column(name = "college")
	private String college;

	@Column(name = "department")
	private String department;

	@Column(name = "degree")
	private String degree;

	@Column(name = "degree_year")
	private Integer degreeYear;

	@Column(name = "hiring_date")
	private Date hiringDate;

	@Column(name = "scientific_degree")
	private String scientificDegree;

	@Column(name = "general_major")
	private String generalMajor;

	@Column(name = "special_major")
	private String specialMajor;

	@Column(name = "knowledge_sector")
	private String knowledgeSector;

	@Column(name = "knowledge_field")
	private String knowledgeField;

	@Column(name = "years_of_experience")
	private Integer yearsOfExperience;

	@Column(name = "university_years_of_experience")
	private Integer universityYearsOfExperience;

	@Lob
	@Column(name = "image", columnDefinition = "bytea")
	private byte[] image;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getArabicName() {
		return arabicName;
	}

	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getScopesName() {
		return scopesName;
	}

	public void setScopesName(String scopesName) {
		this.scopesName = scopesName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Integer getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Integer jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Integer getDegreeYear() {
		return degreeYear;
	}

	public void setDegreeYear(Integer degreeYear) {
		this.degreeYear = degreeYear;
	}

	public Date getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	public String getScientificDegree() {
		return scientificDegree;
	}

	public void setScientificDegree(String scientificDegree) {
		this.scientificDegree = scientificDegree;
	}

	public String getGeneralMajor() {
		return generalMajor;
	}

	public void setGeneralMajor(String generalMajor) {
		this.generalMajor = generalMajor;
	}

	public String getSpecialMajor() {
		return specialMajor;
	}

	public void setSpecialMajor(String specialMajor) {
		this.specialMajor = specialMajor;
	}

	public String getKnowledgeSector() {
		return knowledgeSector;
	}

	public void setKnowledgeSector(String knowledgeSector) {
		this.knowledgeSector = knowledgeSector;
	}

	public String getKnowledgeField() {
		return knowledgeField;
	}

	public void setKnowledgeField(String knowledgeField) {
		this.knowledgeField = knowledgeField;
	}

	public Integer getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public Integer getUniversityYearsOfExperience() {
		return universityYearsOfExperience;
	}

	public void setUniversityYearsOfExperience(Integer universityYearsOfExperience) {
		this.universityYearsOfExperience = universityYearsOfExperience;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	
	
	
}
