package org.santander.marketpricehandler.core.domain.model;

/*
Handle the price quote and applies appropriate bid/ask commission
 */
public class PriceQuote {
    public static final double BID_COMMISSION_PERCENTAGE = (0.1 / 100);
    public static final double ASK_COMMISSION_PERCENTAGE = (0.1 / 100);
    private final String ccyPair;
    private long priceId;
    private double bid;
    private double ask;
    private String timeStamp;


    public PriceQuote(final String ccyPair, final long priceId, final double bid, final double ask, String timeStamp) {
        this.ccyPair = ccyPair;
        this.updatePrice(priceId, bid, ask, timeStamp);
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

    public PriceQuote updatePrice(final long priceId, final double bid, final double ask, String timeStamp) {
        // Any specific check e.g. priceId > (current) this.priceId to ensure we are processing only the latest?
       this.priceId = priceId;
       this.bid = applyBidCommission(bid);
       this.ask = applyAskCommission(ask);
       this.timeStamp =  timeStamp;
       return this;
    }

    private Double applyBidCommission(Double bid) {
        if(Double.isNaN(bid))
            return bid;
        return  bid - bid * BID_COMMISSION_PERCENTAGE;
    }

    private Double applyAskCommission(Double ask) {
        if(Double.isNaN(ask))
            return ask;
        return  ask + ask * ASK_COMMISSION_PERCENTAGE;
    }
}
