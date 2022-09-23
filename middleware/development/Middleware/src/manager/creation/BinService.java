package manager.creation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import commons.client.Rest;
import commons.util.Util;
import config.Database;
import config.DefaultConfig;
import manager.model.components.Bin;
import manager.model.components.Pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Api;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Collection;

/**
 * API Rest of Bins (DataContainers)
 *
 * @author abarron
 * @version 1
 */
@WebServlet("/api/v1/creation/bins/*")
public class BinService extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BinService.class);
    private static final long serialVersionUID = 1L;
    private final Api api = new Api();
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
     * Get bins
     * GET /bins/
     * GET /bins/id
     *
     * @param request  [request]
     * @param response [response]
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        logger.info(DefaultConfig.accessMarker, Util.getMessageAccess(request));
        String id;
        String option;
        String containerId;
        PrintWriter out;

        if (pathInfo == null || pathInfo.equals("/")) {
            Collection<Bin> collection = Database.getAllBins();
            response.setStatus(HttpURLConnection.HTTP_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String res = gson.toJson(collection);

            try {
                out = response.getWriter();
                out.print(res);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        String[] splits = pathInfo.split("/");

        switch (splits.length) {
            case 2:

                id = splits[1];

                if (id.equals("delete")) {
                    for (Bin bin : Database.getAllBins()) {
                        String url = bin.getUrl() + "api/v1/stats/delete";
                        new Rest(url).get();
                    }
                    try {
                        response.setStatus(HttpURLConnection.HTTP_OK);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");

                        JsonObject json = new JsonObject();
                        json.addProperty("message", "Deleted files of data containers");

                        out = response.getWriter();
                        out.print(json);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }

                if (Database.existsBin(id)) {
                    response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                    return;
                }

                response.setStatus(HttpURLConnection.HTTP_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String res = gson.toJson(Database.getBin(id));

                try {
                    out = response.getWriter();
                    out.print(res);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    logger.debug(e.toString());
                }

                break;

            case 3:

                id = splits[1];
                option = splits[2];

                if (option.equals("create")) {

                    Bin bin = Database.getBin(id);
                    containerId = api.createContainer(bin.getContainer());
                    bin.getContainer().setId(containerId);

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Create container " + bin.getId());

                    try {
                        out = response.getWriter();
                        out.print(json);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }

                if (option.equals("start")) {

                    Bin bin = Database.getBin(id);
                    api.startContainer(bin.getContainer().getId());
                    bin.setStatus(true);

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Start bin " + bin.getId());

                    try {
                        out = response.getWriter();
                        out.print(json);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }

                if (option.equals("restart")) {

                    Bin bin = Database.getBin(id);
                    bin.setStatus(false);
                    api.restartContainer(bin.getContainer().getId());

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Restart bin " + bin.getId());

                    try {
                        out = response.getWriter();
                        out.print(json);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }

                if (option.equals("stop")) {

                    Bin bin = Database.getBin(id);
                    api.stopContainer(bin.getContainer().getId());
                    bin.setStatus(false);

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Stop container " + bin.getId());

                    try {
                        out = response.getWriter();
                        out.print(json);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }

                if (option.equals("remove")) {

                    Bin bin = Database.getBin(id);
                    api.removeContainer(bin.getContainer().getId());
                    bin.setStatus(false);

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Remove bin " + bin.getId());

                    try {
                        out = response.getWriter();
                        out.print(json);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }

                break;

            default:
                response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                break;
        }

    }

    /**
     * Add new bin is DB
     * POST /bins/
     *
     * @param request  [request]
     * @param response [response]
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        logger.info(DefaultConfig.accessMarker, Util.getMessageAccess(request));

        if (pathInfo == null || pathInfo.equals("/")) {

            StringBuilder builder = new StringBuilder();
            BufferedReader reader;
            try {
                reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String payload = builder.toString();

            Bin bin = gson.fromJson(payload, Bin.class);
            logger.debug(bin.toString());

            long timeStart, timeEnd, timeTotal = 0;
            Database.createBin(bin);
            timeStart = System.nanoTime();
            String containerId = api.createContainer(bin.getContainer());
            timeEnd = System.nanoTime();
            timeTotal = timeEnd - timeStart;
            bin.getContainer().setId(containerId);

            logger.info("Deploy " + bin.getId() + " time: " + timeTotal);

            response.setStatus(HttpURLConnection.HTTP_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JsonObject json = new JsonObject();
            json.addProperty("message", "Bin start");
            json.addProperty("id", bin.getId());

            PrintWriter out;
            try {
                out = response.getWriter();
                out.print(json);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
        }

    }

    /**
     * Delete container in DB
     * DELETE /containers/
     *
     * @param request  [request]
     * @param response [response]
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        logger.info(DefaultConfig.accessMarker, Util.getMessageAccess(request));

        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length != 2) {
            response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
            return;
        }

        String id = splits[1];

        if (!Database.existsBin(id)) {
            Bin bin = Database.getBin(id);
            Pool pool = Database.getPool(bin.getPoolId());
            String url = pool.getUrl() + ":" + pool.getWebPort();
            //String url = dataPool.getUrl().replace("ek_xib_0", "148.247.204.244") + ":" + dataPool.getPublicPort();
            //logger.debug(url);
            api.removeDataContainerInPool(url, bin.getId());
            api.stopContainer(bin.getContainer().getId());
            api.removeContainer(bin.getContainer().getId());
            Database.deleteBin(id);
        } else {
            response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
        }
        
    }

}
