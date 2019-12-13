import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Day9 {

    public static void main(String[] args) {
        File input = new File("/Users/tiff/AoC/AoC2019/Day9/day9code.txt");
        String baseString="";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            baseString = reader.readLine();
            System.out.println(baseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long[] instructions = Arrays.stream(baseString.split(",")).mapToLong(Long::parseLong)
                .toArray();
        BlockingQueue<BigDecimal> inputQueue = new LinkedBlockingQueue<BigDecimal>();
        BlockingQueue<BigDecimal> outputQueue = new LinkedBlockingQueue<BigDecimal>();
        inputQueue.add(BigDecimal.ONE)   ;
        IntcodeComputer computer = new IntcodeComputer(instructions.clone(),inputQueue, outputQueue);
        computer.run();
        try {
            while (outputQueue.size()>0) {
                System.out.println("Output: " + outputQueue.take().toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
