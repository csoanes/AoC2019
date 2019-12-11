import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Day9 {

    public static void main(String[] args) {
        File input = new File("/Users/tiff/AoC/AoC2019/Day9/day9Test1.txt");
        String baseString="";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            baseString = reader.readLine();
            System.out.println(baseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long[] instructions = Arrays.stream(baseString.split(",")).mapToLong(Long::parseLong)
                .boxed()
                .toArray(Long[]::new);
        BlockingQueue<Long> inputQueue = new LinkedBlockingQueue<Long>();
        BlockingQueue<Long> outputQueue = new LinkedBlockingQueue<Long>();
        
        IntCodeComputer computer = new IntCodeComputer(instructions.clone(),inputQueue, outputQueue);
        computer.run();
        try {
            System.out.println("Output: "+outputQueue.take().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
