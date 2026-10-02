package com.rinitec.algerieoffice.persistence.modal.company.portfolio;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "events_calendar")
public class EventCalendar implements Serializable {
	private static final long serialVersionUID = -7683742638604622833L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "target_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID eventUUID;
	
	@Column(nullable = false)
	private DateTime eventDate;
	
	@Min(8)
	@Max(24)
	@Column(nullable = false)
	private Integer clockOpen;
	
	@Min(8)
	@Max(24)
	@Column(nullable = false)
	private Integer clockClose;
	
	@Max(48)
	@Column(nullable = false)
	private Integer wilaya;
	
	public EventCalendar() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getEventUUID() {
		return eventUUID;
	}

	public void setEventUUID(UUID eventUUID) {
		this.eventUUID = eventUUID;
	}

	public DateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(DateTime eventDate) {
		this.eventDate = eventDate;
	}

	public Integer getClockOpen() {
		return clockOpen;
	}

	public void setClockOpen(Integer clockOpen) {
		this.clockOpen = clockOpen;
	}

	public Integer getClockClose() {
		return clockClose;
	}

	public void setClockClose(Integer clockClose) {
		this.clockClose = clockClose;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	@Override
	public String toString() {
		return "EventCalendar [id=" + id + ", eventUUID=" + eventUUID + ", eventDate=" + eventDate + ", clockOpen="
				+ clockOpen + ", clockClose=" + clockClose + ", wilaya=" + wilaya + "]";
	}

}
