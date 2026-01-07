import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Set<String> builtins = Set.of("exit", "echo", "type");

        while (true) {
            System.out.print("$ ");
            System.out.flush();

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] tokens = input.split("\\s+");
            String command = tokens[0];

            switch (command) {
                case "exit" -> System.exit(0);

                case "echo" -> {
                    if (tokens.length > 1) {
                        System.out.println(String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length)));
                    } else {
                        System.out.println();
                    }
                }

                case "type" -> {
                    if (tokens.length < 2) {
                        System.out.println("type: missing argument");
                        continue;
                    }

                    String target = tokens[1];

                    if (builtins.contains(target)) {
                        System.out.println(target + " is a shell builtin");
                        continue;
                    }

                    String pathEnv = System.getenv("PATH");
                    if (pathEnv == null || pathEnv.isEmpty()) {
                        System.out.println(target + ": not found");
                        continue;
                    }

                    boolean found = false;
                    for (String dir : pathEnv.split(File.pathSeparator)) {
                        File file = new File(dir, target);
                        if (file.exists() && file.isFile() && file.canExecute()) {
                            System.out.println(target + " is " + file.getAbsolutePath());
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println(target + ": not found");
                    }
                }

                default -> {
                    String pathEnv = System.getenv("PATH");
                    if (pathEnv == null || pathEnv.isEmpty()) {
                        System.err.println(command + ": command not found");
                        continue;
                    }

                    File executable = null;
                    for (String dir : pathEnv.split(File.pathSeparator)) {
                        File candidate = new File(dir, command);
                        if (candidate.exists() && candidate.isFile() && candidate.canExecute()) {
                            executable = candidate;
                            break;
                        }
                    }

                    if (executable == null) {
                        System.err.println(command + ": command not found");
                        continue;
                    }

                    // Build argv list
                    List<String> cmd = new ArrayList<>();
                    cmd.add(command); // argv[0] MUST be command name
                    cmd.addAll(Arrays.asList(tokens).subList(1, tokens.length));

                    ProcessBuilder pb = new ProcessBuilder(cmd);

                    // 🔑 IMPORTANT: run in executable's directory
                    pb.directory(executable.getParentFile());

                    pb.inheritIO();

                    try {
                        pb.start().waitFor();
                    } catch (Exception e) {
                        System.err.println("Error executing command");
                    }
                }


            }
        }
    }
}
