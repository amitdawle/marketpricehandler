package org.santander.marketpricehandler.core.api;

import org.santander.marketpricehandler.core.api.feedprocessor.PriceTick;
import org.santander.marketpricehandler.core.api.feedprocessor.PriceTickProcessor;
import org.santander.marketpricehandler.core.api.pricesnapshot.Price;
import org.santander.marketpricehandler.core.api.pricesnapshot.PriceLookup;
import org.santander.marketpricehandler.core.domain.model.PriceQuote;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
/*
The handler and the 'view' for the current (last) prices. Note there is no synchronization
so with multiple threads we will have visibility issues. One option would be to change the map
to a concurrent map or we could have a layer handling locking/thread pinning etc.
 */
public class PriceCache implements PriceTickProcessor, PriceLookup {

    private final Map<String, PriceQuote> instrumentCache = new HashMap<>();
    @Override
    public void process(final PriceTick tick) {
        Objects.requireNonNull(tick);
        instrumentCache.compute(tick.getInstrument(), (ccypair, quote) -> {
            if(quote == null){
               return new PriceQuote(ccypair, tick.getId(), tick.getBid(), tick.getAsk(), tick.getTimeStamp());
            }
            quote.updatePrice(tick.getId(), tick.getBid(), tick.getAsk(), tick.getTimeStamp());
            return quote;

        });
    }

    @Override
    public Optional<Price> getPrice(final String instrumentId) {
        Objects.requireNonNull(instrumentId);
        if(!instrumentCache.containsKey(instrumentId)) {
            return Optional.empty();
        }
        PriceQuote instrument = instrumentCache.get(instrumentId);
        Price price = new Price(instrument.getCcyPair(),
                instrument.getPriceId(), instrument.getBid(), instrument.getAsk(), instrument.getTimeStamp());
        return Optional.of(price);
    }
}
