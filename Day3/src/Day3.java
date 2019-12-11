import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {

    public static void main(String [] args) {
        String fileName = " ealinput.txt";
        List<String> lines = new ArrayList<>();
        List<String> modules = new ArrayList<>();
        //read file into stream, try-with-resources

        try (
                Stream<String> stream = Files.lines(Paths.get(fileName))) {

            lines = stream.collect(Collectors.toList());

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        lines.forEach(System.out::println);

        Wire wire1 = new Wire();
        wire1.setLocation(0,0);
        Wire wire2 = new Wire();
        wire2.setLocation(0,0);
        String line1 = lines.get(0);
        String[] moves = line1.split(",");
        for (String move: moves) {
            char direction = move.charAt(0);
            Integer magnitude = Integer.parseInt(move.substring(1));
            System.out.println("Moving direction: "+ direction +" mag:" + magnitude);
            wire1.move(Character.toString(direction), magnitude);
        }
        System.out.println("Wire1 final location" + wire1.toString());
        String line2 = lines.get(1);
        moves = line2.split(",");
        for (String move: moves) {
            char direction= move.charAt(0);
            Integer magnitude = Integer.parseInt(move.substring(1));
            System.out.println("Moving direction: "+ direction +" mag:" + magnitude);
            wire2.move(Character.toString(direction), magnitude);
        }
        System.out.println("Wire2 final location" + wire2.toString());
        List<Point> wire1Trail = wire1.getTrail();
        int minDistance=32768;
        int minWirelength = 32768;
        for (Point point:wire1Trail) {
            //System.out.println("Checking: " + point.toString());
            if (wire2.checkTrail(point)) {
                System.out.println("Intersection at" + point.toString());
                int distance = Math.abs(point.x) + Math.abs(point.y);
                if (distance != 0 && distance < minDistance)     {
                    minDistance=distance;
                }
                System.out.println("distance: "+distance);
                if (distance >0) {
                    int wirelength= (wire1.countStepsTo(point)+wire2.countStepsTo(point));
                    System.out.println("wirelength to intercesction: " +wirelength)    ;
                    if (wirelength < minWirelength) minWirelength=wirelength;
                }
            }
        }

        System.out.println("Mindist: " + minDistance);
        System.out.println("Minwirelength: " + minWirelength);
    }

}
