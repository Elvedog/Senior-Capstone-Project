package employedatabasesystem;

class TimeWorked {
    private String date;
    private int hoursWorked;

    public TimeWorked(String date, int hoursWorked) {
        this.date = date;
        this.hoursWorked = hoursWorked;
    }

    public String getDate() {
        return date;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }
}
