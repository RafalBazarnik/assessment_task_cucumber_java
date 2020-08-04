package ratesapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ResponsePOJO {
    @JsonProperty("base")
    public String base;

    @JsonProperty("rates")
    public Map<String, Float> rates;

    @JsonProperty("date")
    public String date;

    public ResponsePOJO() {
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ResponsePOJO{" +
                "base='" + base + '\'' +
                ", rates=" + rates +
                ", date='" + date + '\'' +
                '}';
    }
}
