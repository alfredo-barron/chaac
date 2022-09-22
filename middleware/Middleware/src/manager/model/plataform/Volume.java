package manager.model.plataform;

public class Volume {

    private final String volume;
    private final String bind;
    private final String mode;

    public Volume(String volume, String bind, String mode) {
        this.volume = volume;
        this.bind = bind;
        this.mode = mode;
    }

    public String getVolume() {
        return volume;
    }

    public String getBind() {
        return bind;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "Volume{" +
                "volume='" + volume + '\'' +
                ", bind='" + bind + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }

}
