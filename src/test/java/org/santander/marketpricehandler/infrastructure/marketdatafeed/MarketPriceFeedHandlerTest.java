package org.santander.marketpricehandler.infrastructure.marketdatafeed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.santander.marketpricehandler.core.api.feedprocessor.PriceTick;
import org.santander.marketpricehandler.core.api.feedprocessor.PriceTickProcessor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarketPriceFeedHandlerTest {

    @Mock
    private PriceTickProcessor processor;

    @Mock
    private PriceTickParser parser;


    @Test
    void onMessage() throws Exception {
        PriceTick tick = mock(PriceTick.class);
        when(parser.parse(any(String.class))).thenReturn(tick);

        MarketPriceFeedHandler handler = new MarketPriceFeedHandler(parser, processor);

        String feedMessage = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";
        handler.onMessage(feedMessage);

        verify(parser).parse(feedMessage);
        verify(processor).process(tick);

    }

    @Test
    void badMessageNotProcessed() throws Exception {
        PriceTick tick = mock(PriceTick.class);
        when(parser.parse(any(String.class))).thenThrow(PriceTickParseException.class);

        MarketPriceFeedHandler handler = new MarketPriceFeedHandler(parser, processor);

        String feedMessage = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";
        handler.onMessage(feedMessage);

        verify(parser).parse(feedMessage);
        verify(processor, times(0)).process(tick);

    }


}