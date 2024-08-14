
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Buscaminas {

    private final char matriz[][];
    private final int minas[][];
    private boolean isInitialized;
    private boolean gameOver;
    private final Random random;

    public Buscaminas(char[][] matriz, int[][] minas, boolean isInitialized, boolean gameOver) {
        this.matriz = matriz;
        this.minas = minas;
        this.isInitialized = isInitialized;
        this.gameOver = gameOver;
        this.random = new Random();
    }

    public char[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(ArrayList<int[]> userChoices) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                boolean isMina = false;
                boolean isUserChoice = false;

                int[] celdaActual = {i, j};
                int nearbyMinas = 0;

                for (int[] array : minas) {
                    int distToMinaY = Math.abs(array[0] - i);
                    int distToMinaX = Math.abs(array[1] - j);

                    if (Arrays.equals(array, celdaActual)) {
                        isMina = true;
                    } else if (distToMinaY <= 1 && distToMinaX <= 1) {
                        nearbyMinas++;
                    }
                }

                for (int[] choice : userChoices) {
                    if (Arrays.equals(choice, celdaActual)) {
                        isUserChoice = true;
                    }
                }

                if (isMina) {
                    matriz[i][j] = isUserChoice ? 'x' : '*';

                    if (isUserChoice) {
                        setGameOver(true);
                    }
                } else {
                    matriz[i][j] = (char) (nearbyMinas + '0');
                }
            }
        }
    }

    public int[][] getMinas() {
        return minas;
    }

    public void setMinas(int[][] minas, int userY, int userX) {
        for (int i = 0; i < minas.length; i++) {
            int randomEjeY = random.nextInt(matriz.length);
            int randomEjeX = random.nextInt(matriz.length);

            int[] newMina = {randomEjeY, randomEjeX};

            boolean uniqueMinas = true;

            for (int[] array : minas) {
                if (Arrays.equals(array, newMina)) {
                    i--;
                    System.out.println("La mina se repite.\nMina existente: " + Arrays.toString(array) + ". Mina generada: " + Arrays.toString(newMina) + "\nGenerando nueva mina...");
                    uniqueMinas = false;
                }
            }

            if (!uniqueMinas) {
                continue;
            }

            if (randomEjeY == userY && randomEjeX == userX) {
                i--;
                System.out.println("La mina coincide. X: " + randomEjeX + ". Y: " + randomEjeY);
                continue;
            }

            minas[i][0] = randomEjeY;
            minas[i][1] = randomEjeX;
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

    public void printScheme() {
        int countUnknown = 0;

        for (char[] array : matriz) {
            for (char celda : array) {
                System.out.print(celda + " ");
                
                if (celda == '?') {
                    countUnknown++;
                }
            }

            System.out.println();
        }

        if (gameOver) {
            System.out.println("\nFin del juego. Has explotado una mina.");

            return;
        }

        if (countUnknown == minas.length) {
            setGameOver(true);

            System.out.println("\nFelicidades! Has terminado el juego :)");
        }
    }

    public void setUnknownCells(ArrayList<int[]> userChoices) {
        /* for (int[] choice : userChoices) {
            System.out.println(Arrays.toString(choice));
        } */

        for (int x = 0; x < matriz.length; x++) {
            for (int y = 0; y < matriz[0].length; y++) {
                /* if (matriz[x][y] != '0' && matriz[x][y] != '+') {
                    System.out.println("La coordenada [" + x + ", " + y + "] puede ser unknown");
                }

                if (x == 0 && y == 0) {
                    System.out.println("Celdas checkeadas para [0, 0]:");
                } */

                boolean setToUnknown = true;
                int[] currCell = {x, y};

                for (int i = -1; i < 2; i++) {
                    if (x + i < 0 || x + i > matriz.length - 1) {
                        continue;
                    }

                    for (int j = -1; j < 2; j++) {
                        if (y + j < 0 || y + j > matriz[0].length - 1) {
                            continue;
                        }

                        /* if (x == 0 && y == 0) {
                            System.out.println("[" + (x + i) + ", " + (y + j) + "]: " + matriz[x + i][y + j]);
                        } */
                        if (matriz[x + i][y + j] == '0') {
                            setToUnknown = false;
                        }
                    }
                }

                for (int[] choice : userChoices) {
                    if (Arrays.equals(choice, currCell)) {
                        setToUnknown = false;
                    }
                }

                if (setToUnknown) {
                    matriz[x][y] = '?';
                }
            }
        }
    }
}
