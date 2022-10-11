package org.santander.marketpricehandler.infrastructure.repository;

import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceQuote;
import org.santander.marketpricehandler.core.domain.model.Price;

import java.util.Optional;

public interface PriceRepository {
    Price update(Price newPrice);

    Optional<PriceQuote> get(String instrumentId);
}
