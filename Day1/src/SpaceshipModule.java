import java.math.*;
import java.util.ArrayList;
import java.util.List;


public class SpaceshipModule {

    private Integer mass;


    public SpaceshipModule(Integer mass) {
        this.mass=mass;
    }

    @Override
    public String toString() {
        return "SpaceshipModule{" +
                "mass=" + mass + " fuelRaw: " + getFuelRaw(getMass()) + " fuelCompound: " + getFuelCompound() +
                '}';
    }

    public Integer getMass() { return this.mass;}

    public Integer getFuelRaw(final Integer mass) {
        return ((int)Math.floor(mass/3)-2);}


    public Integer getFuelCompound(){
        Integer fuelSubs = 0;
        Integer initial = getFuelRaw(this.getMass());
        System.out.println("initial: " + initial);
        fuelSubs += (initial);
        System.out.println("fuelSubs: " + fuelSubs);
        while (initial > 0) {
            System.out.println("fuelSubs: " + fuelSubs);
            System.out.println("initial: " + initial);

            initial = getFuelRaw(initial);
            if (initial > 0) { fuelSubs+=initial;}
        }
        return fuelSubs;
    }
}
