import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day8Part1 {

    public static void main(String[] args) {
        File input = new File("/Users/tiff/AoC/AoC2019/Day8/passwordpicture.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String baseString = reader.readLine();
            System.out.println(baseString);
            int width=25;
            int height=6;
            int layerlength=width*height;
            int layers = baseString.length()/layerlength;

            int[][] picture= new int[height][width];
            for (int j=0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    picture[j][k] = 2;
                }
            }
            int minZeros=65536;
            int part1answer=0;

            System.out.println("Layers: "+layers);

            char[] array=baseString.toCharArray();
            int index=0;

            for (int i=0; i < layers; i++) {
                System.out.println("Layer: "+i);
                int zeros=0;
                int ones=0;
                int twos = 0;
                for (int j=0; j < height; j++) {
                    StringBuilder line = new StringBuilder();
                    for (int k=0; k < width; k++) {
                        line.append(array[index]) ;
                        int pixel=Integer.parseInt(String.valueOf(array[index++]))   ;
                        if (pixel<2 && picture[j][k]==2) picture[j][k]=pixel;
                    }
                    System.out.println(line);

                    zeros+= line.chars().filter(ch -> ch == '0').count();
                    ones+=  line.chars().filter(ch -> ch == '1').count();
                    twos+= line.chars().filter(ch -> ch == '2').count();

                }
                System.out.println("Zeros for this layer: "+ zeros);
                if (zeros < minZeros) {
                    minZeros=zeros;
                    part1answer= twos*ones;

                }
                System.out.println(Arrays.toString(picture));
            }

            for (int j=0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    System.out.print(picture[j][k]);
                }
                System.out.println();
            }
            System.out.println("Part1 answer: " + part1answer);
        } catch (IOException e) {
            e.printStackTrace();
            ;
        }

    }
}
