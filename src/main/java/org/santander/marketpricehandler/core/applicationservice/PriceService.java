package org.santander.marketpricehandler.core.applicationservice;

import org.santander.marketpricehandler.core.applicationservice.api.feedprocessor.PriceTick;
import org.santander.marketpricehandler.core.applicationservice.api.feedprocessor.PriceTickProcessor;
import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceLookup;
import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceQuote;
import org.santander.marketpricehandler.core.domain.model.Price;
import org.santander.marketpricehandler.infrastructure.repository.InMemoryPriceRepository;
import org.santander.marketpricehandler.infrastructure.repository.PriceRepository;

import java.util.Objects;
import java.util.Optional;
/*
The handler and the 'view' for the current (last) prices. Note there is no synchronization
so with multiple threads we will have visibility issues. One option would be to change the map
to a concurrent map, or we could have a layer handling locking/thread pinning etc.
 */
public class PriceService implements PriceTickProcessor, PriceLookup {

    private final PriceRepository repository ;

    public PriceService(PriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void process(final PriceTick tick) {

        Objects.requireNonNull(tick);
        repository.update(
               new Price(tick.getInstrument(), tick.getId(), tick.getBid(), tick.getAsk(), tick.getTimeStamp()));

    }

    @Override
    public Optional<PriceQuote> getPrice(final String instrumentId) {
       return repository.get(instrumentId);
    }
}
