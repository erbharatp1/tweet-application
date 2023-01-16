package com.csipl.tms.model.latlonglocation;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the LocationMaster database table.
 * 
 */
@Entity
@NamedQuery(name="LocationMaster.findAll", query="SELECT l FROM LocationMaster l")
public class LocationMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long locationId;

	private Long createdBy;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	private double latitude;

	private String locationAddress;

	private double longitude;

	private double radius;

	//bi-directional many-to-one association to AttendanceLocationMapping
	@OneToMany(mappedBy="locationMaster" , cascade = CascadeType.ALL)
	private List<AttendanceLocationMapping> attendanceLocationMappings;

	public LocationMaster() {
	}

	public Long getLocationId() {
		return this.locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getLocationAddress() {
		return this.locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRadius() {
		return this.radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public List<AttendanceLocationMapping> getAttendanceLocationMappings() {
		return this.attendanceLocationMappings;
	}

	public void setAttendanceLocationMappings(List<AttendanceLocationMapping> attendanceLocationMappings) {
		this.attendanceLocationMappings = attendanceLocationMappings;
	}

	public AttendanceLocationMapping addAttendanceLocationMapping(AttendanceLocationMapping attendanceLocationMapping) {
		getAttendanceLocationMappings().add(attendanceLocationMapping);
		attendanceLocationMapping.setLocationMaster(this);

		return attendanceLocationMapping;
	}

	public AttendanceLocationMapping removeAttendanceLocationMapping(AttendanceLocationMapping attendanceLocationMapping) {
		getAttendanceLocationMappings().remove(attendanceLocationMapping);
		attendanceLocationMapping.setLocationMaster(null);

		return attendanceLocationMapping;
	}

}