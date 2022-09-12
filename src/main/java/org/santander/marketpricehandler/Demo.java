package org.santander.marketpricehandler;

import org.santander.marketpricehandler.core.applicationservice.PriceCache;
import org.santander.marketpricehandler.core.applicationservice.api.feedprocessor.PriceTickProcessor;
import org.santander.marketpricehandler.infrastructure.marketdatafeed.MarketPriceFeedHandler;
import org.santander.marketpricehandler.infrastructure.marketdatafeed.csv.CSVPriceTickParser;
import org.santander.marketpricehandler.infrastructure.rest.PriceController;

public class Demo {

    /**
     * Simple (single threaded) example showing how the market price handler service
     * can process the feed and make it available to the clients.
     *
     */

    public static void main(String[] args) {
        String [] feed = new String[] {
                "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001",
                "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002",
                "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002",
                "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100",
                "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110"
        };
        /*
         * All this bootstrapping would be done with spring
         */
        PriceCache priceService  = new PriceCache();
        PriceTickProcessor processor = priceService;
        MarketPriceFeedHandler handler = new MarketPriceFeedHandler(new CSVPriceTickParser(), processor);
        PriceController controller = new PriceController(priceService);

        handleFeed(feed, handler);

        print(controller);

    }

    private static void handleFeed(String[] feed, MarketPriceFeedHandler handler) {
        for(String row: feed){
            handler.onMessage(row);
        }
    }


    private static void print(PriceController controller) {
        System.out.println("EUR/USD price is " +  controller.get("EUR/USD"));
        System.out.println("EUR/JPY price is " + controller.get("EUR/JPY"));
        System.out.println("GBP/USD price is " + controller.get("GBP/USD"));
        System.out.println("GBP/KPW price is " + controller.get("GBP/KPW")
        );
    }

}