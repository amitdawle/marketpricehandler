package org.santander.marketpricehandler.core.api.pricesnapshot;

import java.util.Optional;

public interface PriceLookup {

    Optional<Price> getPrice(String instrumentId);
}
