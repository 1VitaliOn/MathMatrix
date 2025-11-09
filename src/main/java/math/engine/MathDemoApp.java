package math.engine;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MathDemoApp extends Application {

    private TextArea outputArea;
    private TextField inputField1, inputField2, inputField3, inputField4;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Math Matrix Engine");

        // Создаем интерфейс
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        // Заголовок
        Label titleLabel = new Label("Math Matrix Engine - Линейная алгебра");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Панель ввода
        GridPane inputPanel = createInputPanel();

        // Панель кнопок операций
        HBox operationPanel = createOperationPanel();

        // Область вывода
        outputArea = new TextArea();
        outputArea.setPrefHeight(400);
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-font-family: 'Consolas', monospace; -fx-font-size: 12px;");

        root.getChildren().addAll(titleLabel, inputPanel, operationPanel, outputArea);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        showWelcomeMessage();
    }

    private GridPane createInputPanel() {
        GridPane panel = new GridPane();
        panel.setHgap(10);
        panel.setVgap(5);
        panel.setPadding(new Insets(10, 0, 10, 0));

        // Поля ввода
        Label inputLabel = new Label("Введите значения (через запятую или пробел):");
        inputField1 = new TextField();
        inputField1.setPromptText("Пример: 1, 2, 3");
        inputField1.setPrefWidth(200);

        Label inputLabel2 = new Label("Второй набор значений (для операций):");
        inputField2 = new TextField();
        inputField2.setPromptText("Пример: 4, 5, 6");
        inputField2.setPrefWidth(200);

        Label matrixLabel = new Label("Матрица 3x3 (9 чисел через запятую):");
        inputField3 = new TextField();
        inputField3.setPromptText("1,2,3,4,5,6,7,8,9");
        inputField3.setPrefWidth(300);

        Label vectorLabel = new Label("Вектор для системы (3 числа):");
        inputField4 = new TextField();
        inputField4.setPromptText("8, -11, -3");
        inputField4.setPrefWidth(200);

        panel.add(inputLabel, 0, 0);
        panel.add(inputField1, 1, 0);
        panel.add(inputLabel2, 0, 1);
        panel.add(inputField2, 1, 1);
        panel.add(matrixLabel, 0, 2);
        panel.add(inputField3, 1, 2);
        panel.add(vectorLabel, 0, 3);
        panel.add(inputField4, 1, 3);

        return panel;
    }

    private HBox createOperationPanel() {
        HBox panel = new HBox(10);

        Button vectorOpsBtn = new Button("Векторные операции");
        Button matrixOpsBtn = new Button("Матричные операции");
        Button systemBtn = new Button("Решить систему");
        Button transformBtn = new Button("Преобразования");
        Button clearBtn = new Button("Очистить");

        vectorOpsBtn.setOnAction(e -> performVectorOperations());
        matrixOpsBtn.setOnAction(e -> performMatrixOperations());
        systemBtn.setOnAction(e -> solveLinearSystem());
        transformBtn.setOnAction(e -> performTransformations());
        clearBtn.setOnAction(e -> clearAll());

        panel.getChildren().addAll(vectorOpsBtn, matrixOpsBtn, systemBtn, transformBtn, clearBtn);

        return panel;
    }

    private void showWelcomeMessage() {
        outputArea.appendText("=== MATH MATRIX ENGINE ===\n");
        outputArea.appendText("Библиотека линейной алгебры\n\n");
        outputArea.appendText("Инструкция:\n");
        outputArea.appendText("1. Введите значения в поля выше\n");
        outputArea.appendText("2. Выберите тип операции\n");
        outputArea.appendText("3. Смотрите результат в этом окне\n\n");
    }

    private float[] parseInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new float[0];
        }

        // Заменяем запятые на пробелы и разделяем
        String[] parts = input.replace(",", " ").split("\\s+");
        float[] result = new float[parts.length];

        for (int i = 0; i < parts.length; i++) {
            try {
                result[i] = Float.parseFloat(parts[i].trim());
            } catch (NumberFormatException e) {
                outputArea.appendText("Ошибка: неверный формат числа: " + parts[i] + "\n");
                return new float[0];
            }
        }

        return result;
    }

    private void performVectorOperations() {
        outputArea.appendText("\n=== ВЕКТОРНЫЕ ОПЕРАЦИИ ===\n");

        float[] values1 = parseInput(inputField1.getText());
        float[] values2 = parseInput(inputField2.getText());

        if (values1.length < 2) {
            outputArea.appendText("Ошибка: введите минимум 2 числа для вектора\n");
            return;
        }

        try {
            // Создаем векторы в зависимости от количества компонентов
            if (values1.length == 2) {
                Vector2D v1 = new Vector2D(values1[0], values1[1]);
                outputArea.appendText("Вектор 2D: " + v1 + "\n");
                outputArea.appendText("Длина: " + v1.ComputeLength() + "\n");
                outputArea.appendText("Нормализованный: " + v1.Normalize() + "\n");

                if (values2.length == 2) {
                    Vector2D v2 = new Vector2D(values2[0], values2[1]);
                    outputArea.appendText("Второй вектор: " + v2 + "\n");
                    outputArea.appendText("Сумма: " + v1.Add(v2) + "\n");
                    outputArea.appendText("Разность: " + v1.Subtract(v2) + "\n");
                    outputArea.appendText("Скалярное произведение: " + v1.ComputeDotProduct(v2) + "\n");
                }

            } else if (values1.length == 3) {
                Vector3D v1 = new Vector3D(values1[0], values1[1], values1[2]);
                outputArea.appendText("Вектор 3D: " + v1 + "\n");
                outputArea.appendText("Длина: " + v1.ComputeLength() + "\n");
                outputArea.appendText("Нормализованный: " + v1.Normalize() + "\n");

                if (values2.length == 3) {
                    Vector3D v2 = new Vector3D(values2[0], values2[1], values2[2]);
                    outputArea.appendText("Второй вектор: " + v2 + "\n");
                    outputArea.appendText("Сумма: " + v1.Add(v2) + "\n");
                    outputArea.appendText("Разность: " + v1.Subtract(v2) + "\n");
                    outputArea.appendText("Скалярное произведение: " + v1.ComputeDotProduct(v2) + "\n");
                    outputArea.appendText("Векторное произведение: " + v1.ComputeCrossProduct(v2) + "\n");
                }

            } else if (values1.length == 4) {
                Vector4D v1 = new Vector4D(values1[0], values1[1], values1[2], values1[3]);
                outputArea.appendText("Вектор 4D: " + v1 + "\n");
                outputArea.appendText("Длина: " + v1.ComputeLength() + "\n");

                if (Math.abs(v1.GetW()) > 1e-6) {
                    outputArea.appendText("Преобразование в 3D: " + v1.ToVector3D() + "\n");
                }
            }

        } catch (Exception e) {
            outputArea.appendText("Ошибка: " + e.getMessage() + "\n");
        }
    }

    private void performMatrixOperations() {
        outputArea.appendText("\n=== МАТРИЧНЫЕ ОПЕРАЦИИ ===\n");

        float[] matrixValues = parseInput(inputField3.getText());

        if (matrixValues.length == 9) {
            // Матрица 3x3
            float[][] data3x3 = {
                    {matrixValues[0], matrixValues[1], matrixValues[2]},
                    {matrixValues[3], matrixValues[4], matrixValues[5]},
                    {matrixValues[6], matrixValues[7], matrixValues[8]}
            };

            try {
                Matrix3x3 matrix = new Matrix3x3(data3x3);
                outputArea.appendText("Матрица 3x3:\n" + matrix);
                outputArea.appendText("Определитель: " + matrix.ComputeDeterminant() + "\n");
                outputArea.appendText("Транспонированная:\n" + matrix.Transpose());

                // Показываем обратную матрицу если она существует
                if (Math.abs(matrix.ComputeDeterminant()) > 1e-6) {
                    outputArea.appendText("Обратная матрица:\n" + matrix.ComputeInverse());
                }

            } catch (Exception e) {
                outputArea.appendText("Ошибка: " + e.getMessage() + "\n");
            }
        } else {
            outputArea.appendText("Для матрицы 3x3 нужно 9 чисел\n");
        }
    }

    private void solveLinearSystem() {
        outputArea.appendText("\n=== РЕШЕНИЕ СИСТЕМЫ ЛИНЕЙНЫХ УРАВНЕНИЙ ===\n");

        float[] matrixValues = parseInput(inputField3.getText());
        float[] vectorValues = parseInput(inputField4.getText());

        if (matrixValues.length != 9) {
            outputArea.appendText("Ошибка: для матрицы нужно 9 чисел\n");
            return;
        }

        if (vectorValues.length != 3) {
            outputArea.appendText("Ошибка: для вектора нужно 3 числа\n");
            return;
        }

        try {
            float[][] data = {
                    {matrixValues[0], matrixValues[1], matrixValues[2]},
                    {matrixValues[3], matrixValues[4], matrixValues[5]},
                    {matrixValues[6], matrixValues[7], matrixValues[8]}
            };

            Matrix3x3 A = new Matrix3x3(data);
            Vector3D b = new Vector3D(vectorValues[0], vectorValues[1], vectorValues[2]);

            outputArea.appendText("Матрица A:\n" + A);
            outputArea.appendText("Вектор b: " + b + "\n");

            Vector3D solution = A.SolveLinearSystem(b);
            outputArea.appendText("Решение x: " + solution + "\n");

            // Проверка
            Vector3D verification = A.Multiply(solution);
            outputArea.appendText("Проверка A*x: " + verification + "\n");
            outputArea.appendText("Решение " + (verification.equals(b) ? "ВЕРНО" : "ОШИБКА") + "\n");

        } catch (Exception e) {
            outputArea.appendText("Ошибка решения: " + e.getMessage() + "\n");
        }
    }

    private void performTransformations() {
        outputArea.appendText("\n=== АФФИННЫЕ ПРЕОБРАЗОВАНИЯ ===\n");

        float[] pointValues = parseInput(inputField1.getText());

        if (pointValues.length < 3) {
            outputArea.appendText("Введите точку (3 числа) для преобразований\n");
            return;
        }

        Vector3D point = new Vector3D(pointValues[0], pointValues[1], pointValues[2]);
        outputArea.appendText("Исходная точка: " + point + "\n");

        // Перевод
        Matrix4x4 translation = LinearAlgebraEngine.CreateTranslationMatrix(2, 3, 4);
        Vector3D translated = translation.Multiply(point);
        outputArea.appendText("После переноса на (2,3,4): " + translated + "\n");

        // Поворот на 45 градусов вокруг Z
        Matrix4x4 rotation = LinearAlgebraEngine.CreateRotationMatrixZ((float) Math.PI / 4);
        Vector3D rotated = rotation.Multiply(point);
        outputArea.appendText("После поворота на 45° вокруг Z: " + rotated + "\n");

        // Масштабирование
        Matrix4x4 scale = LinearAlgebraEngine.CreateScaleMatrix(2, 2, 2);
        Vector3D scaled = scale.Multiply(point);
        outputArea.appendText("После масштабирования в 2 раза: " + scaled + "\n");
    }

    private void clearAll() {
        inputField1.clear();
        inputField2.clear();
        inputField3.clear();
        inputField4.clear();
        outputArea.clear();
        showWelcomeMessage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}