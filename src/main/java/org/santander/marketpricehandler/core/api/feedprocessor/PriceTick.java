package org.santander.marketpricehandler.core.api.feedprocessor;

public class PriceTick {
    private final int id;
    private final String instrument;
    private final double bid;
    private final double ask;
    private final String timeStamp; // todo change

    public PriceTick(final int id, final String instrument, final double bid, final double ask,
                     final String timeStamp) {
        this.id = id;
        this.instrument = instrument;
        this.bid = bid;
        this.ask = ask;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public String getInstrument() {
        return instrument;
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
}
