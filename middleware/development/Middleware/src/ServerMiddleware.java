import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.DefaultConfig;

/**
 * Server Middleware
 *
 * @author abarron
 * @version 1
 */
public class ServerMiddleware {

    private static final Logger logger = LoggerFactory.getLogger(ServerMiddleware.class);

    public static void main(String[] args) {

        WebMiddlewareServer middlewareServer = new WebMiddlewareServer(new InetSocketAddress(DefaultConfig.WEB_PORT));
        middlewareServer.start();

        String url = "http://" + DefaultConfig.NODE_URL + ":" + DefaultConfig.WEB_PORT + "/";
        logger.info(url);

    }

}
