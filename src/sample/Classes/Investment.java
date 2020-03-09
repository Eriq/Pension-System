package sample.Classes;

public class Investment {

    int investment_id;
    String investment_type;
    float annual_returns;
    String details;

    public Investment(int investment_id, String investment_type, float annual_returns, String details) {
        this.investment_id = investment_id;
        this.investment_type = investment_type;
        this.annual_returns = annual_returns;
        this.details = details;
    }

    public int getInvestment_id() {
        return investment_id;
    }

    public void setInvestment_id(int investment_id) {
        this.investment_id = investment_id;
    }

    public String getInvestment_type() {
        return investment_type;
    }

    public void setInvestment_type(String investment_type) {
        this.investment_type = investment_type;
    }

    public float getAnnual_returns() {
        return annual_returns;
    }

    public void setAnnual_returns(float annual_returns) {
        this.annual_returns = annual_returns;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
