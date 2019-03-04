import java.io.IOException;

public class Main {
    public static void main(String [] args) throws IOException {
        Keychanger keychanger = new Keychanger();
        keychanger.read("resources/config.properties");
        keychanger.write("resources/new.properties", keychanger.change());
    }
}
