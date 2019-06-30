package Employees;

import java.util.Date;

public class Employee implements IEmployee {

    private String employeeID;
    private String projectID;
    private Date dateFrom;
    private Date dateTo;

    public Employee(String employeeID, String projectID, Date dateFrom, Date dateTo) {
        this.setEmployeeID(employeeID);
        this.setProjectID(projectID);
        this.setDateFrom(dateFrom);
        this.setDateTo(dateTo);
    }

    @Override
    public String getEmployeeID() {
        return this.employeeID;
    }

    @Override
    public String getProjectID() {
        return this.projectID;
    }

    @Override
    public Date getDateFrom() {
        return this.dateFrom;
    }

    @Override
    public Date getDateTo() {
        return this.dateTo;
    }

    @Override
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    @Override
    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    @Override
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public void setDateTo(Date dateTo) {
        // if date is null then set it to current date
        if (dateTo == null) {
            dateTo = new Date();
        }
        this.dateTo = dateTo;
    }
}