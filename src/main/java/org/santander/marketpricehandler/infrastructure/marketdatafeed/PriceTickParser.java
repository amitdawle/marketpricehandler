package org.santander.marketpricehandler.infrastructure.marketdatafeed;

import org.santander.marketpricehandler.core.api.feedprocessor.PriceTick;

public interface PriceTickParser {
    PriceTick parse(String feedMessage) throws PriceTickParseException;
}
