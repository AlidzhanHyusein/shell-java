import java.io.File;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Set<String> builtins = Set.of("exit", "echo", "type");

        while (true) {
            System.out.print("$ ");
            System.out.flush();

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String arguments = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "exit" -> System.exit(0);
                case "echo" -> System.out.println(arguments);
                case "type" -> {
                    if(builtins.contains(arguments)) {
                        System.out.println(arguments +" is a shell builtin");
                        return;
                    }

                    String pathEnv = System.getenv("PATH");

                    if(pathEnv == null && pathEnv.isEmpty()) {
                        System.out.println(command + ": not found");
                        return;
                    }

                    String[] paths = pathEnv.split(File.pathSeparator);

                    for(String dir : paths){
                        File file = new File(dir,command);

                        if(file.exists() && file.isFile() && file.canExecute()){
                            System.out.println(command + " is " + file.getAbsolutePath());
                            return;
                        }
                    }
                    System.out.println(command + ": not found");
                }
                default -> System.err.println(command + ": command not found");
            }
        }
    }
}
