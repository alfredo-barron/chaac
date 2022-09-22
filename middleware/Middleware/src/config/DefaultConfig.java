package config;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Constants of project
 *
 * @author abarron
 * @version 1
 */
public class DefaultConfig {

    public static final Marker accessMarker = MarkerFactory.getMarker("AC");

    public static final String NODE_ID = System.getenv().get("NODE_ID");
    public static final String NODE_URL = System.getenv().get("NODE_URL");
    public static final int WEB_PORT = Integer.parseInt(System.getenv().get("WEB_PORT"));

    public static final String DB_URL = System.getenv().get("DB_URL");
    public static final String LAUNCHER_URL = System.getenv().get("LAUNCHER_URL");

    public static final String VOLUME_LOGS = "/var/log/chaac";

    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    // Constants
    public static final double oneMegabyte = 1e+6;
    public static final double oneGigabyte = 1e+9;
    public static final double NANOSECONDS = 1e+9;

}
