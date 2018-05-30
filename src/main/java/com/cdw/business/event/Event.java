package com.cdw.business.event;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String hostedBy;
	private String description;
	private String eventName;
	private String location;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy'T'HH:mm:ss.SSS")
	private Timestamp eventStart;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy'T'HH:mm:ss.SSS")
	private Timestamp eventEnd;
	
	public Event() {
		
	}

	public Event(String hostedBy, String description, String eventName, String location, Date eventDate,
			Timestamp eventStart, Timestamp eventEnd) {
		super();
		this.hostedBy = hostedBy;
		this.description = description;
		this.eventName = eventName;
		this.location = location;
		this.eventStart = eventStart;
		this.eventEnd = eventEnd;
	}

	public Event(int id, String hostedBy, String description, String eventName, String location, Date eventDate,
			Timestamp eventStart, Timestamp eventEnd) {
		super();
		this.id = id;
		this.hostedBy = hostedBy;
		this.description = description;
		this.eventName = eventName;
		this.location = location;
		this.eventStart = eventStart;
		this.eventEnd = eventEnd;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", hostedBy=" + hostedBy + ", description=" + description + ", eventName="
				+ eventName + ", location=" + location + ", eventStart=" + eventStart
				+ ", eventEnd=" + eventEnd + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHostedBy() {
		return hostedBy;
	}

	public void setHostedBy(String hostedBy) {
		this.hostedBy = hostedBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Timestamp getEventStart() {
		return eventStart;
	}

	public void setEventStart(Timestamp eventStart) {
		this.eventStart = eventStart;
	}

	public Timestamp getEventEnd() {
		return eventEnd;
	}

	public void setEventEnd(Timestamp eventEnd) {
		this.eventEnd = eventEnd;
	}
	
	
}
