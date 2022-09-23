package manager.model.plataform;

public class Port {

    private final String privatePort;
    private final String publicPort;

    public Port(String privatePort, String publicPort) {
        this.privatePort = privatePort;
        this.publicPort = publicPort;
    }

    public String getPrivatePort() {
        return privatePort;
    }

    public String getPublicPort() {
        return publicPort;
    }

    @Override
    public String toString() {
        return "publicPort{" +
                "privatePort='" + privatePort + '\'' +
                ", publicPort='" + publicPort + '\'' +
                '}';
    }

}
