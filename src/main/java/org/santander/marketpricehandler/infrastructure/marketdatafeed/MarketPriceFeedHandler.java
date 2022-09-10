package org.santander.marketpricehandler.infrastructure.marketdatafeed;

import org.santander.marketpricehandler.core.api.feedprocessor.PriceTick;
import org.santander.marketpricehandler.core.api.feedprocessor.PriceTickProcessor;

import java.util.Objects;


public class MarketPriceFeedHandler {

    private final PriceTickParser parser ;
    private final PriceTickProcessor service;

    public MarketPriceFeedHandler(PriceTickParser parser, PriceTickProcessor service) {
        this.parser = parser;
        this.service = service;
    }

    public void onMessage(String feedMessage)  {
        Objects.requireNonNull(feedMessage);

        try {
            PriceTick tick = parser.parse(feedMessage);
            service.process(tick);
        } catch (Exception e) {
            //log error and then suppress or rethrow?
            //would throw lead to rollback and redelivery ?
        }

    }

}
