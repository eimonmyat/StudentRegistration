package entities;

import java.sql.Date;
import java.sql.Time;

public class Schedule {
	private String id;
	private Date startDate;
	private Time startTime;
	private Date endDate;
	private Time endTime;
	private String totalUser;
	//private Course course;
	private Classroom classroom;
	private Lecturer lecturer;
	
	public Schedule(String id,Date startDate,Date endDate,Time startTime,Time endTime,
			String totalUser,Classroom classroom,Lecturer lecturer) {
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

	public Date getstartDate() {
		return startDate;
	}
	public void setstartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setendDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getendDate() {
		return endDate;
	}

	public void setstartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getstartTime() {
		return startTime;
	}

	public void setendTime(Time endTime) {
		this.endTime = endTime;
	}
	public Time getendTime() {
		return endTime;
	}

	public void setTotalUser(String totalUser) {
		this.totalUser = totalUser;
	}
	public String gettotalUser() {
		return totalUser;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	public Classroom getClassroom() {
		return classroom;
	}
	public void setLecturer(Lecturer lecturer) {
		this.lecturer=lecturer;
	}
	public Lecturer getLecturer() {
		return lecturer;
	}
}