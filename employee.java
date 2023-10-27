package employedatabasesystem;

public class Employee {
	
	    private String name;
	    private String id;

	    public Employee(String name, String id) {
	        this.name = name;
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public String getId() {
	        return id;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    @Override
	    public String toString() {
	        return "Name: " + name + ", ID: " + id;
	    }
	}

