package sample.Classes;

public class Vesting {

    int vesting_id;
    String vesting_type;
    int vesting_period;
    float vesting_rate;
    String details;

    public Vesting(int vesting_id, String vesting_type, int vesting_period, float vesting_rate, String details) {
        this.vesting_id = vesting_id;
        this.vesting_type = vesting_type;
        this.vesting_period = vesting_period;
        this.vesting_rate = vesting_rate;
        this.details = details;
    }


    public int getVesting_id() {
        return vesting_id;
    }

    public void setVesting_id(int vesting_id) {
        this.vesting_id = vesting_id;
    }

    public String getVesting_type() {
        return vesting_type;
    }

    public void setVesting_type(String vesting_type) {
        this.vesting_type = vesting_type;
    }

    public int getVesting_period() {
        return vesting_period;
    }

    public void setVesting_period(int vesting_period) {
        this.vesting_period = vesting_period;
    }

    public float getVesting_rate() {
        return vesting_rate;
    }

    public void setVesting_rate(float vesting_rate) {
        this.vesting_rate = vesting_rate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
