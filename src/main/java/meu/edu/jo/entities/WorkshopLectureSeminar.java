package meu.edu.jo.entities;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "workshops_lectures_seminars")
@Data
public class WorkshopLectureSeminar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category")
    private Long category;

    @Column(name = "female_number")
    private Long femaleNumber;

    @Column(name = "male_number")
    private Long maleNumber;

    @Column(name = "location")
    private String location;

    @Column(name = "the_date")
    private Date theDate;

    @Column(name = "the_role")
    private Long theRole;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "the_file")
    private byte[] theFile;

	private String theRoleDescription;
	
    @Column(name = "att_type")
    private String attType;

}
