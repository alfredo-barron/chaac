package manager.model.plataform;

import java.util.List;

public class HostMicroservices {

    private final Host host;
    private final List<Microservice> microservices;

    public HostMicroservices(Host host, List<Microservice> microservices) {
        this.host = host;
        this.microservices = microservices;
    }

    public Host getHost() {
        return host;
    }

    public List<Microservice> getMicroservices() {
        return microservices;
    }

    @Override
    public String toString() {
        return "HostMicroservices{" +
                "host=" + host +
                ", microservices=" + microservices +
                '}';
    }

}
