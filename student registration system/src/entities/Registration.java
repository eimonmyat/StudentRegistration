package entities;

public class Registration {
	private String id;
	private String date;
	private Student student;
	private Schedule schedule;
	private double amount;
	public Registration(String id,String date,Student student,Schedule schedule,double amount) {
		this.id=id;
		this.date=date;
		this.student=student;
		this.schedule=schedule;
		this.amount=amount;
	}
	public Registration() {
		
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student=student;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule=schedule;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount=amount;
	}
}
