package org.santander.marketpricehandler.infrastructure.marketdatafeed.csv;

import org.junit.jupiter.api.Test;
import org.santander.marketpricehandler.core.api.feedprocessor.PriceTick;
import org.santander.marketpricehandler.infrastructure.marketdatafeed.PriceTickParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVPriceTickParserTest {

    CSVPriceTickParser parser = new CSVPriceTickParser();

    @Test
    public void parseTickInAValidCsvFormat() {
        String input = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";
        PriceTick tick =  parser.parse(input);
        assertEquals(106, tick.getId());
        assertEquals("EUR/USD", tick.getInstrument());
        assertEquals(1.1000, tick.getBid());
        assertEquals(1.2000, tick.getAsk());
    }

    @Test()
    public void parseTickInAnInValidCsvFormat() {
        String input = "106, EUR, 1.1000,1.2000,01-06-2020 12:01:01:001";
        PriceTickParseException exception = assertThrows(PriceTickParseException.class, () -> {
            parser.parse(input);
        });

        assertEquals(input , exception.getLine());
        assertEquals("Invalid ccy pair format.", exception.getMessage());
    }

}