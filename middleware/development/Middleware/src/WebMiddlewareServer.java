import java.net.InetSocketAddress;

import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import commons.server.WebServer;
import manager.acquisition.AcquisitionService;
import manager.acquisition.AttributeService;
import manager.acquisition.HostService;
import manager.analysis.AnalysisService;
import manager.analysis.RankingService;
import manager.creation.CreationService;
import manager.creation.BallService;
import manager.creation.BinService;
import manager.creation.PoolService;

/**
 * Server Data Middleware
 *
 * @author abarron
 * @version 1
 */
public class WebMiddlewareServer extends WebServer {

    public WebMiddlewareServer(InetSocketAddress address) {
        super("Middleware Service", address);
        ServletHandler servletHandler = new ServletHandler();
        setHandlers(servletHandler);

        // Api Methodology
        // Acquisition
        servletHandler.addServletWithMapping(AcquisitionService.class, "/api/v1/acquisition/*");
        servletHandler.addServletWithMapping(HostService.class, "/api/v1/acquisition/hosts/*");
        servletHandler.addServletWithMapping(AttributeService.class, "/api/v1/acquisition/attributes/*");
        // Analysis
        servletHandler.addServletWithMapping(AnalysisService.class, "/api/v1/analysis/*");
        servletHandler.addServletWithMapping(RankingService.class, "/api/v1/analysis/ranking/*");
        // Creation
        servletHandler.addServletWithMapping(CreationService.class, "/api/v1/creation/*");
        servletHandler.addServletWithMapping(PoolService.class, "/api/v1/creation/pools/*");
        servletHandler.addServletWithMapping(BinService.class, "/api/v1/creation/bins/*");
        servletHandler.addServletWithMapping(BallService.class, "/api/v1/balls/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(System.getProperty("user.dir") + "/web");
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setCacheControl("no-store,no-cache,must-revalidate");

        addHandlers(resourceHandler);

    }

}
