package math.engine;

public final class Matrix3x3 {

    private final float[][] Elements;

    public Matrix3x3(float[][] elements) {
        ValidateMatrixDimensions(elements, 3, 3);
        this.Elements = DeepCopyArray(elements);
    }

    public static Matrix3x3 CreateIdentity() {
        return new Matrix3x3(new float[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
    }

    public static Matrix3x3 CreateZero() {
        return new Matrix3x3(new float[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
    }

    public Matrix3x3 Add(Matrix3x3 other) {
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.Elements[i][j] + other.Elements[i][j];
            }
        }
        return new Matrix3x3(result);
    }

    public Matrix3x3 Subtract(Matrix3x3 other) {
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.Elements[i][j] - other.Elements[i][j];
            }
        }
        return new Matrix3x3(result);
    }

    public Matrix3x3 Multiply(Matrix3x3 other) {
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                float sum = 0;
                for (int k = 0; k < 3; k++) {
                    sum += this.Elements[i][k] * other.Elements[k][j];
                }
                result[i][j] = sum;
            }
        }
        return new Matrix3x3(result);
    }

    public Vector3D Multiply(Vector3D vector) {
        float x = Elements[0][0] * vector.GetX() +
                Elements[0][1] * vector.GetY() +
                Elements[0][2] * vector.GetZ();

        float y = Elements[1][0] * vector.GetX() +
                Elements[1][1] * vector.GetY() +
                Elements[1][2] * vector.GetZ();

        float z = Elements[2][0] * vector.GetX() +
                Elements[2][1] * vector.GetY() +
                Elements[2][2] * vector.GetZ();

        return new Vector3D(x, y, z);
    }

    public Matrix3x3 Transpose() {
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.Elements[j][i];
            }
        }
        return new Matrix3x3(result);
    }

    public float ComputeDeterminant() {
        float a = Elements[0][0], b = Elements[0][1], c = Elements[0][2];
        float d = Elements[1][0], e = Elements[1][1], f = Elements[1][2];
        float g = Elements[2][0], h = Elements[2][1], i = Elements[2][2];

        return a * (e * i - f * h) -
                b * (d * i - f * g) +
                c * (d * h - e * g);
    }

    public Matrix3x3 ComputeInverse() {
        float det = ComputeDeterminant();
        if (Math.abs(det) < 1e-12f) {
            throw new ArithmeticException("Матрица вырождена, обратной не существует");
        }

        float[][] result = new float[3][3];

        float a = Elements[0][0], b = Elements[0][1], c = Elements[0][2];
        float d = Elements[1][0], e = Elements[1][1], f = Elements[1][2];
        float g = Elements[2][0], h = Elements[2][1], i = Elements[2][2];

        result[0][0] = (e * i - f * h) / det;
        result[0][1] = (c * h - b * i) / det;
        result[0][2] = (b * f - c * e) / det;

        result[1][0] = (f * g - d * i) / det;
        result[1][1] = (a * i - c * g) / det;
        result[1][2] = (c * d - a * f) / det;

        result[2][0] = (d * h - e * g) / det;
        result[2][1] = (b * g - a * h) / det;
        result[2][2] = (a * e - b * d) / det;

        return new Matrix3x3(result);
    }

    public Vector3D SolveLinearSystem(Vector3D vector) {
        Matrix3x3 inverse = ComputeInverse();
        return inverse.Multiply(vector);
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
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
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
        Matrix3x3 other = (Matrix3x3) obj;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
        sb.append("Matrix3x3:\n");
        for (int i = 0; i < 3; i++) {
            sb.append("[ ");
            for (int j = 0; j < 3; j++) {
                sb.append(String.format("%8.3f ", Elements[i][j]));
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}