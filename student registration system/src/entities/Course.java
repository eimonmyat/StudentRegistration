package entities;

public class Course {
	private String id;
	private String name;
	private double fee;
	private int duration;
	private Category category;
	//private int stuNo;
	public Course(String id,String name,double fee,Category category,int duration) {
		this.id=id;
		this.name=name;
		this.fee=fee;
		this.category=category;
		this.duration=duration;
	}
	public Course() {
		
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee=fee;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category=category;
	}
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration=duration;
	}
	
}