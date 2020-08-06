package ratesapi;

import lombok.Data;

import java.util.Map;

@Data
public class ResponsePOJO {
    private String base;
    private Map<String, Float> rates;
    private String date;
}
