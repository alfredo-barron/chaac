package manager.analysis.ranking;

import java.time.Instant;

public class Interval {

    public static long getInterval(String current, String previous) {
        Instant cur = Instant.parse(current);
        Instant prev = Instant.parse(previous);

        long time = (cur.getEpochSecond() - prev.getEpochSecond()) * 1000;

        return time;
    }

}
