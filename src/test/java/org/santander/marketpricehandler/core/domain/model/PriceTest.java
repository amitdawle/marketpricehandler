package org.santander.marketpricehandler.core.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PriceTest {

    @Test
    void initialPrice() {
        Price quote = new Price("GBP/USD", 101, 1.13, 1.14, "01-06-2020 12:01:02:110");
        Assertions.assertEquals(1.13d * (1 - 0.001), quote.getBid(), 0.001);
        Assertions.assertEquals(1.14d * (1 + 0.001), quote.getAsk(), 0.001);
    }

    @Test
    void updatePrice() {
        Price quote = new Price("EUR/JPY", 101, 119.60,119.92, "01-06-2020 12:01:02:110");
        Assertions.assertEquals(119.60 * (1 - 0.001), quote.getBid(), 0.001);
        Assertions.assertEquals(119.92 * (1 + 0.001), quote.getAsk(), 0.001);
    }
}