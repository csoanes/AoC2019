import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    List<String> orbits = null;
    static Map<String, Map<String, Integer>> hierarchiesMap = new HashMap<String, Map<String, Integer>>();
    static Map<String, Integer> planetOrders = new HashMap<>();
    static int youOrder=0;
    static int sanOrder=0;
    static int commonOrder=0;

    public static void main(String [] args) {

        Main main = new Main();

        // load the data
        try {
            main.orbits = Files.readAllLines(Paths.get("/Users/tiff/AoC/AoC2019/Day6/realInput.txt"));
            System.out.println(Arrays.toString(main.orbits.toArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // add all orbitees to list
        Map<String/*Orbiter*/, String/*Orbitee*/> orbitMap = new HashMap<>();

        for (String orbit : main.orbits) {
            int indexof = orbit.indexOf(')');
            String orbitee = orbit.substring(0, indexof);
            String orbiter = orbit.substring(indexof + 1);
            orbitMap.put(orbiter, orbitee);
            System.out.println(orbiter + " is in orbit of " + orbitee);
        }
        // find the origin
        Planet origin = null;
        for (String orbitee : orbitMap.values()) {
            if (!orbitMap.keySet().contains(orbitee)) {
                origin = new Planet(null, orbitee,0);
                System.out.println("Origin found: " + orbitee);
            }
        }
        //now, we know the origin - find all planets that have that as orbitee

        String result = orbitMap.entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + entry.getValue())
                .collect(Collectors.joining(", "));
        System.out.println(result);
        main.addPlanets(origin);
        System.out.println("OrderSum: " + main.printPlanets(origin, 0));
        // find the intersection of the two herarchiemap
        Set<String> youSet = hierarchiesMap.get("YOU").keySet();
        Set<String> sanSet = hierarchiesMap.get("SAN").keySet() ;  ;

        for (String planetName: youSet)   {
            System.out.print(planetName+",");

        }
        System.out.println();
        for (String planetName: sanSet)   {
            System.out.print(planetName+",");

        }
        System.out.println();
        sanSet.retainAll(youSet);
        commonOrder=0;
        for (String planetName: sanSet)   {
            int planetOrder = planetOrders.get(planetName);
            System.out.print(" "+planetName+", order: "+ planetOrder);
            if (planetOrder > commonOrder) commonOrder=planetOrder;
        }
        System.out.println();
        System.out.println("Highest Common Order: "+commonOrder + " San order: " + sanOrder + " youOrder: "+ youOrder );
        int mintrans=(youOrder-1)-commonOrder+(sanOrder-1)-commonOrder        ;
        System.out.println("minimum transfers: " + mintrans);
    }
    // for each thing that orbits nothing, find things that orbit it
    // recurse, adding to tree and counting vertices back to origin
    void addPlanets(Planet orbitee) {
        System.out.println("Adding planets orbited by " + orbitee.name);
        for (String orbit : orbits) {
            int indexof = orbit.indexOf(')');
            String orbiteeStr = orbit.substring(0, indexof);
            if (orbiteeStr.equals(orbitee.name)) {
                String orbiterStr=orbit.substring(indexof+1);
                System.out.println("Found planet: " + orbiterStr)  ;
                
                orbitee.addChildPlanet(new Planet(orbitee, orbiterStr,orbitee.order+1));
            }
        }
        for (Planet orbiter:orbitee.orbitedBy)  {
            addPlanets(orbiter);
        }

    }

    int printPlanets(Planet orbitee, int tab) {
        int orderSum=0;
        StringBuilder tabStr= new StringBuilder();
        for (int i=0; i < tab; i++ ) tabStr.append(" ");
        orderSum+=orbitee.order;
        if (orbitee.name.equals("YOU")) {
            youOrder=orbitee.order;
            storeHierarchy(orbitee);
        }else {
            if (orbitee.name.equals("SAN")) {
                sanOrder=orbitee.order;
                storeHierarchy(orbitee);
            }
        }
        System.out.println(tabStr+"Planet: " + orbitee.name + " of order "+ orbitee.order +" is orbited by: ");
        planetOrders.put(orbitee.name, orbitee.order);
        for (Planet orbiter: orbitee.orbitedBy) {
            orderSum+=printPlanets(orbiter, tab++);
        }
        return orderSum;
    }

    void storeHierarchy(Planet planet) {
        String name= planet.name;
        Map<String, Integer>hierarchyMap = new HashMap<String, Integer>();
        while (planet.orbiting!=null) {
            hierarchyMap.put(planet.name, planet.order);
            planet=planet.orbiting;
        }
        hierarchiesMap.put(name, hierarchyMap);
    }
}
