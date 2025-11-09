package math.engine;

public final class Vector3D {

    private final float XComponent;
    private final float YComponent;
    private final float ZComponent;

    public Vector3D(float x, float y, float z) {
        this.XComponent = x;
        this.YComponent = y;
        this.ZComponent = z;
    }

    public Vector3D Add(Vector3D other) {
        return new Vector3D(
                this.XComponent + other.XComponent,
                this.YComponent + other.YComponent,
                this.ZComponent + other.ZComponent
        );
    }

    public Vector3D Subtract(Vector3D other) {
        return new Vector3D(
                this.XComponent - other.XComponent,
                this.YComponent - other.YComponent,
                this.ZComponent - other.ZComponent
        );
    }

    public Vector3D Multiply(float scalar) {
        return new Vector3D(
                this.XComponent * scalar,
                this.YComponent * scalar,
                this.ZComponent * scalar
        );
    }

    public Vector3D Divide(float scalar) {
        if (Math.abs(scalar) < 1e-12f) {
            throw new ArithmeticException("Деление на ноль невозможно");
        }
        return new Vector3D(
                this.XComponent / scalar,
                this.YComponent / scalar,
                this.ZComponent / scalar
        );
    }

    public float ComputeLength() {
        return (float) Math.sqrt(
                XComponent * XComponent +
                        YComponent * YComponent +
                        ZComponent * ZComponent
        );
    }

    public Vector3D Normalize() {
        float length = ComputeLength();
        if (length < 1e-12f) {
            throw new ArithmeticException("Невозможно нормализовать нулевой вектор");
        }
        return Divide(length);
    }

    public float ComputeDotProduct(Vector3D other) {
        return this.XComponent * other.XComponent +
                this.YComponent * other.YComponent +
                this.ZComponent * other.ZComponent;
    }

    public Vector3D ComputeCrossProduct(Vector3D other) {
        return new Vector3D(
                this.YComponent * other.ZComponent - this.ZComponent * other.YComponent,
                this.ZComponent * other.XComponent - this.XComponent * other.ZComponent,
                this.XComponent * other.YComponent - this.YComponent * other.XComponent
        );
    }

    public float GetX() {
        return XComponent;
    }

    public float GetY() {
        return YComponent;
    }

    public float GetZ() {
        return ZComponent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector3D other = (Vector3D) obj;
        return Math.abs(XComponent - other.XComponent) < 1e-6f &&
                Math.abs(YComponent - other.YComponent) < 1e-6f &&
                Math.abs(ZComponent - other.ZComponent) < 1e-6f;
    }

    @Override
    public String toString() {
        return String.format("Vector3D(%.2f, %.2f, %.2f)",
                XComponent, YComponent, ZComponent);
    }
}