package Employees;

import java.util.Date;

public interface IEmployee {

    String getEmployeeID();

    void setEmployeeID(String employeeID);

    String getProjectID();

    void setProjectID(String projectID);

    Date getDateFrom();

    void setDateFrom(Date dateFrom);

    Date getDateTo();

    void setDateTo(Date dateTo);

}