package org.santander.marketpricehandler.infrastructure.rest;

import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceQuote;
import org.santander.marketpricehandler.core.applicationservice.api.pricesnapshot.PriceLookup;

import java.util.Objects;
import java.util.Optional;

/*
@RestController
@RequestMapping(path = "/fxprices")
 */
public class PriceController {

    private final PriceLookup service;

    public PriceController(PriceLookup service) {
        this.service = service;
    }

    /*
    public ResponseEntity<Price> get(@PathVariable(value = "instrumentId") String instrumentId)
    */
    public String /*ResponseEntity<Price>*/  get(/*@PathVariable(value = "instrumentId")*/ String instrumentId){
      Optional<PriceQuote> price = service.getPrice(instrumentId);
      /*
      return price.map(p -> ResponseEntity.ok().body(p)).orElseGet( () -> ResponseEntity.notFound().build());
      */
      return price.map(Objects::toString).orElse("");

    }
}

