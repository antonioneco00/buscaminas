
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Buscaminas game = new Buscaminas(new char[5][5], new int[5][2], false, false);
            System.out.println("Bienvenido al buscaminas!");
            ArrayList<int[]> userChoices = new ArrayList<>();

            while (!game.isGameOver()) {
                System.out.println("Introduce una coordenada X:");
                int userX = sc.nextInt();

                System.out.println("Introduce una coordenada Y:");
                int userY = sc.nextInt();

                if (userY < 0 || userY >= game.getMatriz().length || userX < 0 || userX >= game.getMatriz().length) {
                    System.out.println("Coordenadas fuera de rango. Por favor, introduce coordenadas válidas.");
                    continue;
                }

                int[] userChoice = {userY, userX};
                userChoices.add(userChoice);

                System.out.println("Has introducido las coordenadas:\n[" + userY + ", " + userX + "]");

                if (!game.isInitialized()) {
                    game.setMinas(game.getMinas(), userY, userX);
                }

                game.setInitialized(true);

                /* System.out.println("Coordenadas de las minas:");

                for (int i = 0; i < 3; i++) {
                    System.out.println(Arrays.toString(game.getMinas()[i]));
                } */
                game.setMatriz(userChoices);
                game.setUnknownCells(userChoices);
                game.printScheme();
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
}
