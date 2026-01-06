import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        Scanner scanner = new Scanner(System.in);


        while(true){
            System.out.print("$ ");
            System.out.flush();

            String input = scanner.nextLine();

            String[] commands = {"exit","echo","type"};

            String[] parts = input.split(" ",2);
            String command = parts[0];
            String arguments = parts.length > 1 ? parts[1] :"";

            switch (command){
                case "exit" -> System.exit(0);
                case "echo" -> System.out.println(arguments);
                case "type" -> System.out.println(Arrays.stream(commands).anyMatch(c -> c.equals(arguments)) ? arguments + " is a shell builtin" :   arguments + ": not found");
                default -> System.err.printf("%s: command not found%n",input);

            }

        }
    }
}
