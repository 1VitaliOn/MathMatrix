package math.engine;

public final class Matrix4x4 {

    private final float[][] Elements;

    public Matrix4x4(float[][] elements) {
        ValidateMatrixDimensions(elements, 4, 4);
        this.Elements = DeepCopyArray(elements);
    }

    public static Matrix4x4 CreateIdentity() {
        return new Matrix4x4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix4x4 CreateZero() {
        return new Matrix4x4(new float[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    public static Matrix4x4 CreateTranslation(float x, float y, float z) {
        return new Matrix4x4(new float[][]{
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}
        });
    }

    public Matrix4x4 Add(Matrix4x4 other) {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.Elements[i][j] + other.Elements[i][j];
            }
        }
        return new Matrix4x4(result);
    }

    public Matrix4x4 Subtract(Matrix4x4 other) {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.Elements[i][j] - other.Elements[i][j];
            }
        }
        return new Matrix4x4(result);
    }

    public Matrix4x4 Multiply(Matrix4x4 other) {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += this.Elements[i][k] * other.Elements[k][j];
                }
                result[i][j] = sum;
            }
        }
        return new Matrix4x4(result);
    }

    public Vector4D Multiply(Vector4D vector) {
        float x = Elements[0][0] * vector.GetX() +
                Elements[0][1] * vector.GetY() +
                Elements[0][2] * vector.GetZ() +
                Elements[0][3] * vector.GetW();

        float y = Elements[1][0] * vector.GetX() +
                Elements[1][1] * vector.GetY() +
                Elements[1][2] * vector.GetZ() +
                Elements[1][3] * vector.GetW();

        float z = Elements[2][0] * vector.GetX() +
                Elements[2][1] * vector.GetY() +
                Elements[2][2] * vector.GetZ() +
                Elements[2][3] * vector.GetW();

        float w = Elements[3][0] * vector.GetX() +
                Elements[3][1] * vector.GetY() +
                Elements[3][2] * vector.GetZ() +
                Elements[3][3] * vector.GetW();

        return new Vector4D(x, y, z, w);
    }

    public Vector3D Multiply(Vector3D vector) {
        Vector4D homogeneous = new Vector4D(vector, 1.0f);
        Vector4D result = Multiply(homogeneous);
        return result.ToVector3D();
    }

    public Matrix4x4 Transpose() {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.Elements[j][i];
            }
        }
        return new Matrix4x4(result);
    }

    public float ComputeDeterminant() {
        // Реализация через разложение по первой строке
        float det = 0;

        for (int j = 0; j < 4; j++) {
            float cofactor = ComputeCofactor(0, j);
            det += Elements[0][j] * cofactor;
        }

        return det;
    }

    private float ComputeCofactor(int row, int col) {
        float[][] minor = new float[3][3];
        int minorRow = 0;

        for (int i = 0; i < 4; i++) {
            if (i == row) continue;
            int minorCol = 0;
            for (int j = 0; j < 4; j++) {
                if (j == col) continue;
                minor[minorRow][minorCol] = Elements[i][j];
                minorCol++;
            }
            minorRow++;
        }

        Matrix3x3 minorMatrix = new Matrix3x3(minor);
        float sign = ((row + col) % 2 == 0) ? 1 : -1;
        return sign * minorMatrix.ComputeDeterminant();
    }

    public Matrix4x4 ComputeInverse() {
        float det = ComputeDeterminant();
        if (Math.abs(det) < 1e-12f) {
            throw new ArithmeticException("Матрица вырождена, обратной не существует");
        }

        float[][] result = new float[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float cofactor = ComputeCofactor(i, j);
                // Транспонирование кофакторов для получения присоединенной матрицы
                result[j][i] = cofactor / det;
            }
        }

        return new Matrix4x4(result);
    }

    public float GetElement(int row, int col) {
        ValidateIndices(row, col);
        return Elements[row][col];
    }

    private void ValidateMatrixDimensions(float[][] matrix, int rows, int cols) {
        if (matrix == null || matrix.length != rows) {
            throw new IllegalArgumentException("Неверная размерность матрицы");
        }
        for (float[] row : matrix) {
            if (row == null || row.length != cols) {
                throw new IllegalArgumentException("Неверная размерность матрицы");
            }
        }
    }

    private void ValidateIndices(int row, int col) {
        if (row < 0 || row >= 4 || col < 0 || col >= 4) {
            throw new IllegalArgumentException("Индексы выходят за границы матрицы");
        }
    }

    private float[][] DeepCopyArray(float[][] original) {
        float[][] copy = new float[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Matrix4x4 other = (Matrix4x4) obj;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Math.abs(Elements[i][j] - other.Elements[i][j]) >= 1e-6f) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix4x4:\n");
        for (int i = 0; i < 4; i++) {
            sb.append("[ ");
            for (int j = 0; j < 4; j++) {
                sb.append(String.format("%8.3f ", Elements[i][j]));
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}