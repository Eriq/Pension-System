package sample.Classes;

public class Contribution {
    int contribution_id;
    int participant;
    double amount;
    String timestamp;
    String details;

    public Contribution(int contribution_id, int participant, double amount, String timestamp, String details) {
        this.contribution_id = contribution_id;
        this.participant = participant;
        this.amount = amount;
        this.timestamp = timestamp;
        this.details = details;
    }

    public int getContribution_id() {
        return contribution_id;
    }

    public void setContribution_id(int contribution_id) {
        this.contribution_id = contribution_id;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
