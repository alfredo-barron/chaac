package manager.model.plataform;

import java.util.Objects;

/**
 * @author alfredobarron
 */
public class Host {

    private final String id;
    private String url;
    private long cores;
    private long memory;

    public Host(String id) {
        this.id = id;
    }

    public Host(String id, String url, long cores, long memory) {
        this.id = id;
        this.url = url;
        this.cores = cores;
        this.memory = memory;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return id.equals(host.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Host{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", cores=" + cores +
                ", memory=" + memory +
                '}';
    }

}
