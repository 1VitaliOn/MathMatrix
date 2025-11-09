package math.engine;

public final class Vector2D {

    private final float XComponent;
    private final float YComponent;

    public Vector2D(float x, float y) {
        this.XComponent = x;
        this.YComponent = y;
    }

    public Vector2D Add(Vector2D other) {
        return new Vector2D(
                this.XComponent + other.XComponent,
                this.YComponent + other.YComponent
        );
    }

    public Vector2D Subtract(Vector2D other) {
        return new Vector2D(
                this.XComponent - other.XComponent,
                this.YComponent - other.YComponent
        );
    }

    public Vector2D Multiply(float scalar) {
        return new Vector2D(
                this.XComponent * scalar,
                this.YComponent * scalar
        );
    }

    public Vector2D Divide(float scalar) {
        if (Math.abs(scalar) < 1e-12f) {
            throw new ArithmeticException("Деление на ноль невозможно");
        }
        return new Vector2D(
                this.XComponent / scalar,
                this.YComponent / scalar
        );
    }

    public float ComputeLength() {
        return (float) Math.sqrt(
                XComponent * XComponent +
                        YComponent * YComponent
        );
    }

    public Vector2D Normalize() {
        float length = ComputeLength();
        if (length < 1e-12f) {
            throw new ArithmeticException("Невозможно нормализовать нулевой вектор");
        }
        return Divide(length);
    }

    public float ComputeDotProduct(Vector2D other) {
        return this.XComponent * other.XComponent +
                this.YComponent * other.YComponent;
    }

    public float GetX() {
        return XComponent;
    }

    public float GetY() {
        return YComponent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2D other = (Vector2D) obj;
        return Math.abs(XComponent - other.XComponent) < 1e-6f &&
                Math.abs(YComponent - other.YComponent) < 1e-6f;
    }

    @Override
    public String toString() {
        return String.format("Vector2D(%.2f, %.2f)", XComponent, YComponent);
    }
}