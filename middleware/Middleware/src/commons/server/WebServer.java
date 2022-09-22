package commons.server;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final String DISABLED_METHODS = "TRACE,OPTIONS";

    private final Server server;
    private final String serviceName;
    private final InetSocketAddress address;
    private final ServerConnector serverConnector;
    private final ConstraintSecurityHandler securityHandler;
    protected final ServletContextHandler servletContextHandler;

    public WebServer(String serviceName, InetSocketAddress address) {
        this.serviceName = serviceName;
        this.address = address;

        QueuedThreadPool threadPool = new QueuedThreadPool();
        int webThreadCount = 1;

        threadPool.setMinThreads(webThreadCount * 2 + 1);
        threadPool.setMaxThreads(webThreadCount * 2 + 100);

        server = new Server(threadPool);
        serverConnector = new ServerConnector(server);
        serverConnector.setPort(address.getPort());
        serverConnector.setHost(address.getAddress().getHostAddress());
        serverConnector.setReuseAddress(true);

        server.addConnector(serverConnector);

        try {
            serverConnector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

        servletContextHandler = new ServletContextHandler(ServletContextHandler.SECURITY | ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/");

        securityHandler = (ConstraintSecurityHandler) servletContextHandler.getSecurityHandler();
        for (String s : DISABLED_METHODS.split(",")) {
            disableMethod(s);
        }
    }

    public void addHandlers(AbstractHandler handler) {
        HandlerList handlerList = new HandlerList();
        handlerList.addHandler(handler);
        for (Handler h : server.getHandlers()) {
            handlerList.addHandler(h);
        }
        server.setHandler(handlerList);
    }

    public void setHandlers(AbstractHandler handler) {
        server.setHandler(handler);
    }

    private void disableMethod(String method) {
        Constraint constraint = new Constraint();
        constraint.setAuthenticate(true);
        constraint.setName("Disable " + method);
        ConstraintMapping disableMapping = new ConstraintMapping();
        disableMapping.setConstraint(constraint);
        disableMapping.setMethod(method.toUpperCase());
        disableMapping.setPathSpec("/");

        securityHandler.addConstraintMapping(disableMapping);
    }

    public Server getServer() {
        return server;
    }

    public String getBindHost() {
        String bindHost = serverConnector.getHost();
        return bindHost == null ? "0.0.0.0" : bindHost;
    }

    public int getLocalPort() {
        return serverConnector.getLocalPort();
    }

    public void stop() throws Exception {
        for (Connector connector : server.getConnectors()) {
            connector.stop();
        }
        server.stop();
    }

    public void start() {
        try {
            logger.info("{} starting @ {}", serviceName, address);
            server.start();
            logger.info("{} started @ {}", serviceName, address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
