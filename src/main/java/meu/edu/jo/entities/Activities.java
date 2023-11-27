package meu.edu.jo.entities;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "activities")
@Data
public class Activities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "User_Id")
	private Long userId;

	@Column(name = "The_Type")
	private Long theType;
	
	@Column(name = "The_Status")
	private Long theStatus;

	@Column(name = "The_Date")
	private Date theDate;

	@Column(name = "The_Order")
	private Long theOrder;

	@Column(name = "The_Language")
	private Long theLanguage;

	@Column(name = "The_Index")
	private Long theIndex;

	@Column(name = "Title")
	private String title;

	@Column(name = "The_Source")
	private String theSource;

	@Lob
	@Column(name = "Activity_File")
	private byte[] activityFile;

	
	
	private String publishTypeDescription;
	private String publishStatusDescription;
	private String publishLanguageDescription;
	private String publisherOrderDescription;
	private String indexingDescription;
	
	
}
