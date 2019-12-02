import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestClass {

    public static void main(String [] args) {
        // readinput
        String fileName = "Day1/realinput.txt";
        List<String> lines = new ArrayList<>();
        List<SpaceshipModule> modules = new ArrayList<>();
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            lines = stream.collect(Collectors.toList());

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        lines.forEach(System.out::println);

        for(String line: lines) {
            modules.add(new SpaceshipModule(Integer.parseInt(line)));
        }

        modules.forEach(System.out::println);

        Integer sum =0;
        Integer compoundsum =0;

        for(SpaceshipModule module: modules) {
            sum += module.getFuelRaw(module.getMass());
            compoundsum += module.getFuelCompound();
        }

        System.out.println(sum);
        System.out.println(compoundsum);
    }
}
