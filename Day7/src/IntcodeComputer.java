import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

class IntcodeComputer implements Runnable {

    private int[] instructions;
    private int index;
    private Scanner reader;
    private final BlockingQueue<Integer> inputQueue;
    private final BlockingQueue<Integer> outputQueue;


    public IntcodeComputer(int[] instructions, BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue) {
        this.instructions = instructions;
        this.index = 0;
        this.reader = new Scanner(System.in);
        this.inputQueue=inputQueue;
        this.outputQueue=outputQueue;
    }

    @Override
    public void run(){
        //System.out.println(Thread.currentThread().getName()+" Input Queue"+ inputQueue.toString());
        String command;
        while(true){
            command = String.valueOf(this.instructions[this.index]);
            System.out.println(Thread.currentThread().getName()+" Command: " + command);
            if (command.endsWith("99")){
                System.out.println( Thread.currentThread().getName() + " Exited because of input 99");
                break;
            } else if (command.endsWith("1")){
                instruction1(command);
            } else if (command.endsWith("2")){
                instruction2(command);
            } else if (command.endsWith("3")){
                instruction3(command);
            } else if (command.endsWith("4")) {
                instruction4(command);
            } else if (command.endsWith("5")){
                instruction5(command);
            } else if (command.endsWith("6")) {
                instruction6(command);
            } else if (command.endsWith("7")){
                instruction7(command);
            } else if (command.endsWith("8")) {
                instruction8(command);
            } else {
                System.out.println("Invalid input, terminating");
                System.out.println("Index is " + this.index);
                System.out.println("Value is " + command);
                break;
            }
        }
    }
    private ArrayList<String> populateParameters(String command){
        ArrayList<String> parameters = new ArrayList<>();
        int length;
        for (int i = command.length() - 3; i >= 0; i--){
            parameters.add(String.valueOf(command.charAt(i)));
        }
        length = parameters.size();
        for (int i = 2; i > length; i--){
            parameters.add("0");
        }
        this.index++; // Index is now not on a value
        return parameters;
    }

    private ArrayList<Integer> populateValues(ArrayList<String> parameters){
        ArrayList<Integer> values = new ArrayList<>(parameters.size());
        for (String parameter : parameters) {
            if (parameter.equals("0")) {
                values.add(this.instructions[this.instructions[this.index]]);
            } else if (parameter.equals("1")) {
                values.add(this.instructions[this.index]);
            }
            this.index++;
        }
        return values;
    }

    private void instruction1(String command){
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Integer> values = populateValues(parameters);
        int pos = this.instructions[this.index];
        int sum = 0;
        for (int number : values){
            sum += number;
        }
        this.instructions[pos] = sum;
//        System.out.print("Running, thread: " + Thread.currentThread().getName());
//        System.out.println(" Writing " + sum + " to position " + pos);
        this.index++; //Index is now on next instruction
    }
    private void instruction2(String command){
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Integer> values = populateValues(parameters);
        int pos = this.instructions[this.index];
        int sum = 1;
        for (int number : values){
            sum *= number;
        }
        this.instructions[pos] = sum;
//        System.out.print("Running, thread: " + Thread.currentThread().getName());
//        System.out.println(" Writing " + sum + " to position " + pos);
        this.index++;
    }
    private void instruction3(String command){
        Integer input=99;
        System.out.println( Thread.currentThread().getName() + " Input Queue" +  inputQueue.toString());
        try {
            input=inputQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.index++;
        int pos = this.instructions[this.index];
        this.instructions[pos] = input;
        System.out.println(Thread.currentThread().getName()+" Got input from queue: " + input);
        this.index++;
    }
    private void instruction4(String command){
        ArrayList<String> parameters = populateParameters(command);
        int output;
        if (parameters.get(0).equals("0")){
            output = this.instructions[this.instructions[this.index]];
        } else {
            output = this.instructions[this.index];
        }
//        System.out.print("Running, thread: " + Thread.currentThread().getName());
//        System.out.println(" index is " + this.index);
        System.out.println(Thread.currentThread().getName()+" Output is: " + output);
        this.outputQueue.add(output);
        this.index++;
    }
    private void instruction5(String command){
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Integer> values = populateValues(parameters);
        if (values.get(0) != 0){
            this.index = values.get(1);
        }
    }
    private void instruction6(String command){
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Integer> values = populateValues(parameters);
        if (values.get(0) == 0){
            this.index = values.get(1);
        }
    }
    private void instruction7(String command){
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Integer> values = populateValues(parameters);
        int pos = this.instructions[this.index];
        int value = 0;
        if (values.get(0) < values.get(1)){
            value = 1;
        }
        this.instructions[pos] = value;
//        System.out.print("Running, thread: " + Thread.currentThread().getName());
//        System.out.println(" Writing " + value + " to position " + pos);
        this.index++;
    }
    private void instruction8(String command){
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Integer> values = populateValues(parameters);
        int pos = this.instructions[this.index];
        int value = 0;
        if (values.get(0).equals(values.get(1))){
            value = 1;
        }
        this.instructions[pos] = value;
//        System.out.print("Running, thread: " + Thread.currentThread().getName());
//        System.out.println(" Writing " + value + " to position " + pos);
        this.index++;
    }

    public int getExitCode(){
        return this.instructions[0];
    }
    public void printInstructions(){
        for (int i = 0; i < this.index; i++){
            System.out.println(this.instructions[i]);
        }
    }
}