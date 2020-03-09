package sample.Classes;

import java.sql.Date;

public class Benefit {
    int participant_id;
    String first_name;
    String last_name;
    double pension_benefits;
    String scheme;
    Date retirement_date;

    public Benefit(int participant_id, String first_name, String last_name, double pension_benefits, String scheme, Date retirement_date) {
        this.participant_id = participant_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.pension_benefits = pension_benefits;
        this.scheme = scheme;
        this.retirement_date = retirement_date;
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

    public double getPension_benefits() {
        return pension_benefits;
    }

    public void setPension_benefits(double pension_benefits) {
        this.pension_benefits = pension_benefits;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public Date getRetirement_date() {
        return retirement_date;
    }

    public void setRetirement_date(Date retirement_date) {
        this.retirement_date = retirement_date;
    }
}
