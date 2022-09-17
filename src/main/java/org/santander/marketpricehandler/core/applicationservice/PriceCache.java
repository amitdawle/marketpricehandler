package org.santander.marketpricehandler.core.applicationservice;

import org.santander.marketpricehandler.core.applicationservice.api.feedprocessor.PriceTick;
import org.santander.marketpricehandler.core.applicationservice.api.feedprocessor.PriceTickProcessor;
import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceLookup;
import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceQuote;
import org.santander.marketpricehandler.core.domain.model.Price;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
/*
The handler and the 'view' for the current (last) prices. Note there is no synchronization
so with multiple threads we will have visibility issues. One option would be to change the map
to a concurrent map, or we could have a layer handling locking/thread pinning etc.
 */
public class PriceCache implements PriceTickProcessor, PriceLookup {

    private final Map<String, Price> instrumentCache = new HashMap<>();
    @Override
    public void process(final PriceTick tick) {
        Objects.requireNonNull(tick);
        instrumentCache.put(tick.getInstrument(),
               new Price(tick.getInstrument(), tick.getId(), tick.getBid(), tick.getAsk(), tick.getTimeStamp()));

    }

    @Override
    public Optional<PriceQuote> getPrice(final String instrumentId) {
        Objects.requireNonNull(instrumentId);
        if(!instrumentCache.containsKey(instrumentId)) {
            return Optional.empty();
        }
        Price instrument = instrumentCache.get(instrumentId);
        PriceQuote priceQuote = new PriceQuote(instrument.getCcyPair(),
                instrument.getPriceId(), instrument.getBid(), instrument.getAsk(), instrument.getTimeStamp());
        return Optional.of(priceQuote);
    }
}
