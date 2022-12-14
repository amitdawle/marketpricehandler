package org.santander.marketpricehandler.core;

import org.junit.jupiter.api.Test;
import org.santander.marketpricehandler.core.applicationservice.PriceService;
import org.santander.marketpricehandler.core.applicationservice.api.feedprocessor.PriceTick;
import org.santander.marketpricehandler.core.applicationservice.api.feedprocessor.PriceTickProcessor;
import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceLookup;
import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceQuote;
import org.santander.marketpricehandler.infrastructure.repository.InMemoryPriceRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceCacheTest {

    PriceService cache = new PriceService(new InMemoryPriceRepository());

    @Test
    public void processesInitialTick() {
        PriceTick tick = new PriceTick(1, "GBP/USD" , 1.15, 1.16, "01-06-2020 12:01:02:110");
        PriceTickProcessor processor = cache;
        processor.process(tick);

        PriceLookup lookup = cache;
        Optional<PriceQuote> price = lookup.getPrice("GBP/USD");

        assertTrue(price.isPresent());
        assertEquals(1, price.get().getPriceId());
        assertEquals("GBP/USD", price.get().getCcyPair());

    }


    @Test
    public void processesUpdates() {
        PriceTick tick = new PriceTick(1, "GBP/USD" , 1.15, 1.16, "01-06-2020 12:01:02:110");
        PriceTick tick2 = new PriceTick(2, "GBP/USD" , 1.14, 1.17, "01-06-2020 12:01:02:510");
        PriceTick tick3 = new PriceTick(3, "GBP/USD" , 1.16, 1.18, "01-06-2020 12:01:02:910");
        PriceTickProcessor processor = cache;
        processor.process(tick);
        processor.process(tick2);
        processor.process(tick3);


        PriceLookup lookup = cache;
        Optional<PriceQuote> price = lookup.getPrice("GBP/USD");

        assertTrue(price.isPresent());
        assertEquals(3, price.get().getPriceId());
        assertEquals("GBP/USD", price.get().getCcyPair());
        assertEquals(1.16 * (1 - 0.001), price.get().getBid(), 0.001);
        assertEquals(1.18 * (1 + 0.001), price.get().getAsk(), 0.001);
        assertEquals("GBP/USD", price.get().getCcyPair());

    }


}