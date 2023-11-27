package meu.edu.jo.entities;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "university_society_local_public_community")
@Data
public class UniversitySociety {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "the_type")
    private Long theType;

    @Column(name = "the_role")
    private Long theRole;

    @Column(name = "the_date")
    private Date theDate;

    @Column(name = "location")
    private String location;

    @Column(name = "students_number")
    private Long studentsNumber;

    @Column(name = "academic_staff_number")
    private Long academicStaffNumber;

    @Column(name = "managerial_staff_number")
    private Long managerialStaffNumber;

    @Column(name = "number_of_hours")
    private Long numberOfHours;

    @Column(name = "category")
    private Long category;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "universitysociety_file")
    private byte[] universitySocietyFile;
    
	private String activityTypeDescription;
	private String roleDescription;
	private String activityCategoryDescription;

}
