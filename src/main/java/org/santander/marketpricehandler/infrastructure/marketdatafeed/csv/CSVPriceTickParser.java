package org.santander.marketpricehandler.infrastructure.marketdatafeed.csv;

import org.santander.marketpricehandler.core.api.feedprocessor.PriceTick;
import org.santander.marketpricehandler.infrastructure.marketdatafeed.PriceTickParseException;
import org.santander.marketpricehandler.infrastructure.marketdatafeed.PriceTickParser;

import java.util.Objects;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class CSVPriceTickParser implements PriceTickParser {

    /*
    Format for the input feed
     106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
     */
    @Override
    public PriceTick parse(String feedMessage) {
        Objects.requireNonNull(feedMessage);

        String[] tickData = feedMessage.split(",");
        if(tickData.length < 5) {
            throw new PriceTickParseException(feedMessage, "Expected format is id,instrument,bid,ask,timestamp");
        }
        String ccyPair = tickData[1].trim();
        if(!validCcyPair(ccyPair)){
            throw new PriceTickParseException(feedMessage, "Invalid ccy pair format.");
        }

        PriceTick tick = new PriceTick(
                        parseInt(tickData[0]),
                        ccyPair,
                        parseDouble(tickData[2]),
                        parseDouble(tickData[3]),
                        tickData[4].trim());
        return tick;
    }



    private boolean validCcyPair(String ccyPair) {
        if(ccyPair.length() != 7 || !ccyPair.contains("/")){
            return false;
        }
        return true;
    }
}
