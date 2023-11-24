package employedatabasesystem;

public class Employee {
    private String name;
    private String id;
    private String address;
    private String phoneNumber;
    private int timeWorked;
    private String[] workSchedule;
    public Employee(String name, String id, String address, String phoneNumber, int timeWorked) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.timeWorked = timeWorked;
        this.workSchedule = new String[7];
    }

    // Getters and setters for all fields
    public String[] getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String[] workSchedule) {
        this.workSchedule = workSchedule;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getTimeWorked() {
        return timeWorked;
    }

    public String getWorkScheduleForDay(int dayIndex) {
        if (dayIndex >= 0 && dayIndex < workSchedule.length) {
            return workSchedule[dayIndex];
        }
        return ""; // Return an empty string or another appropriate value if out of bounds.
    }
    public void setTimeWorked(int timeWorked) {
        this.timeWorked = timeWorked;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", timeWorked=" + timeWorked +
                '}';
    }

	public void setScheduledTime(int newScheduledTime) {
		// TODO Auto-generated method stub
		
	}

	public void setTasks(String newTasks) {
		// TODO Auto-generated method stub
		
	}

	public void setSalary(double newSalary) {
		// TODO Auto-generated method stub
		
	}

	public String getTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	

	public void setWorkScheduleForDay(int i, String newWorkSchedule) {
		// TODO Auto-generated method stub
		
	}
}
