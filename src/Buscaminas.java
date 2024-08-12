
import java.util.Arrays;
import java.util.Random;

public class Buscaminas {

    private final char matriz[][];
    private final int minas[][];
    private boolean isInitialized;
    private boolean gameOver;
    private Random random;

    public Buscaminas(char[][] matriz, int[][] minas, boolean isInitialized, boolean gameOver, Random random) {
        this.matriz = matriz;
        this.minas = minas;
        this.isInitialized = isInitialized;
        this.gameOver = gameOver;
        this.random = random;
    }

    public char[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(int[] userChoice) {
        for (int i = 0; i < this.getMatriz().length; i++) {
            for (int j = 0; j < this.getMatriz()[0].length; j++) {
                boolean isMina = false;
                boolean isUserChoice = false;

                int[] celdaActual = {i, j};
                int nearbyMinas = 0;

                for (int[] array : this.getMinas()) {
                    int distToMinaX = Math.abs(array[0] - i);
                    int distToMinaY = Math.abs(array[1] - j);

                    if (Arrays.equals(array, celdaActual)) {
                        isMina = true;
                    } else if (distToMinaX <= 1 && distToMinaY <= 1) {
                        nearbyMinas++;
                    }
                }

                if (Arrays.equals(userChoice, celdaActual)) {
                    isUserChoice = true;
                }

                if (isMina) {
                    this.getMatriz()[i][j] = isUserChoice ? 'x' : '*';

                    if (isUserChoice) {
                        this.setGameOver(true);
                    }
                } else if (isUserChoice) {
                    matriz[i][j] = '+';
                } else if (matriz[i][j] != '+') {
                    matriz[i][j] = (char) (nearbyMinas + '0');
                }
            }
        }
    }

    public int[][] getMinas() {
        return minas;
    }

    public void setMinas(int[][] minas, Random random, int userX, int userY) {
        for (int i = 0; i < 3; i++) {
            int randomEjeX = random.nextInt(5);
            int randomEjeY = random.nextInt(5);

            int[] newMina = {randomEjeX, randomEjeY};

            boolean uniqueMinas = true;

            for (int[] array : this.getMinas()) {
                if (Arrays.equals(array, newMina)) {
                    i--;
                    System.out.println("La mina se repite.\nMina existente: " + Arrays.toString(array) + ". Mina generada: " + Arrays.toString(newMina) + "\nGenerando nueva mina...");
                    uniqueMinas = false;
                }
            }

            if (!uniqueMinas) {
                continue;
            }

            if (randomEjeX == userX && randomEjeY == userY) {
                i--;
                System.out.println("La mina coincide. X: " + randomEjeX + ". Y: " + randomEjeY);
                continue;
            }

            minas[i][0] = randomEjeX;
            minas[i][1] = randomEjeY;
        }
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void printScheme() {
        for (char[] array : this.matriz) {
            for (char celda : array) {
                System.out.print(celda + " ");
            }

            System.out.println();
        }

        if (this.isGameOver()) {
            System.out.println("\nFin del juego. Has explotado una mina.");
        }
    }
}
