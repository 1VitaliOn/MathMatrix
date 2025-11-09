package math.engine;

/**
 * 🎯 Фасад для линейной алгебры - единая точка доступа ко всем операциям
 * 📊 Объединяет векторы и матрицы в согласованный API
 */
public final class LinearAlgebraEngine {

    private LinearAlgebraEngine() {
        // Утилитный класс - запрет создания экземпляров
    }

    // ==================== ВЕКТОРНЫЕ ОПЕРАЦИИ ====================

    public static Vector2D CreateVector2D(float x, float y) {
        return new Vector2D(x, y);
    }

    public static Vector3D CreateVector3D(float x, float y, float z) {
        return new Vector3D(x, y, z);
    }

    public static Vector4D CreateVector4D(float x, float y, float z, float w) {
        return new Vector4D(x, y, z, w);
    }

    public static Vector4D CreateVector4DFrom3D(Vector3D vector, float w) {
        return new Vector4D(vector, w);
    }

    // ==================== МАТРИЧНЫЕ ОПЕРАЦИИ ====================

    public static Matrix3x3 CreateIdentityMatrix3x3() {
        return Matrix3x3.CreateIdentity();
    }

    public static Matrix3x3 CreateZeroMatrix3x3() {
        return Matrix3x3.CreateZero();
    }

    public static Matrix4x4 CreateIdentityMatrix4x4() {
        return Matrix4x4.CreateIdentity();
    }

    public static Matrix4x4 CreateZeroMatrix4x4() {
        return Matrix4x4.CreateZero();
    }

    public static Matrix4x4 CreateTranslationMatrix(float x, float y, float z) {
        return Matrix4x4.CreateTranslation(x, y, z);
    }

    // ==================== ПРОВЕРКИ И УТИЛИТЫ ====================

    public static boolean AreVectorsEqual(Vector3D v1, Vector3D v2) {
        return v1.equals(v2);
    }

    public static boolean AreMatricesEqual(Matrix3x3 m1, Matrix3x3 m2) {
        return m1.equals(m2);
    }

    public static float ComputeAngleBetweenVectors(Vector3D v1, Vector3D v2) {
        float dotProduct = v1.ComputeDotProduct(v2);
        float lengths = v1.ComputeLength() * v2.ComputeLength();

        if (Math.abs(lengths) < 1e-12f) {
            throw new ArithmeticException("Невозможно вычислить угол для нулевых векторов");
        }

        float cosAngle = dotProduct / lengths;
        cosAngle = Math.max(-1.0f, Math.min(1.0f, cosAngle)); // Ограничение для арккосинуса

        return (float) Math.acos(cosAngle);
    }

    public static Matrix4x4 CreateRotationMatrixX(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        return new Matrix4x4(new float[][]{
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix4x4 CreateRotationMatrixY(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        return new Matrix4x4(new float[][]{
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix4x4 CreateRotationMatrixZ(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        return new Matrix4x4(new float[][]{
                {cos, -sin, 0, 0},
                {sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix4x4 CreateScaleMatrix(float scaleX, float scaleY, float scaleZ) {
        return new Matrix4x4(new float[][]{
                {scaleX, 0, 0, 0},
                {0, scaleY, 0, 0},
                {0, 0, scaleZ, 0},
                {0, 0, 0, 1}
        });
    }
}