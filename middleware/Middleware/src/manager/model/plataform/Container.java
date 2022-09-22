package manager.model.plataform;

import java.util.List;
import java.util.Map;

public class Container {

    private String id;
    private String name;
    private String image;
    private String network;
    private List<Port> ports;
    private Map<String, String> env;
    private Tmpfs tmpfs;
    private List<Volume> volumes;

    public Container() {
    }

    public Container(String name, String image, String network) {
        this.name = name;
        this.image = image;
        this.network = network;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }

    public String getNetwork() {
        return this.network;
    }

    public List<Port> getPorts() {
        return this.ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public Map<String,String> getEnv() {
        return this.env;
    }

    public void setEnv(Map<String,String> env) {
        this.env = env;
    }

    public Tmpfs getTmpfs() {
        return this.tmpfs;
    }

    public void setTmpfs(Tmpfs tmpfs) {
        this.tmpfs = tmpfs;
    }

    public List<Volume> getVolumes() {
        return this.volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }


    @Override
    public String toString() {
        return "Container{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", network='" + network + '\'' +
                ", ports=" + ports +
                ", env=" + env +
                ", tmpfs=" + tmpfs +
                ", volumes=" + volumes +
                '}';
    }

}
