package org.santander.marketpricehandler.infrastructure.marketdatafeed;

public class PriceTickParseException extends RuntimeException{

    private String line;
    public PriceTickParseException(String line, String failureReason) {
        super(failureReason);
        this.line = line;
    }

    public String getLine() {
        return line;
    }
}
