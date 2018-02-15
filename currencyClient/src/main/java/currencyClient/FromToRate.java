package client;

/**
 * FromToRate
 * Local class to handle API JSon response with Gson.
 */
public class FromToRate {
    private String from;
    private String to;
    private String rate;

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public String getRate() {
        return this.rate;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return String.format("Exchange rate from %s to %s is %s", this.from, this.to, this.rate);
    }
}
