package sample.Classes;

public class Scheme {

    int scheme_id;
    String scheme_name;
    float contribution_rate;
    String details;

    public Scheme(int scheme_id, String scheme_name, float contribution_rate, String details) {
        this.scheme_id = scheme_id;
        this.scheme_name = scheme_name;
        this.contribution_rate = contribution_rate;
        this.details = details;
    }

    public int getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(int scheme_id) {
        this.scheme_id = scheme_id;
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public void setScheme_name(String scheme_name) {
        this.scheme_name = scheme_name;
    }

    public float getContribution_rate() {
        return contribution_rate;
    }

    public void setContribution_rate(float contribution_rate) {
        this.contribution_rate = contribution_rate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
