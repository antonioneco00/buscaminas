
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {

    static Buscaminas game = new Buscaminas(new char[5][5], new int[3][2], false, false, new Random());

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Bienvenido al buscaminas!");

            while (!game.isGameOver()) {
                System.out.println("Introduce una coordenada X:");
                int userX = sc.nextInt();

                System.out.println("Introduce una coordenada Y:");
                int userY = sc.nextInt();

                int[] userChoice = {userX, userY};

                System.out.println("Has introducido las coordenadas:\n" + Arrays.toString(userChoice));

                if (!game.isInitialized()) {
                    game.setMinas(game.getMinas(), game.getRandom(), userX, userY);
                }

                game.setInitialized(true);

                System.out.println("Coordenadas de las minas:");

                for (int i = 0; i < 3; i++) {
                    System.out.println(Arrays.toString(game.getMinas()[i]));
                }

                game.setMatriz(userChoice);
                game.printScheme();
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }    
}
