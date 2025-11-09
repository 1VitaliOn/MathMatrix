package math.engine;

public final class Vector4D {

    private final float XComponent;
    private final float YComponent;
    private final float ZComponent;
    private final float WComponent;

    public Vector4D(float x, float y, float z, float w) {
        this.XComponent = x;
        this.YComponent = y;
        this.ZComponent = z;
        this.WComponent = w;
    }

    public Vector4D(Vector3D vector, float w) {
        this(vector.GetX(), vector.GetY(), vector.GetZ(), w);
    }

    public Vector4D Add(Vector4D other) {
        return new Vector4D(
                this.XComponent + other.XComponent,
                this.YComponent + other.YComponent,
                this.ZComponent + other.ZComponent,
                this.WComponent + other.WComponent
        );
    }

    public Vector4D Subtract(Vector4D other) {
        return new Vector4D(
                this.XComponent - other.XComponent,
                this.YComponent - other.YComponent,
                this.ZComponent - other.ZComponent,
                this.WComponent - other.WComponent
        );
    }

    public Vector4D Multiply(float scalar) {
        return new Vector4D(
                this.XComponent * scalar,
                this.YComponent * scalar,
                this.ZComponent * scalar,
                this.WComponent * scalar
        );
    }

    public Vector4D Divide(float scalar) {
        if (Math.abs(scalar) < 1e-12f) {
            throw new ArithmeticException("Деление на ноль невозможно");
        }
        return new Vector4D(
                this.XComponent / scalar,
                this.YComponent / scalar,
                this.ZComponent / scalar,
                this.WComponent / scalar
        );
    }

    public float ComputeLength() {
        return (float) Math.sqrt(
                XComponent * XComponent +
                        YComponent * YComponent +
                        ZComponent * ZComponent +
                        WComponent * WComponent
        );
    }

    public Vector4D Normalize() {
        float length = ComputeLength();
        if (length < 1e-12f) {
            throw new ArithmeticException("Невозможно нормализовать нулевой вектор");
        }
        return Divide(length);
    }

    public float ComputeDotProduct(Vector4D other) {
        return this.XComponent * other.XComponent +
                this.YComponent * other.YComponent +
                this.ZComponent * other.ZComponent +
                this.WComponent * other.WComponent;
    }

    public Vector3D ToVector3D() {
        if (Math.abs(WComponent) < 1e-12f) {
            throw new ArithmeticException("W компонента равна нулю");
        }
        return new Vector3D(
                XComponent / WComponent,
                YComponent / WComponent,
                ZComponent / WComponent
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

    public float GetW() {
        return WComponent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector4D other = (Vector4D) obj;
        return Math.abs(XComponent - other.XComponent) < 1e-6f &&
                Math.abs(YComponent - other.YComponent) < 1e-6f &&
                Math.abs(ZComponent - other.ZComponent) < 1e-6f &&
                Math.abs(WComponent - other.WComponent) < 1e-6f;
    }

    @Override
    public String toString() {
        return String.format("Vector4D(%.2f, %.2f, %.2f, %.2f)",
                XComponent, YComponent, ZComponent, WComponent);
    }
}