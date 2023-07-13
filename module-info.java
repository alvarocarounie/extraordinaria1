
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

  class DeterminantCalculatorTest {
    public static class DeterminantCalculator {
        private int[][] matrix;

        public DeterminantCalculator(String filename) throws IOException {
            matrix = readMatrixFromFile(filename);
        }

        public int getDeterminant() {
            return calculateDeterminant(matrix);
        }

        private int[][] readMatrixFromFile(String filename) throws IOException {
            int[][] matrix = new int[3][3];

            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                int row = 0;
                while ((line = br.readLine()) != null && row < 3) {
                    String[] values = line.trim().split("\\s+");
                    for (int col = 0; col < 3; col++) {
                        matrix[row][col] = Integer.parseInt(values[col]);
                    }
                    row++;
                }
            }

            if (matrix.length != 3 || matrix[0].length != 3) {
                throw new IllegalArgumentException("La matriz debe ser de dimensiones 3x3.");
            }

            return matrix;
        }

        private int calculateDeterminant(int[][] matrix) {
            if (matrix.length != 3 || matrix[0].length != 3) {
                throw new IllegalArgumentException("La matriz debe ser de dimensiones 3x3.");
            }

            int determinant = 0;

            determinant += matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]);
            determinant -= matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]);
            determinant += matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

            return determinant;
        }
    }

	private static final String Assertions = null;
   //test
    public void testDeterminantCalculation() throws IOException {
        DeterminantCalculator calculator = new DeterminantCalculator("matrix.txt");
        int determinant = calculator.getDeterminant();
        Assertions.assertEquals(42, determinant);
    }

  //test
    public void testInvalidMatrixDimensions() {
        try {
            DeterminantCalculator calculator = new DeterminantCalculator("invalid_matrix.txt");
            Assertions.fail("No se lanzó una excepción para una matriz de dimensiones inválidas.");
        } catch (IOException e) {
            Assertions.fail("Se lanzó una excepción IOException en lugar de IllegalArgumentException para una matriz de dimensiones inválidas.");
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("La matriz debe ser de dimensiones 3x3.", e.getMessage());
        }

    }
}