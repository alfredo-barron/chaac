package manager.analysis;

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

@WebServlet("/api/v1/analysis/*")
public class AnalysisService extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisService.class);
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
        String pathInfo = request.getPathInfo();
        logger.info(DefaultConfig.accessMarker, Util.getMessageAccess(request));
        PrintWriter out;

        if (pathInfo == null || pathInfo.equals("/")) {
            try {
                out = response.getWriter();
                String builder = "GET /ranking/ \n" +
                        "GET /ranking/k";
                out.print(builder);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
