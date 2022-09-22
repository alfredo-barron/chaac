package log;

import org.slf4j.Marker;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import config.DefaultConfig;

public class MyMarketFilterAccess extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        Marker marker = event.getMarker();
        if (DefaultConfig.accessMarker.equals(marker) || DefaultConfig.accessMarker.contains(marker)) {
            return FilterReply.ACCEPT;
        } else {
            return FilterReply.DENY;
        }
    }

}