package backend.pattern;

public class ParkingSingleton {

    private static ParkingSingleton instance;

    private ParkingSingleton() {
    }

    public static synchronized ParkingSingleton getInstance() {
        if (instance == null) {
            instance = new ParkingSingleton();
        }

        return instance;
    }
}