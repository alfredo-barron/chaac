package manager.creation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commons.util.Util;
import config.Database;
import config.DefaultConfig;
import manager.model.components.Ball;

/**
 * API Rest of Metadata of data in the DistributionScheme
 *
 * @author abarron
 * @version 1
 */
@WebServlet("/api/v1/balls/*")
public class BallService extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BallService.class);
    private final Gson gson = new Gson();
    private final HashMap<String, Ball> db = new HashMap<>();

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
     * Get balls
     * GET /balls/
     * GET /balls/id
     *
     * @param request  [request]
     * @param response [response]
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        Map<String, String[]> parameters = request.getParameterMap();
        logger.info(DefaultConfig.accessMarker, Util.getMessageAccess(request));
        PrintWriter out;

        if (!parameters.isEmpty()) {
            if (parameters.containsKey("fileName")) {
                String[] fileNames = parameters.get("fileName");
                if (fileNames.length == 1) {
                    String fileName = fileNames[0];
                    Ball ball = Database.getBallByName(fileName);
                    if (!Objects.isNull(ball)) {
                        response.setStatus(HttpURLConnection.HTTP_OK);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        String res = gson.toJson(ball);
                        try {
                            out = response.getWriter();
                            out.print(res);
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                        return;
                    }
                }
            }
            if (parameters.containsKey("hash")) {
                String[] hashes = parameters.get("hash");
                if (hashes.length == 1) {
                    String hash = hashes[0];
                    Ball ball = Database.getBallByHash(hash);
                    if (!Objects.isNull(ball)) {
                        response.setStatus(HttpURLConnection.HTTP_OK);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        String res = gson.toJson(ball);
                        try {
                            out = response.getWriter();
                            out.print(res);
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                        return;
                    }
                }
            }
            return;
        }

        if (pathInfo == null || pathInfo.equals("/")) {
            Collection<Ball> collection = Database.getAllBalls();
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

        if (splits.length != 2) {
            response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
            return;
        }

        String id = splits[1];

        if (Database.existsBall(id)) {
            response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
            return;
        }

        response.setStatus(HttpURLConnection.HTTP_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String res = gson.toJson(Database.getBall(id));

        try {
            out = response.getWriter();
            out.print(res);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Add new metadata is DB
     * POST /metadata/
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

            Ball ball = gson.fromJson(payload, Ball.class);
            logger.debug(ball.toString());
            Database.createBall(ball);

            response.setStatus(HttpURLConnection.HTTP_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JsonObject json = new JsonObject();
            json.addProperty("message", "Register ball");
            json.addProperty("id", ball.getId());

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

        if (!db.containsKey(id)) {
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

        Ball ball = gson.fromJson(payload, Ball.class);

        db.put(id, ball);

        response.setStatus(HttpURLConnection.HTTP_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String res = gson.toJson(ball);

        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(res);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete metadata in DB
     * DELETE /metadata/
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

        db.remove(id);

        if (!db.containsKey(id)) {
            response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
        }

    }

}
