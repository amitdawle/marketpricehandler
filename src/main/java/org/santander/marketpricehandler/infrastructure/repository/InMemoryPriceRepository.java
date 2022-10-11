package org.santander.marketpricehandler.infrastructure.repository;

import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceQuote;
import org.santander.marketpricehandler.core.domain.model.Price;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryPriceRepository implements PriceRepository
{

    private final ConcurrentMap<String, Price> instrumentCache = new ConcurrentHashMap<>();

    @Override
    public Price update(Price newPrice){
       return instrumentCache.put(newPrice.getCcyPair(), newPrice);
    }


    @Override
    public Optional<PriceQuote> get(final String instrumentId) {
        return Optional.ofNullable(instrumentId)
                .map(instrumentCache::get)
                .map(instrument -> new PriceQuote(instrument.getCcyPair(),
                        instrument.getPriceId(), instrument.getBid(), instrument.getAsk(), instrument.getTimeStamp()));
    }

}
