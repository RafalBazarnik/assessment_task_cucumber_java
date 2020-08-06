package ratesapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class ResponsePOJO {
    @JsonProperty("base")
    public String base;

    @JsonProperty("rates")
    public Map<String, Float> rates;

    @JsonProperty("date")
    public String date;

    public ResponsePOJO() {
        // empty constructor
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponsePOJO that = (ResponsePOJO) o;
        return base.equals(that.base) &&
                rates.equals(that.rates) &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, rates, date);
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
