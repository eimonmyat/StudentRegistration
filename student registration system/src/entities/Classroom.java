package entities;

public class Classroom {
	private String id;
	private String name;
	private int totalUsers;
	public Classroom(String id,String name) {
		this.id=id;
		this.name=name;
	}
	public Classroom() {
		
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
	
	public int getUsers() {
		return totalUsers;
	}
	public void setUsers(int totalUsers) {
		this.totalUsers=totalUsers;
	}
}