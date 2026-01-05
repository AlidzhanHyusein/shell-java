import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        Scanner scanner = new Scanner(System.in);
        int a = 2;
        int b = 2;

        while(true){
            System.out.print("$ ");
            String input = scanner.next();

            if(input.equals("exit")){
                break;
            }
            if(input.equals("echo")){
                input = scanner.nextLine();
                System.out.println(input);
                continue;
            }

            System.out.println(input + ": command not found");
        }
    }
}
