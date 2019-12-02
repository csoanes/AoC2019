import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestClass {

    public static void main(String [] args) {
        // readinput
        String fileName = "Day2/testinput.txt";
        List<String> lines = new ArrayList<>();
        List<String> modules = new ArrayList<>();
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            lines = stream.collect(Collectors.toList());

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        lines.forEach(System.out::println);
        String[] program = lines.get(5).split(",");
        Integer[] programData = new Integer[program.length];

        int i=0;
        for(String line: program) {
            System.out.println("Line: "+line);
            programData[i++]=(Integer.parseInt(line));
        }
        for (int noun=0; noun <100; noun++) {
            for (int verb=0; verb < 100; verb++) {
                // grab a copy of programData and set noun and verb
                Integer[] currData = Arrays.copyOf(programData, programData.length);
                currData[1]=noun;
                currData[2]=verb;
                System.out.println("Trying with noun: " + noun + " and verb: " + verb);
                runProgram(currData);
                System.out.println(currData[0]+ " Finished!");
                System.out.println("Program: " + Arrays.toString(currData));
                if (currData[0]==19690720) {
                    System.out.println("Bingo! noun: " + noun + " verb: " + verb);
                    System.exit(0);
                }
            }
        }


        System.exit(0);


//        modules.forEach(System.out::println);
//
//        Integer sum =0;
//        Integer compoundsum =0;
//
//
//        System.out.println(sum);
//        System.out.println(compoundsum);
    }

    private static void runProgram(Integer[] programData) {
        int counter =0;

        while (programData[counter]!=99) {
            int opcode = programData[counter];
            //Addition
            int lhs = programData[programData[counter+1]];
            int rhs = programData[programData[counter+2]];
            int targetCounter = programData[counter+3];
            if (opcode == 1) {
                programData[targetCounter] = lhs+rhs;
            } else if (opcode== 2) {
                programData[targetCounter] = lhs*rhs;
            } else {
                System.out.println("Error invalid opcode: " + opcode);
            }
            counter += 4;
        }
    }


}
