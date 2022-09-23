package manager.acquisition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commons.util.Util;
import config.DefaultConfig;
import manager.acquisition.storage.DataContextStorage;
import manager.model.plataform.ContextAttribute;

@WebServlet("/api/v1/acquisition/attributes")
public class AttributeService extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AttributeService.class);
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();
    DataContextStorage dataContextStorage = new DataContextStorage();

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
     * Add new attributes is DB
     * POST /attributes/
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

            Type contextAttributeList = new TypeToken<ArrayList<ContextAttribute>>() {
            }.getType();
            List<ContextAttribute> contextAttributes = gson.fromJson(payload, contextAttributeList);
            logger.debug(contextAttributes.toString());

            // Add DB
            dataContextStorage.putData(contextAttributes);

            response.setStatus(HttpURLConnection.HTTP_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JsonObject json = new JsonObject();
            json.addProperty("message", "Register context attributes");

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

}
