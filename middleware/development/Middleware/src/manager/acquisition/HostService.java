package manager.acquisition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;

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
import manager.acquisition.storage.DataContextStorage;
import manager.model.plataform.ContextAttribute;
import manager.model.plataform.Host;
import manager.model.plataform.HostMicroservices;
import manager.model.plataform.Microservice;


@WebServlet("/api/v1/acquisition/hosts")
public class HostService extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(HostService.class);
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
     * Get hosts, microservices and attributes
     * <p>
     * GET /hosts/
     * GET /hosts/id
     * <p>
     * GET /hosts/id/microservices/
     * GET /hosts/id/microservices/id
     * <p>
     * GET /hosts/id/microservices/id/attributes/
     *
     * @param request  [request]
     * @param response [response]
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        logger.info(DefaultConfig.accessMarker, Util.getMessageAccess(request));
        String res;
        String id;
        String option;
        String microserviceId;
        PrintWriter out;

        if (pathInfo == null || pathInfo.equals("/")) {
            Collection<Host> collection = Database.getAllHost();
            response.setStatus(HttpURLConnection.HTTP_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            res = gson.toJson(collection);
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

                if (Database.existsHost(id)) {
                    response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                }

                response.setStatus(HttpURLConnection.HTTP_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                res = gson.toJson(Database.getHost(id));

                try {
                    out = response.getWriter();
                    out.print(res);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case 3:

                id = splits[1];
                option = splits[2];
                if (option.equals("microservices")) {
                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    Host host = Database.getHost(id);
                    res = gson.toJson(Database.getAllMicroservicesHost(host.getId()));

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
            case 4:

                id = splits[1];
                microserviceId = splits[3];

                if (Database.existsHostMicroservice(microserviceId)) {
                    response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                    return;
                } else {

                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    res = gson.toJson(Database.getMicroserviceHost(id, microserviceId));

                    try {
                        out = response.getWriter();
                        out.print(res);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 5:

                id = splits[1];
                microserviceId = splits[3];
                option = splits[4];

                if (option.equals("attributes")) {
                    if (Database.existsHostMicroservice(microserviceId)) {
                        response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                        return;
                    } else {

                        response.setStatus(HttpURLConnection.HTTP_OK);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");

                        Microservice microservice = Database.getMicroserviceHost(id, microserviceId);

                        List<ContextAttribute> contextAttributeList = dataContextStorage.getData(microservice.getName() + "-" + microservice.getId());

                        res = gson.toJson(contextAttributeList);

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

                break;
            default:
                response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
                break;
        }


    }

    /**
     * Add new host is DB
     * POST /hosts/
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
            HostMicroservices hostMicroservices = gson.fromJson(payload, HostMicroservices.class);
            System.out.println(hostMicroservices);

            Database.createHost(hostMicroservices);

            response.setStatus(HttpURLConnection.HTTP_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JsonObject json = new JsonObject();
            json.addProperty("message", "Register host");
            json.addProperty("id", hostMicroservices.getHost().getId());

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




