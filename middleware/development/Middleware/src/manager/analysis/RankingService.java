package manager.analysis;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commons.util.Util;
import config.Database;
import config.DefaultConfig;
import manager.analysis.ranking.Ranking;
import manager.model.plataform.Microservice;
import manager.model.plataform.Zone;

@WebServlet("/api/v1/analysis/ranking")
public class RankingService extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(RankingService.class);
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

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
     * Get hosts
     * <p>
     * GET /ranking/
     * GET /ranking/k
     *
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
                out.print("/ranking/k \n");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length != 2) {
            response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
            return;
        }

        int k = Integer.parseInt(splits[1]);
        Collection<Microservice> collection = Database.getAllMicroservices();

        Ranking ranking = new Ranking();
        ranking.compute();

        HashMap<String, Zone> zoneHashMap = ranking.clustering(k, collection);

        response.setStatus(HttpURLConnection.HTTP_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String res = gson.toJson(zoneHashMap.values());

        try {
            out = response.getWriter();
            out.print(res);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
