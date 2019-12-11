import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class IntcodeComputer implements Runnable {

    private BigDecimal[] instructions;
    private int index;
    private int relativeBase = 0;

    private int[] commandModes = new int[]{0, 0, 0};
    private final BlockingQueue<BigDecimal> inputQueue;
    private final BlockingQueue<BigDecimal> outputQueue;


    public IntcodeComputer(int[] instructionsIn, BlockingQueue<BigDecimal> inputQueue, BlockingQueue<BigDecimal> outputQueue) {
        instructions = new BigDecimal[10000];
        Arrays.fill(instructions, BigDecimal.ZERO);
        for (int i = 0; i < instructionsIn.length; i++) {
            this.instructions[i] = new BigDecimal(instructionsIn[i]);
        }
        this.index = 0;

        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;

    }

    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getName()+" Input Queue"+ inputQueue.toString());
        String command;
        boolean finished = false;
        while (!finished) {

            command = String.valueOf(this.instructions[this.index++]);

            int currentCommand = setCommand(command);
            System.out.println(Thread.currentThread().getName() + " Command: " + currentCommand);

            switch (currentCommand) {
                case 99:
                    System.out.println(Thread.currentThread().getName() + " Exited because of input 99");
                    finished = true;
                    break;
                case 1:
                    instruction1();
                    break;
                case 2:
                    instruction2();
                    break;
                case 3:
                    instruction3();
                    break;
                case 4:
                    instruction4();
                    break;
                case 5:
                    instruction5();
                    break;
                case 6:
                    instruction6();
                    break;
                case 7:
                    instruction7();
                    break;
                case 8:
                    instruction8();
                    break;
                case 9:
                    instruction9();
                    break;
                default:
                    System.out.println("Invalid input, terminating");
                    System.out.println("Index is " + this.index);
                    System.out.println("Value is " + command);
                    finished = true;
                    break;
            }
        }
    }

    private void instruction1() {

        BigDecimal number = readMemory(commandModes[0], instructions[this.index++]);
        BigDecimal sum = readMemory(commandModes[1], instructions[this.index++]);
        BigDecimal pos = readMemory(1, instructions[this.index++]);
        sum = sum.add(number);
        this.instructions[pos.intValue()] = sum;
        System.out.print("Running, thread: " + Thread.currentThread().getName());
        System.out.println(" Writing " + sum + " to position " + pos);
    }

    private void instruction2() {

        BigDecimal number = readMemory(commandModes[0], instructions[this.index++]);
        BigDecimal mult = readMemory(commandModes[1], instructions[this.index++]);
        BigDecimal pos = readMemory(1, instructions[this.index++]);
        mult = mult.multiply(number);
        this.instructions[pos.intValue()] = mult;
        System.out.print("Running, thread: " + Thread.currentThread().getName());
        System.out.println(" Writing " + mult + " to position " + pos);
    }

    private void instruction3() {
        BigDecimal input = BigDecimal.ZERO;
        System.out.println(Thread.currentThread().getName() + " Input Queue" + inputQueue.toString());
        try {
            input = inputQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BigDecimal pos = readMemory(1, instructions[this.index++]);
        this.instructions[pos.intValue()] = input;
        System.out.println(Thread.currentThread().getName() + " Got input from queue: " + input);
    }

    private void instruction4() {

        BigDecimal output = readMemory(commandModes[0], instructions[this.index++]);
        System.out.println(Thread.currentThread().getName()+" index is " + this.index);
        System.out.println(Thread.currentThread().getName() + " Output is: " + output);
        this.outputQueue.add(output);
    }

    private void instruction5() {

        BigDecimal operand = readMemory(commandModes[0], instructions[this.index++]);
        BigDecimal target = readMemory(commandModes[1], instructions[this.index++]);
        if (operand.equals(BigDecimal.ZERO)) {
            this.index = target.intValue();
        }
    }

    private void instruction6() {

        BigDecimal operand = readMemory(commandModes[0], instructions[this.index++]);
        BigDecimal target = readMemory(commandModes[1], instructions[this.index++]);
        if (operand.equals(BigDecimal.ZERO)) {
            this.index = target.intValue();
        }
    }

    private void instruction7() {

        BigDecimal param1 = readMemory(commandModes[0], instructions[this.index++]);
        BigDecimal param2 = readMemory(commandModes[1], instructions[this.index++]);
        BigDecimal pos = readMemory(1, instructions[this.index++]);
        BigDecimal value = BigDecimal.ZERO;
        if (param1.compareTo(param2) <= 0) {
            value = BigDecimal.ONE;
        }
        this.instructions[pos.intValue()] = value;
        System.out.print("Running, thread: " + Thread.currentThread().getName());
        System.out.println(" Writing " + value + " to position " + pos);
    }

    private void instruction8() {

        BigDecimal param1 = readMemory(commandModes[0], instructions[this.index++]);
        BigDecimal param2 = readMemory(commandModes[1], instructions[this.index++]);
        BigDecimal pos = readMemory(1, instructions[this.index++]);
        BigDecimal value = BigDecimal.ZERO;
        if (param1.compareTo(param2) == 0) {
            value = BigDecimal.ONE;
        }
        this.instructions[pos.intValue()] = value;
        System.out.print(Thread.currentThread().getName()+ " Writing " + value + " to position " + pos);
    }

    private void instruction9() {

        BigDecimal param1 = readMemory(commandModes[0], instructions[this.index++]);
        this.relativeBase += param1.intValue();
        System.out.print("Running, thread: " + Thread.currentThread().getName());
        System.out.println(" Writing " + param1 + " to relativeBase ");
    }

    // reads memory address provided by the argument or write to depending on mode and argument
    public BigDecimal readMemory(int mode, BigDecimal argument) {
        switch (mode) {
            case 0:  // position mode - argument is an absolute address
                return this.instructions[argument.intValue()];

            case 1: // immediate mode - return argument as value
                return argument;

            case 2: // offset mode - return the value at address relative base, offset by parameter
                return this.instructions[relativeBase + argument.intValue()];

            default:
                System.out.println("Error invalid memory mode");
                break;
        }
        return BigDecimal.ZERO;
    }

    public int setCommand(String command) {
        // work out the length of the command and select rigthtmost two digits.
        int cmdLen = command.length();
        int retVal = 99;
        switch (cmdLen) {
            case 1:
            case 2:
                retVal = Integer.parseInt(command);
                break;
            case 3:
                retVal = Integer.parseInt(command.substring(1));
                this.commandModes[0] = Integer.parseInt(command.substring(0, 1));
                break;
            case 4:
                retVal = Integer.parseInt(command.substring(2));
                this.commandModes[1] = Integer.parseInt((command.substring(0, 1)));
                this.commandModes[0] = Integer.parseInt((command.substring(1, 2)));
                break;
            case 5:
                retVal = Integer.parseInt(command.substring(3));
                this.commandModes[2] = Integer.parseInt((command.substring(0, 1)));
                this.commandModes[1] = Integer.parseInt((command.substring(1, 2)));
                this.commandModes[0] = Integer.parseInt((command.substring(2, 3)));
        }
        System.out.println("ParamModes" + Arrays.toString(commandModes));
        System.out.println("Command:" + retVal);
        return retVal;
    }


    public void printInstructions() {
        for (int i = 0; i < this.index; i++) {
            System.out.println(this.instructions[i]);
        }
    }

    // tests:
    public static void main(String[] args) {
        int[] testCommands = {1, 02, 103, 1004, 10005, 11006, 11107, 11118};
        BlockingQueue<BigDecimal> input = new LinkedBlockingQueue<BigDecimal>();
        BlockingQueue<BigDecimal> output = new LinkedBlockingQueue<BigDecimal>();
        IntcodeComputer test = new IntcodeComputer(testCommands, input, output);
        for (int command : testCommands) {
            test.setCommand(String.valueOf(command));
        }
        // test command modes
        System.out.println(test.readMemory(0, new BigDecimal(7)));
        System.out.println(test.readMemory(1, new BigDecimal(7)));
        System.out.println(test.readMemory(2, new BigDecimal(6)));
        // test that reports itself and exits
        int[] testCommands2 = {109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99};
        IntcodeComputer test2 = new IntcodeComputer(testCommands2, input, output);
        test2.run();

        while (output.size() > 0) {
            String outputStr = output.remove().toString() + ",";
            System.out.println("Output: " + outputStr);
        }

        int [] testCommands3 = {1102,34915192,34915192,7,4,7,99,0};
        IntcodeComputer test3 = new IntcodeComputer(testCommands3, input, output);
        test3.run();

        while (output.size() > 0) {
            String outputStr = output.remove().toString() + ",";
            System.out.println("Output: " + outputStr);
        }
    }
}