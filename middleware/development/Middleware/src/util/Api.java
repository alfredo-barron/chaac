package util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import commons.client.Rest;
import commons.messages.Response;
import config.DefaultConfig;
import manager.model.plataform.Container;

public class Api {

    public final String LAUNCHER_URL = DefaultConfig.LAUNCHER_URL;
    private final Gson gson = new Gson();
    private final JsonParser parser = new JsonParser();

    public Api() {}

    public String createContainer(Container container) {
        Response response;
        String server = "http://" + LAUNCHER_URL + "/api/containers";

        String jsonInString = gson.toJson(container);
        response = new Rest(server).post(jsonInString);

        JsonObject jsonObject = parser.parse(response.getMessage()).getAsJsonObject();
        System.out.println(jsonObject);

        return jsonObject.get("short_id").getAsString();
    }

    public Response startContainer(String id) {
        Response response;
        String server = "http://" + LAUNCHER_URL + "/api/containers/" + id + "/start";

        response = new Rest(server).get();

        return response;
    }

    public Response restartContainer(String id) {
        Response response;
        String server = "http://" + LAUNCHER_URL + "/api/containers/" + id + "/restart";

        response = new Rest(server).get();

        return response;
    }

    public Response stopContainer(String id) {
        Response response;
        String server = "http://" + LAUNCHER_URL + "/api/containers/" + id + "/stop";

        response = new Rest(server).get();

        return response;
    }

    public Response removeContainer(String id) {
        Response response;
        String server = "http://" + LAUNCHER_URL + "/api/containers/" + id;

        response = new Rest(server).delete();

        return response;
    }

    public Response removeDataContainerInPool(String url, String id) {
        Response response;
        
        String server = "http://" + url + "/api/v1/containers/" + id;
        System.out.println(server);

        response = new Rest(server).delete();

        return response;
    }

}
