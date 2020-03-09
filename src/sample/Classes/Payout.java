package sample.Classes;

public class Payout {

    int payout_id;
    String payout_name;
    float payout_rate;
    String details;

    public Payout(int payout_id, String payout_name, float payout_rate, String details) {
        this.payout_id = payout_id;
        this.payout_name = payout_name;
        this.payout_rate = payout_rate;
        this.details = details;
    }

    public int getPayout_id() {
        return payout_id;
    }

    public void setPayout_id(int payout_id) {
        this.payout_id = payout_id;
    }

    public String getPayout_name() {
        return payout_name;
    }

    public void setPayout_name(String payout_name) {
        this.payout_name = payout_name;
    }

    public float getPayout_rate() {
        return payout_rate;
    }

    public void setPayout_rate(float payout_rate) {
        this.payout_rate = payout_rate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
