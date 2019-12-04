import java.awt.*;
import java.util.ArrayList;

public class Wire {
    private Point location;
    private ArrayList<Point> trail = new ArrayList<>() ;



    public boolean checkTrail(final Point point) {
        // search trail for matching points
        return trail.contains(point);
    }

    public void move(final String direction, Integer distance) {
        while (distance >0) {
            trail.add(location);
            int x=location.x;
            int y= location.y;
            switch (direction) {
                case "U":
                    x++;
                    break;
                case "D":
                    x--;
                    break;
                case "L":
                    y--;
                    break;
                case "R":
                    y++;
                    break;
                default:
                    System.out.println("Error unknown directoon");
            }
            distance--;
            location=new Point(x,y);
            System.out.println("New location: " + location.toString());
        }
        printTrail();
    }

    public void setLocation(int x, int y) {
        this.location= new Point(x,y);
        this.trail.clear();
    }

    public String toString() {
        return ("loc: "+ location.toString());
    }

    public ArrayList<Point> getTrail() {
        return this.trail;
    }

    public int countStepsTo(Point point) {
        int steps =0;
        for (Point trailpoint : trail) {

            if (point.x==trailpoint.x && point.y==trailpoint.y) return steps;
            steps++;
        }
        return 0;
    }

    public void printTrail() {
        for (Point point : trail) {
            System.out.println(point.toString())     ;
        }
    }
}
