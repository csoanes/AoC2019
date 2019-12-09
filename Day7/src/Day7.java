import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Day7 {
    public static void main(String[] args) {
        File input = new File("/Users/tiff/AoC/AoC2019/Day7/ampcontroller.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String baseString = reader.readLine();
            int[] instructions = Arrays.stream(baseString.split(",")).mapToInt(Integer::parseInt).toArray();

            List<Integer> phases = Arrays.asList(new Integer[]{5,6,7,8,9});
            Integer highest=0;
            for (int j=0; j < 100; j++) {
                Collections.shuffle(phases);
                // set up amplifier 1 and seed it with phase0
                BlockingQueue<Integer> inputToFirst = new LinkedBlockingQueue<Integer>();
                BlockingQueue<Integer> firstToSecond = new LinkedBlockingQueue<Integer>();
                BlockingQueue<Integer> secondToThird = new LinkedBlockingQueue<Integer>();
                BlockingQueue<Integer> thirdToFourth = new LinkedBlockingQueue<Integer>();
                BlockingQueue<Integer> fourthToFifth = new LinkedBlockingQueue<Integer>();

                inputToFirst.add(phases.get(0));
                inputToFirst.add(0);
                firstToSecond.add(phases.get(1));
                secondToThird.add(phases.get(2));
                thirdToFourth.add(phases.get(3));
                fourthToFifth.add(phases.get(4));

                //IntcodeComputer computer = new IntcodeComputer(instructions.clone(),inputToFirst, firstToSecond);
                new Thread(new IntcodeComputer(instructions.clone(), inputToFirst, firstToSecond)).start();
                new Thread(new IntcodeComputer(instructions.clone(), firstToSecond, secondToThird)).start();
                new Thread(new IntcodeComputer(instructions.clone(), secondToThird, thirdToFourth)).start();
                new Thread(new IntcodeComputer(instructions.clone(), thirdToFourth, fourthToFifth)).start();
                new Thread(new IntcodeComputer(instructions.clone(), fourthToFifth, inputToFirst)).start();
                try {
                    Thread.sleep(300);
                    int output = inputToFirst.take();
                    if (highest < output) highest = output;
                    System.out.println("System Output is " + output);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Hightest is:"+ highest);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}