import java.util.ArrayList;
import java.util.List;

public class Planet {
    Planet orbiting;
    List<Planet> orbitedBy;
    String name;
    int order=0         ;

    public Planet(Planet orbiting, String name, int order) {
        this.orbiting = orbiting;
        this.orbitedBy = new ArrayList<Planet>();
        this.name = name;
        this.order=order;
    }

    public void addChildPlanet(final Planet planet) {
        orbitedBy.add(planet);
    }
}
