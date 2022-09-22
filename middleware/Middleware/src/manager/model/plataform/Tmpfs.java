package manager.model.plataform;

public class Tmpfs {
    
    private final String target;

    public Tmpfs(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Tmpfs{" +
            " target='" + target + "'" +
            "}";
    }
   
}
