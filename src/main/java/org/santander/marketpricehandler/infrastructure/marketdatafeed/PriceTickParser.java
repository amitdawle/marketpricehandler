package org.santander.marketpricehandler.infrastructure.marketdatafeed;

import org.santander.marketpricehandler.core.applicationservice.api.feedprocessor.PriceTick;

public interface PriceTickParser {
    PriceTick parse(String feedMessage) throws PriceTickParseException;
}
