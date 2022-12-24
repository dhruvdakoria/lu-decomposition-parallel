# LU Decomposition

This code in `main.java` performs LU decomposition on a given matrix. LU decomposition is a way to decompose a matrix A into a lower triangular matrix L and an upper triangular matrix U such that A = L \* U.

## Description

The code provides both sequential and parallel implementations of LU decomposition. The parallel implementation uses Java streams to parallelize the decomposition process. The code also provides a method to validate the decomposition by multiplying L and U to get the original matrix back and a method to print the matrices L and U to corresponding files.

## Files

`main.java` - Main code file

Output Files:

- `InputMatrix.txt`
- `parallel-L.txt`
- `parallel-U.txt`
- `sequential-L.txt`
- `sequential-U.txt`

## Usage

To use the code, import the Main class and call either luDecompositionSequential(float[][] matrix, int n) or luDecompositionParallel(float[][] matrix, int n), where matrix is the matrix to be decomposed and n is the size of the matrix. The time taken for the decomposition process and the resulting matrices L and U will be printed to the console. The resulting matrices will also be written to corresponding files.

## Console Output

------------------------ RUN 1 -----------------------------

> java -classpath .:target/dependency/\* Main

Creating Input Matrix A and writing to file InputMatrix.txt

Performing Sequential LU Decomposition
Total time (Sequential LU decomposition): 79 ms.
L and U matrices are valid - sequential
Printing L and U matrices to corresponding files complete.

Performing Parallel LU Decomposition
Total time (Parallel LU decomposition): 123 ms.
L and U matrices are valid - parallel
Printing L and U matrices to corresponding files complete.

------------------------ RUN 2 -----------------------------

> java -classpath .:target/dependency/\* Main

Creating Input Matrix A and writing to file InputMatrix.txt

Performing Sequential LU Decomposition
Total time (Sequential LU decomposition): 57 ms.
L and U matrices are valid - sequential
Printing L and U matrices to corresponding files complete.

Performing Parallel LU Decomposition
Total time (Parallel LU decomposition): 201 ms.
L and U matrices are valid - parallel
Printing L and U matrices to corresponding files complete.

------------------------ RUN 3 -----------------------------

> java -classpath .:target/dependency/\* Main

Creating Input Matrix A and writing to file InputMatrix.txt

Performing Sequential LU Decomposition
Total time (Sequential LU decomposition): 9 ms.
L and U matrices are valid - sequential
Printing L and U matrices to corresponding files complete.

Performing Parallel LU Decomposition
Total time (Parallel LU decomposition): 187 ms.
L and U matrices are valid - parallel
Printing L and U matrices to corresponding files complete.
