import java.util.stream.IntStream;
import java.io.PrintWriter;

class Main {
  // Perform Sequential LU Decomposition
  static void luDecompositionSequential(float[][] matrix, int n) {
    float[][] L = new float[n][n];
    float[][] U = new float[n][n];
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < n; i++) {
      // form upper triangluar matrix
      for (int k = i; k < n; k++) {
        float sum = 0;
        for (int j = 0; j < i; j++) {
          sum += (L[i][j] * U[j][k]);
        }
        U[i][k] = matrix[i][k] - sum;
      }

      // form lower triangluar matrix
      for (int k = i; k < n; k++) {
        if (i == k) {
          L[i][i] = 1;
        } else {
          float sum = 0;
          for (int j = 0; j < i; j++) {
            sum += (L[k][j] * U[j][i]);
          }
          L[k][i] = (matrix[k][i] - sum) / U[i][i];
        }
      }
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Total time (Sequential LU decomposition): " + (endTime - startTime) + " ms.");
    validateMatrix(L, U, matrix, n, "sequential");
    printMatrixToFile(L, U, n, "sequential");
    System.out.println("Printing L and U matrices to corresponding files complete.");
  }

  // Perform Parallel LU Decomposition
  static void luDecompositionParallel(float[][] matrix, int n) {
    float[][] L = new float[n][n];
    float[][] U = new float[n][n];
    long startTime = System.currentTimeMillis();

    // Matrix decomposition stream
    IntStream.rangeClosed(0, n - 1).forEach(i -> {
      // Form upper triangular matrix using a parallel integer stream
      IntStream.rangeClosed(i, n - 1).parallel().forEach(k -> {
        float sum = 0;
        for (int j = 0; j < i; j++) {
          sum += (L[i][j] * U[j][k]);
        }
        U[i][k] = matrix[i][k] - sum;
      });

      // Form lower triangular matrix using a parallel integer stream
      IntStream.rangeClosed(i, n - 1).parallel().forEach(k -> {
        if (i == k) {
          L[i][i] = 1;
        } else {
          float sum = 0;
          for (int j = 0; j < i; j++) {
            sum += (L[k][j] * U[j][i]);
          }
          L[k][i] = (matrix[k][i] - sum) / U[i][i];
        }
      });
    });

    long endTime = System.currentTimeMillis();
    System.out.println("Total time (Parallel LU decomposition): " + (endTime - startTime) + " ms.");

    validateMatrix(L, U, matrix, n, "parallel");
    printMatrixToFile(L, U, n, "parallel");
    System.out.println("Printing L and U matrices to corresponding files complete.");
  }

  // Validate L and U matrices with the original matrix
  static void validateMatrix(float[][] L, float[][] U, float[][] matrix, int n, String type) {
    float[][] lu = new float[n][n];

    // Multiply L and U to get the original matrix back
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          lu[i][j] += L[i][k] * U[k][j];
        }
      }
    }

    // Check if the original matrix is equal to the result of multiplying L and U
    boolean isValid = true;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (Math.abs(matrix[i][j] - lu[i][j]) > 0.001) {
          isValid = false;
          break;
        }
      }
    }

    if (isValid) {
      System.out.println("L and U matrices are valid - " + type);
    } else {
      System.out.println("L and U matrices are not valid - " + type);
    }
  }

  // Print the result to a file for comparison (sequential vs parallel - both L
  // and U matrices)
  static void printMatrixToFile(float[][] L, float[][] U, int n, String type) {
    try {
      PrintWriter writer = new PrintWriter(type + "-L.txt", "UTF-8");
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          writer.print(String.format("%.1f", L[i][j]) + "\t");
        }
        writer.println();
      }
      writer.close();
      writer = new PrintWriter(type + "-U.txt", "UTF-8");
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          writer.print(String.format("%.1f", U[i][j]) + "\t");
        }
        writer.println();
      }
      writer.close();
    } catch (Exception e) {
      System.out.println("Error writing to file " + e);
    }
  }
  
  // Main method
  public static void main(String args[]) {
    int matrixSize = 100;
    float[][] matrixA = new float[matrixSize][matrixSize];
    System.out.println("Creating Input Matrix A and writing to file InputMatrix.txt");

    // Create random matrix
    try {
      PrintWriter writer = new PrintWriter("InputMatrix.txt", "UTF-8");
      for (int i = 0; i < matrixSize; i++) {
        for (int j = 0; j < matrixSize; j++) {
          matrixA[i][j] = (int) (Math.random() * 10);
          writer.print(String.format("%.1f", matrixA[i][j]) + "\t");
        }
        writer.println();
      }
      writer.close();
    } catch (Exception e) {
      System.out.println("Error writing to file InputMatrix.txt " + e);
    }
    
    // Perform sequential LU decomposition.
    System.out.println();
    System.out.println();
    System.out.println("Performing Sequential LU Decomposition");
    luDecompositionSequential(matrixA, matrixSize);
    // Perform parallel LU decomposition.
    System.out.println();
    System.out.println();
    System.out.println("Performing Parallel LU Decomposition");
    luDecompositionParallel(matrixA, matrixSize);
  }

}