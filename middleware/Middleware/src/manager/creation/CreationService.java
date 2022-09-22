package manager.creation;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commons.util.Util;
import config.DefaultConfig;

@WebServlet("/api/v1/creation/*")
public class CreationService extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CreationService.class);
    private static final long serialVersionUID = 1L;

    /**
     * @param request  [request]
     * @param response [response]
     */
    @Override
    public void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
    }

    /**
     * @param request  [request]
     * @param response [response]
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.info(DefaultConfig.accessMarker, Util.getMessageAccess(request));
        PrintWriter out;
        try {
            out = response.getWriter();
            out.print("Creation");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
