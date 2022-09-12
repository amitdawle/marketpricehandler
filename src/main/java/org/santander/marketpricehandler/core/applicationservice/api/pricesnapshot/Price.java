package org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot;

public class Price {
    private final String ccyPair;
    private final long priceId;
    private final double bid;
    private final double ask;
    private final String timeStamp;

    public Price(final String ccyPair, final long priceId, final double bid,
                 final double ask, final String timeStamp) {
        this.ccyPair = ccyPair;
        this.priceId = priceId;
        this.bid = bid;
        this.ask = ask;
        this.timeStamp = timeStamp;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public long getPriceId() {
        return priceId;
    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "Price{" +
                "ccyPair='" + ccyPair + '\'' +
                ", priceId=" + priceId +
                ", bid=" + bid +
                ", ask=" + ask +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
