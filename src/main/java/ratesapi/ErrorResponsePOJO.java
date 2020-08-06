package ratesapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ErrorResponsePOJO {
    @JsonProperty("error")
    public String error;

    public ErrorResponsePOJO() {
        // empty constructor
    }

    @JsonProperty("error")
    public String getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponsePOJO that = (ErrorResponsePOJO) o;
        return Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }

    @Override
    public String toString() {
        return "ErrorResponsePOJO{" +
                "error='" + error + '\'' +
                '}';
    }
}
