package manager.creation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commons.client.Rest;
import commons.messages.Response;
import commons.util.Util;
import config.Database;
import config.DefaultConfig;
import manager.model.components.Bin;
import manager.model.components.Pool;
import util.Api;

/**
 * API Rest of DataContainers
 *
 * @author abarron
 * @version 1
 */
@WebServlet("/api/v1/creation/pools/*")
public class PoolService extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(PoolService.class);
    private static final long serialVersionUID = 1L;
    private final Api api = new Api();
    private final Gson gson = new Gson();
    private final JsonParser parser = new JsonParser();

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
     * Get pools
     * GET /pools/
     * GET /pools/id
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
            Collection<Pool> collection = Database.getAllPools();
            response.setStatus(HttpURLConnection.HTTP_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String res = gson.toJson(collection);

            Database.GRAPH.viewNodes();
            Database.GRAPH.viewEgdes();
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

                if (id.equals("stats")) {
                    List<JsonObject> list = new ArrayList<>();
                    JsonParser parser = new JsonParser();
                    for (Pool pool : Database.getAllPools()) {
                        String url = pool.getUrl() + "/api/v1/stats";
                        Response res = new Rest(url).get();
                        JsonElement jsonElement = parser.parse(res.getMessage());
                        list.add(jsonElement.getAsJsonObject());
                    }
                    try {
                        response.setStatus(HttpURLConnection.HTTP_OK);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");

                        out = response.getWriter();
                        out.print(list);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }

                if (Database.existsPool(id)) {
                    response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                    return;
                }

                response.setStatus(HttpURLConnection.HTTP_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String res = gson.toJson(Database.getPool(id));

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

                    Pool dataPool = Database.getPool(id);
                    containerId = api.createContainer(dataPool.getContainer());
                    dataPool.getContainer().setId(containerId);

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Create pool " + dataPool.getId());

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

                    Pool pool = Database.getPool(id);
                    api.startContainer(pool.getContainer().getId());

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Start pool " + pool.getId());

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

                    Pool pool = Database.getPool(id);
                    pool.setStatus(false);
                    api.restartContainer(pool.getContainer().getId());

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Restart pool " + pool.getId());

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

                    Pool pool = Database.getPool(id);
                    api.stopContainer(pool.getContainer().getId());
                    pool.setStatus(false);

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Stop pool " + pool.getId());

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

                    Pool pool = Database.getPool(id);
                    api.removeContainer(pool.getContainer().getId());
                    pool.setStatus(false);

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    JsonObject json = new JsonObject();
                    json.addProperty("message", "Remove pool " + pool.getId());

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

                // DELETE BINS
                if (option.equals("delete")) {
                    for (Bin bin : Database.getAllBinPool(id)) {
                        String url = bin.getUrl() + "/api/v1/stats/delete";
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

                if (option.equals("containers")) {
                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    Pool pool = Database.getPool(id);
                    res = gson.toJson(Database.getAllBinPool(pool.getId()));

                    try {
                        out = response.getWriter();
                        out.print(res);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                    return;
                }

                break;

            default:
                response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                break;
        }

    }

    /**
     * Add new pool is DB
     * POST /pools/
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

            Pool pool = gson.fromJson(payload, Pool.class);
            logger.debug(pool.toString());

            long timeStart, timeEnd, timeTotal = 0;
            Database.createPool(pool);
            timeStart = System.nanoTime();
            String containerId = api.createContainer(pool.getContainer());
            timeEnd = System.nanoTime();
            timeTotal = timeEnd - timeStart;
            pool.getContainer().setId(containerId);

            logger.info("Deploy " + pool.getId() + " time: " + timeTotal);
       
            response.setStatus(HttpURLConnection.HTTP_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JsonObject json = new JsonObject();
            json.addProperty("message", "Pool start");
            json.addProperty("id", pool.getId());

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
     * Update pool is DB
     * PUT /pools/id
     *
     * @param request  [request]
     * @param response [response]
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
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

        if (Database.existsPool(id)) {
            response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
            return;
        }

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

        JsonObject jsonObject = parser.parse(payload).getAsJsonObject();
        logger.debug(jsonObject.toString());
        String accessToken = jsonObject.get("access_token").getAsString();
        boolean status = jsonObject.get("status").getAsBoolean();

        if (Database.AUTH.authToken(accessToken, id)) {
            Database.updateStatusPool(id, status);

            response.setStatus(HttpURLConnection.HTTP_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JsonObject json = new JsonObject();
            json.addProperty("message", "Update pool");
            json.addProperty("id", id);

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
            response.setStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
        }

    }

    /**
     * Delete pool in DB
     * DELETE /pools/id
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

        if (!Database.existsPool(id)) {
            Pool pool = Database.getPool(id);
            api.stopContainer(pool.getContainer().getId());
            api.removeContainer(pool.getContainer().getId());
            Database.deletePool(id);
        } else {
            response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
        }

    }

}
