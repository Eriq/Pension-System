package sample.Classes;

import java.sql.Date;

public class Participant {
    int participant_id;
    String first_name;
    String last_name;
    Date date_of_birth;
    double annual_salary;
    Date employment_date;
    Date retirement_date;
    int scheme;
    int vesting;
    int investment;
    int payout;
    int details;

    public Participant(int participant_id, String first_name, String last_name, Date date_of_birth, double annual_salary, Date employment_date, Date retirement_date, int scheme, int vesting, int investment, int details) {
        this.participant_id = participant_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.annual_salary = annual_salary;
        this.employment_date = employment_date;
        this.retirement_date = retirement_date;
        this.scheme = scheme;
        this.vesting = vesting;
        this.investment = investment;
        this.details = details;
    }

    public int getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(int participant_id) {
        this.participant_id = participant_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public double getAnnual_salary() {
        return annual_salary;
    }

    public void setAnnual_salary(double annual_salary) {
        this.annual_salary = annual_salary;
    }

    public Date getEmployment_date() {
        return employment_date;
    }

    public void setEmployment_date(Date employment_date) {
        this.employment_date = employment_date;
    }

    public Date getRetirement_date() {
        return retirement_date;
    }

    public void setRetirement_date(Date retirement_date) {
        this.retirement_date = retirement_date;
    }

    public int getScheme() {
        return scheme;
    }

    public void setScheme(int scheme) {
        this.scheme = scheme;
    }

    public int getVesting() {
        return vesting;
    }

    public void setVesting(int vesting) {
        this.vesting = vesting;
    }

    public int getInvestment() {
        return investment;
    }

    public void setInvestment(int investment) {
        this.investment = investment;
    }

    public int getPayout() {
        return payout;
    }

    public void setPayout(int payout) {
        this.payout = payout;
    }

    public int getDetails() {
        return details;
    }

    public void setDetails(int details) {
        this.details = details;
    }
}
