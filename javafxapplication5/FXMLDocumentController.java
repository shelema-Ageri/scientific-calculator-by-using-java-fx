package javafxapplication5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FXMLDocumentController {

    @FXML private TextField display;
    @FXML private Button sinBtn;
    @FXML private Button cosBtn;
    @FXML private Button tanBtn;

    private double firstNumber = 0;
    private String operator = "";
    private boolean powerMode = false;
    private boolean shiftMode = false;

    // ---------- NUMBERS ----------
    @FXML
    private void handleNumber(ActionEvent e) {
        display.appendText(((Button) e.getSource()).getText());
    }

    // ---------- OPERATORS ----------
    @FXML
    private void handleOperator(ActionEvent e) {
        firstNumber = Double.parseDouble(display.getText());
        operator = ((Button) e.getSource()).getText();
        display.clear();
    }

    @FXML
    private void handlePower(ActionEvent e) {
        firstNumber = Double.parseDouble(display.getText());
        powerMode = true;
        display.clear();
    }

    // ---------- EQUALS ----------
    @FXML
    private void handleEquals(ActionEvent e) {
        double secondNumber = Double.parseDouble(display.getText());
        double result = 0;

        if (powerMode) {
            result = Math.pow(firstNumber, secondNumber);
            powerMode = false;
            saveToDatabase(firstNumber + "^" + secondNumber, result + "");
        } else {
            switch (operator) {
                case "+": result = firstNumber + secondNumber; break;
                case "-": result = firstNumber - secondNumber; break;
                case "*": result = firstNumber * secondNumber; break;
                case "/": result = firstNumber / secondNumber; break;
            }
            saveToDatabase(firstNumber + " " + operator + " " + secondNumber, result + "");
        }

        display.setText(result + "");
    }

    // ---------- SHIFT ----------
    @FXML
    private void handleShift(ActionEvent e) {
        shiftMode = !shiftMode;
        if (shiftMode) {
            sinBtn.setText("sin⁻¹");
            cosBtn.setText("cos⁻¹");
            tanBtn.setText("tan⁻¹");
        } else {
            sinBtn.setText("sin");
            cosBtn.setText("cos");
            tanBtn.setText("tan");
        }
    }

    // ---------- TRIG ----------
    @FXML
    private void handleSin(ActionEvent e) {
        double x = Double.parseDouble(display.getText());
        double r;
        if (!shiftMode) {
            r = Math.sin(Math.toRadians(x));
            saveToDatabase("sin(" + x + ")", r + "");
        } else {
            r = Math.toDegrees(Math.asin(x));
            saveToDatabase("sin⁻¹(" + x + ")", r + "");
        }
        display.setText(r + "");
    }

    @FXML
    private void handleCos(ActionEvent e) {
        double x = Double.parseDouble(display.getText());
        double r;
        if (!shiftMode) {
            r = Math.cos(Math.toRadians(x));
            saveToDatabase("cos(" + x + ")", r + "");
        } else {
            r = Math.toDegrees(Math.acos(x));
            saveToDatabase("cos⁻¹(" + x + ")", r + "");
        }
        display.setText(r + "");
    }

    @FXML
    private void handleTan(ActionEvent e) {
        double x = Double.parseDouble(display.getText());
        double r;
        if (!shiftMode) {
            r = Math.tan(Math.toRadians(x));
            saveToDatabase("tan(" + x + ")", r + "");
        } else {
            r = Math.toDegrees(Math.atan(x));
            saveToDatabase("tan⁻¹(" + x + ")", r + "");
        }
        display.setText(r + "");
    }

    // ---------- SCIENTIFIC ----------
    @FXML private void handleSquare(ActionEvent e) {
        double x = Double.parseDouble(display.getText());
        double r = x * x;
        display.setText(r + "");
        saveToDatabase("square(" + x + ")", r + "");
    }

    @FXML private void handleCubeRoot(ActionEvent e) {
        double x = Double.parseDouble(display.getText());
        double r = Math.cbrt(x);
        display.setText(r + "");
        saveToDatabase("cuberoot(" + x + ")", r + "");
    }

    @FXML private void handleSqrt(ActionEvent e) {
        double x = Double.parseDouble(display.getText());
        double r = Math.sqrt(x);
        display.setText(r + "");
        saveToDatabase("sqrt(" + x + ")", r + "");
    }

    @FXML private void handleLog(ActionEvent e) {
        double x = Double.parseDouble(display.getText());
        double r = Math.log10(x);
        display.setText(r + "");
        saveToDatabase("log(" + x + ")", r + "");
    }

    @FXML private void handleLn(ActionEvent e) {
        double x = Double.parseDouble(display.getText());
        double r = Math.log(x);
        display.setText(r + "");
        saveToDatabase("ln(" + x + ")", r + "");
    }

    @FXML private void handlePi(ActionEvent e) {
        display.setText(String.valueOf(Math.PI));
    }

    // ---------- CLEAR ----------
    @FXML private void handleClear(ActionEvent e) {
        display.clear();
        operator = "";
        powerMode = false;
        shiftMode = false;
        sinBtn.setText("sin");
        cosBtn.setText("cos");
        tanBtn.setText("tan");
    }

    // ---------- DATABASE ----------
    private void saveToDatabase(String exp, String res) {
        String sql = "INSERT INTO calculations(expression, result) VALUES (?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, exp);
            ps.setString(2, res);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
