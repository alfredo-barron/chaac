package manager.model.components;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import config.Database;
import config.DefaultConfig;
import manager.model.plataform.Container;
import manager.model.plataform.Port;
import manager.model.plataform.Volume;

public class Pool {

    private final String id;
    private String image;
    private String network;
    private String url;
    private int dataPort;
    private int webPort;
    private int publicPort;
    private String lb;

    private Container container;
    private boolean status;
    private String created_at;

    public Pool(String id) {
        this.id = id;
    }

    public Pool(String id, String image, String network, String url, int dataPort, int webPort, int publicPort, String lb) {
        this.id = id;
        setImage(image);
        setNetwork(network);
        setUrl(url);
        setDataPort(dataPort);
        setWebPort(webPort);
        setPublicPort(publicPort);
        setLb(lb);
        setStatus(false);
        container = new Container();
        created_at = new Timestamp(System.currentTimeMillis()).toString();
    }

    public String getId() {
        return id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }

    public int getDataPort() {
        return dataPort;
    }

    public void setWebPort(int webPort) {
        this.webPort = webPort;
    }

    public int getWebPort() {
        return webPort;
    }

    public void setPublicPort(int publicPort) {
        this.publicPort = publicPort;
    }

    public int getPublicPort() {
        return publicPort;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void createContainer() {
        List<Port> ports = new ArrayList<>();
        ports.add(new Port(String.valueOf(webPort), String.valueOf(publicPort)));

        Map<String, String> env = new HashMap<>();
        env.put("NODE_ID", id);
        env.put("NODE_URL", url);
        env.put("NODE_LB", lb);
        env.put("WEB_PORT", String.valueOf(webPort));
        env.put("DATA_PORT", String.valueOf(dataPort));
        env.put("PUBLIC_PORT", String.valueOf(publicPort));
        env.put("LOG_PATH","/app/logs");
        String urlMiddleware = DefaultConfig.NODE_URL + ":" + DefaultConfig.WEB_PORT;
        env.put("URL_MIDDLEWARE", urlMiddleware);
        String accessToken = Database.AUTH.createAccessToken(id);
        env.put("ACCESS_TOKEN", accessToken);

        List<Volume> volumes = new ArrayList<>();
        volumes.add(new Volume(DefaultConfig.VOLUME_LOGS,"/app/logs","rw"));

        container =  new Container(id, image, network);
        container.setPorts(ports);
        container.setEnv(env);
        container.setVolumes(volumes);   
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pool dataPool = (Pool) o;
        return id.equals(dataPool.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pool{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", network='" + network + '\'' +
                ", url='" + url + '\'' +
                ", dataPort=" + dataPort +
                ", webPort=" + webPort +
                ", publicPort=" + publicPort +
                ", lb='" + lb + '\'' +
                ", container=" + container +
                ", status=" + status +
                ", created_at='" + created_at + '\'' +
                '}';
    }

}
