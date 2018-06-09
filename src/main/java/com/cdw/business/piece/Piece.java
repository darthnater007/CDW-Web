package com.cdw.business.piece;

import java.io.File;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.cdw.business.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Piece {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne //is this a many to one or one to many instance
	@JoinColumn(name="UserId")
	private User user;
	@Transient
	private File uploadFile;
	private String title;
	private String genre;
	private String description;
	private String fileName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	private Timestamp submitted;
	@Column(name = "IsPublication")
	private boolean publication;
	
	public Piece() {
		
	}

	public Piece(User user, File uploadFile, String title, String genre, String description, String fileName, Timestamp submitted, boolean publication) {
		super();
		this.user = user;
		this.uploadFile = uploadFile;
		this.title = title;
		this.genre = genre;
		this.description = description;
		this.fileName = fileName;
		this.submitted = submitted;
		this.publication = publication;
	}

	public Piece(int id, User user, File uploadFile, String title, String genre, String fileName, Timestamp submitted,
			boolean publication) {
		super();
		this.id = id;
		this.user = user;
		this.uploadFile = uploadFile;
		this.title = title;
		this.genre = genre;
		this.fileName = fileName;
		this.submitted = submitted;
		this.publication = publication;
	}

	@Override
	public String toString() {
		return "Piece [id=" + id + ", user=" + user + ", title=" + title + ", genre=" + genre + ", fileName=" + fileName
				+ ", submitted=" + submitted + ", publication=" + publication + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public boolean isPublication() {
		return publication;
	}

	public void setPublication(boolean publication) {
		this.publication = publication;
	}
	
	

}
