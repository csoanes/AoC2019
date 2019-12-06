import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    private List<Integer> codes;
    private int checkSum;

    public static void main(String[] args) throws IOException {
        List<Integer> codes = Arrays.stream(new String(Files.readAllBytes(Paths.get("/Users/tiff/AoC/AoC2019/Day5/realinput.txt"))).split(",")).mapToInt(s -> Integer.parseInt(s.trim())).boxed().collect(Collectors.toCollection(ArrayList::new));

        Program firstPart = new Program(new ArrayList<>(codes), 0, 4);
    }

    public Program(List<Integer> codes, int noun, int verb) {
        this.codes = codes;
//        this.codes.set(1, noun);
//        this.codes.set(2, verb);
        this.checkSum = noun * 100 + verb;
        run();
    }

    private void run() {
        int address=0;
        while (true) {
            int value = codes.get(address);
            if (value == 99) return;
            String operation = String.valueOf(value);
            Integer opcode = 99;
            Integer param1mode = 0;
            Integer param2mode = 0;
            Integer param3mode = 0;
            if (operation.length() > 1) {
                opcode = Integer.parseInt(operation.substring(operation.length() - 2));
                param1mode = operation.charAt(operation.length() - 3) == '1' ? 1 : 0;
                param2mode = operation.length() > 3 ? operation.charAt(operation.length() - 4) == '1' ? 1 : 0 : 0;
                param3mode = operation.length() > 4 ? operation.charAt(operation.length() - 5) == '1' ? 1 : 0 : 0;
            } else {
                opcode = value;
            }

            System.out.println("Opcode: " + opcode + " param1mode: " + param1mode + " param2mode: " + param2mode + " param3mode: " + param3mode);
            Scanner keyboard = new Scanner(System.in);
            switch (opcode) {
                case 1:
                    // 3 parameters: addand, addand, result location
                    int addand1 = param1mode == 1 ? codes.get(address + 1) : codes.get(codes.get(address + 1));
                    int addand2 = param2mode == 1 ? codes.get(address + 2) : codes.get(codes.get(address + 2));
                    codes.set(codes.get(address + 3), addand1 + addand2);
                    address +=4;
                    break;

                case 2:
                    // 3 parameters: multiplicand, multiplicand, result location
                    int multiplicand1 = param1mode == 1 ? codes.get(address + 1) : codes.get(codes.get(address + 1));
                    int multiplicand2 = param2mode == 1 ? codes.get(address + 2) : codes.get(codes.get(address + 2));
                    codes.set(codes.get(address + 3), multiplicand1 * multiplicand2);
                    address +=4;
                    break;

                case 3:
                    System.out.print("Enter an integer: ");
                    Integer input = keyboard.nextInt();
                    codes.set(codes.get(address + 1), input);
                    address +=2;
                    break;
                case 4:

                    int param1 = codes.get(address + 1);
                    if (param1mode == 1) {
                        System.out.println("Output: " + codes.get(address + 1));
                    } else {
                        System.out.println("Output: " + codes.get(codes.get(address + 1)));
                    }
                    address+=2;
                    break;
                case 5:
                    int determinant = param1mode == 1 ? codes.get(address + 1) : codes.get(codes.get(address + 1));
                    int destination = param2mode == 1 ? codes.get(address + 2) : codes.get(codes.get(address + 2));
                    if (determinant!=0) {
                        address=destination;
                    } else {
                        address+=3;
                    }
                    break;
                case 6:
                    determinant = param1mode == 1 ? codes.get(address + 1) : codes.get(codes.get(address + 1));
                    destination = param2mode == 1 ? codes.get(address + 2) : codes.get(codes.get(address + 2));
                    if (determinant==0) {
                        address=destination;
                    } else {
                        address+=3;
                    }
                    break;
                case 7:
                    int val1 = param1mode == 1 ? codes.get(address + 1) : codes.get(codes.get(address + 1));
                    int val2 = param2mode == 1 ? codes.get(address + 2) : codes.get(codes.get(address + 2));
                    destination = codes.get(address + 3);
                    if (val1 < val2)   codes.set(destination,1);
                    else codes.set(destination,0);
                    address+=4;
                    break;
                case 8:
                    val1 = param1mode == 1 ? codes.get(address + 1) : codes.get(codes.get(address + 1));
                    val2 = param2mode == 1 ? codes.get(address + 2) : codes.get(codes.get(address + 2));
                    destination = codes.get(address + 3);
                    if (val1 == val2)   codes.set(destination,1);
                    else codes.set(destination,0);
                    address+=4;
                    break;
                case 99:
                    System.exit(0);

                default:
                    System.out.println("error unkown command:" + opcode);
                    System.exit(1);
            }
        }
    }

    public int getFirst() {
        return codes.get(0);
    }

    public int getCheckSum() {
        return checkSum;
    }
}
