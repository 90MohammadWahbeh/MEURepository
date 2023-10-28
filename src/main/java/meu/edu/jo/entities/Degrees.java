package meu.edu.jo.entities;

import javax.persistence.*;

@Entity
@Table(name = "degrees")
public class Degrees {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "arabic_title")
	private String arabicTitle;

	@Column(name = "english_title")
	private String englishTitle;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "University")
	private String university;

	@Column(name = "Year")
	private Long year;

	@Column(name = "student_name")
	private String studentName;

	@Column(name = "language")
	private Long language;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArabicTitle() {
		return arabicTitle;
	}

	public void setArabicTitle(String arabicTitle) {
		this.arabicTitle = arabicTitle;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}
