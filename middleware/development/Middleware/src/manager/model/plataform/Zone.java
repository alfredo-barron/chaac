package manager.model.plataform;

import java.util.List;

public class Zone {

    private final String label;
    private final List<Microservice> microservices;

    public Zone(String label, List<Microservice> microservices) {
        this.label = label;
        this.microservices = microservices;
    }

    public String getLabel() {
        return label;
    }

    public List<Microservice> getMicroservices() {
        return microservices;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "label='" + label + '\'' +
                ", microservices=" + microservices +
                '}';
    }

}
