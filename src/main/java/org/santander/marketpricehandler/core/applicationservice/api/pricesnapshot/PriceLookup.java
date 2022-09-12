package org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot;

import java.util.Optional;

public interface PriceLookup {

    Optional<Price> getPrice(String instrumentId);
}
