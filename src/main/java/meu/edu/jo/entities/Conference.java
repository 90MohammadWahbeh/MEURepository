package meu.edu.jo.entities;
import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "conference")
@Data
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "location")
    private String location;

    @Column(name = "the_date")
    private Date theDate;

    @Column(name = "the_type")
    private Long theType;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "conference_file")
    private byte[] conferenceFile;
    
    @Column(name = "att_type")
    private String attType;
    
	private String theTypeDescription;
}
