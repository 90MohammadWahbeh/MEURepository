package meu.edu.jo.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activities_conferences")
public class ActivitiesConferences {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "type")
	private Long type;

	@Column(name = "sector")
	private Long sector;

	@Column(name = "activity_description")
	private String activityDescription;

	@Column(name = "number_of_hours")
	private Long numberOfHours;

	@Column(name = "from_date")
	private Date fromDate;

	@Column(name = "to_date")
	private Date toDate;

	@Column(name = "country")
	private Long country;

	@Column(name = "status")
	private Long status;

	@Lob
	@Column(name = "Activity_File", columnDefinition = "bytea")
	private byte[] activityFile;

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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getSector() {
		return sector;
	}

	public void setSector(Long sector) {
		this.sector = sector;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public Long getNumberOfHours() {
		return numberOfHours;
	}

	public void setNumberOfHours(Long numberOfHours) {
		this.numberOfHours = numberOfHours;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public byte[] getActivityFile() {
		return activityFile;
	}

	public void setActivityFile(byte[] activityFile) {
		this.activityFile = activityFile;
	}

}
