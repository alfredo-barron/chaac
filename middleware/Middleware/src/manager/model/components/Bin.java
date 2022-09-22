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
import manager.model.plataform.Tmpfs;
import manager.model.plataform.Volume;

/**
 * Bin Model
 *
 * @author abarron
 * @version 1
 */
public class Bin {

    private final String id;
    private String poolId;
    private String image;
    private String network;
    private String url;
    private int dataPort;
    private int webPort;
    private int publicPort;

    private long cacheSize;
    private String cachePolicy;
    
    private int levels;
    private String memDir;
    private String memLevel;
    private String memSize;
    private String diskDir;
    private String diskLevel;
    private String diskSize;

    private Container container;
    private boolean status;
    private String created_at;

    public Bin(String id) {
        this.id = id;
    }

    public Bin(String id, String poolId, String image, String network, String url, int dataPort, int webPort, 
                        int publicPort, long cacheSize, String cachePolicy, int levels, String memDir, String memLevel, 
                        String memSize, String diskDir, String diskLevel, String diskSize) {
        this.id = id;
        setPoolId(poolId);
        setImage(image);
        setNetwork(network);
        setUrl(url);
        setDataPort(dataPort);
        setWebPort(webPort);
        setPublicPort(publicPort);
        setCacheSize(cacheSize);
        setCachePolicy(cachePolicy);
        setLevels(levels);
        setMemDir(memDir);
        setMemLevel(memLevel);
        setMemSize(memSize);
        setDiskDir(diskDir);
        setDiskLevel(diskLevel);
        setDiskSize(diskSize);
        setStatus(false);
        created_at = new Timestamp(System.currentTimeMillis()).toString();
    }

    public String getId() {
        return id;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }

    public void setWebPort(int webPort) {
        this.webPort = webPort;
    }

    public void setPublicPort(int publicPort) {
        this.publicPort = publicPort;
    }

    public void setCacheSize(long cacheSize) {
        this.cacheSize = cacheSize;
    }

    public void setCachePolicy(String cachePolicy) {
        this.cachePolicy = cachePolicy;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public void setMemDir(String memDir) {
        this.memDir = memDir;
    }

    public void setMemLevel(String memLevel) {
        this.memLevel = memLevel;
    }

    public void setMemSize(String memSize) {
        this.memSize = memSize;
    }

    public void setDiskDir(String diskDir) {
        this.diskDir = diskDir;
    }

    public void setDiskLevel(String diskLevel) {
        this.diskLevel = diskLevel;
    }

    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    public Container getContainer() {
        return container;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void createContainer(String urlDataPool, int urlDataPoolPort, String urlCloud) {
        List<Port> ports = new ArrayList<>();
        ports.add(new Port(String.valueOf(webPort), String.valueOf(publicPort)));
        
        Map<String, String> env = new HashMap<>();
        env.put("NODE_ID", id);
        env.put("NODE_URL", url);
        env.put("DATA_PORT", String.valueOf(dataPort));
        env.put("WEB_PORT", String.valueOf(webPort));
        env.put("PUBLIC_PORT", String.valueOf(publicPort));
        env.put("CACHE_SIZE", String.valueOf(cacheSize));
        env.put("CACHE_POLICY", cachePolicy);
        env.put("NODE_LEVELS", String.valueOf(levels));
        env.put("MEM_DIR", memDir);
        env.put("MEM_LEVEL", memLevel);
        env.put("MEM_SIZE", memSize);
        env.put("DISK_DIR", diskDir);
        env.put("DISK_LEVEL", diskLevel);
        env.put("DISK_SIZE", diskSize);
        env.put("URL_DATA_POOL", urlDataPool);
        env.put("PORT_DATA_POOL", String.valueOf(urlDataPoolPort));
        env.put("LOG_PATH","/app/logs");
        String urlMiddleware = DefaultConfig.NODE_URL + ":" + DefaultConfig.WEB_PORT;
        env.put("URL_MIDDLEWARE", urlMiddleware);
        //env.put("URL_SYNC", urlSync);
        env.put("URL_CLOUD", urlCloud);
        String accessToken = Database.AUTH.createAccessToken(id);
        env.put("ACCESS_TOKEN", accessToken);

        Tmpfs tmpfs = new Tmpfs(memDir);

        List<Volume> volumes = new ArrayList<>();
        volumes.add(new Volume(DefaultConfig.VOLUME_LOGS,"/app/logs","rw"));

        container = new Container(id, image, network);
        container.setPorts(ports);
        container.setEnv(env);
        container.setTmpfs(tmpfs);
        container.setVolumes(volumes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bin that = (Bin) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bin{" +
                "id='" + id + '\'' +
                ", dataPoolId='" + poolId + '\'' +
                ", url='" + url + '\'' +
                ", dataPort=" + dataPort +
                ", webPort=" + webPort +
                ", publicPort=" + publicPort +
                ", cacheSize=" + cacheSize +
                ", cachePolicy='" + cachePolicy + '\'' +
                ", levels=" + levels +
                ", memDir='" + memDir + '\'' +
                ", memLevel='" + memLevel + '\'' +
                ", memSize='" + memSize + '\'' +
                ", diskDir='" + diskDir + '\'' +
                ", diskLevel='" + diskLevel + '\'' +
                ", diskSize='" + diskSize + '\'' +
                ", container=" + container +
                ", status=" + status +
                ", created_at='" + created_at + '\'' +
                '}';
    }

}
