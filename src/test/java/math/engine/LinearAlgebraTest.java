package math.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *Тесты для математической библиотеки
 */
public class LinearAlgebraTest {

    private static final float FLOAT_PRECISION = 0.001f;

    @Test
    public void testVector2DOperations() {
        Vector2D v1 = new Vector2D(3.0f, 4.0f);
        Vector2D v2 = new Vector2D(1.0f, 2.0f);

        // Сложение
        Vector2D sum = v1.Add(v2);
        assertEquals(4.0f, sum.GetX(), FLOAT_PRECISION);
        assertEquals(6.0f, sum.GetY(), FLOAT_PRECISION);

        // Длина
        assertEquals(5.0f, v1.ComputeLength(), FLOAT_PRECISION);

        // Нормализация
        Vector2D normalized = v1.Normalize();
        assertEquals(1.0f, normalized.ComputeLength(), FLOAT_PRECISION);
    }

    @Test
    public void testVector3DCrossProduct() {
        Vector3D v1 = new Vector3D(1.0f, 0.0f, 0.0f);
        Vector3D v2 = new Vector3D(0.0f, 1.0f, 0.0f);

        Vector3D cross = v1.ComputeCrossProduct(v2);
        assertEquals(new Vector3D(0.0f, 0.0f, 1.0f), cross);
    }

    @Test
    public void testMatrixInverse() {
        Matrix3x3 matrix = new Matrix3x3(new float[][]{
                {4, 7, 2},
                {3, 5, 1},
                {2, 3, 4}
        });

        Matrix3x3 inverse = matrix.ComputeInverse();
        Matrix3x3 product = matrix.Multiply(inverse);
        Matrix3x3 identity = Matrix3x3.CreateIdentity();

        assertTrue(product.equals(identity));
    }

    @Test
    public void testLinearSystemSolving() {
        Matrix3x3 A = new Matrix3x3(new float[][]{
                {2, 1, -1},
                {-3, -1, 2},
                {-2, 1, 2}
        });

        Vector3D b = new Vector3D(8.0f, -11.0f, -3.0f);
        Vector3D x = A.SolveLinearSystem(b);

        // Проверяем, что A*x = b
        Vector3D Ax = A.Multiply(x);
        assertTrue(Ax.equals(b));
    }

    @Test
    public void testErrorHandling() {
        // Деление на ноль
        assertThrows(ArithmeticException.class, () -> {
            Vector3D v = new Vector3D(1.0f, 2.0f, 3.0f);
            v.Divide(0.0f);
        });

        // Нормализация нулевого вектора
        assertThrows(ArithmeticException.class, () -> {
            Vector3D zero = new Vector3D(0.0f, 0.0f, 0.0f);
            zero.Normalize();
        });
    }
}