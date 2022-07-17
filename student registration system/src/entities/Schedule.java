package entities;

import java.sql.Date;
import java.sql.Time;

public class Schedule {
	private String id;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String totalUser;
	private String course;
	private String classroom;
	private String lecturer;
	private int regUser;
	
	public Schedule(String id,String startTime,String endTime,String startDate,String endDate,
			String totalUser,String course,String classroom,String lecturer) {
		this.id=id;
		this.startDate=startDate;
		this.endDate=endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.totalUser=totalUser;
		this.classroom=classroom;
		this.lecturer=lecturer;
	}
	public Schedule() {
		
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getstartDate() {
		return startDate;
	}
	public void setstartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setendDate(String endDate) {
		this.endDate = endDate;
	}
	public String getendDate() {
		return endDate;
	}

	public void setstartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getstartTime() {
		return startTime;
	}

	public void setendTime(String endTime) {
		this.endTime = endTime;
	}
	public String getendTime() {
		return endTime;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	public String getClassroom() {
		return classroom;
	}
	public void setLecturer(String lecturer) {
		this.lecturer=lecturer;
	}
	public String getLecturer() {
		return lecturer;
	}
	public void setCourse(String course) {
		this.course=course;
	}
	public String getCourse() {
		return course;
	}
	public int getRegisterUser() {
		return regUser;
	}
	public void setRegisterUser(int regUser) {
		this.regUser=regUser;
	}
}