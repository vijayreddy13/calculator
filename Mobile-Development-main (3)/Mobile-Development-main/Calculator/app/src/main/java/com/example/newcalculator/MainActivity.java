package com.example.newcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private StringBuilder currentInput;
    private double firstOperand;
    private String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create main layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(16, 16, 16, 16);

        // Create result TextView
        resultTextView = new TextView(this);
        resultTextView.setTextSize(32);
        resultTextView.setGravity(android.view.Gravity.END);
        resultTextView.setPadding(16, 16, 16, 16);
        resultTextView.setBackgroundColor(0xFFEEEEEE);
        mainLayout.addView(resultTextView);

        // Create GridLayout for buttons
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(4);
        gridLayout.setRowCount(5);

        // Button labels
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        // Create and add buttons to GridLayout
        for (String label : buttonLabels) {
            Button button = new Button(this);
            button.setText(label);
            button.setOnClickListener(this::onButtonClick);
            gridLayout.addView(button);
        }

        mainLayout.addView(gridLayout);

        // Set the main layout as the content view
        setContentView(mainLayout);

        // Initialize input variables
        currentInput = new StringBuilder();
    }

    // Handles button clicks
    private void onButtonClick(View view) {
        String buttonText = ((Button) view).getText().toString();
        switch (buttonText) {
            case "C":
                currentInput.setLength(0);
                firstOperand = 0;
                operator = null;
                break;
            case "=":
                calculateResult();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (currentInput.length() > 0) {
                    firstOperand = Double.parseDouble(currentInput.toString());
                    operator = buttonText;
                    currentInput.setLength(0);
                }
                break;
            default:
                currentInput.append(buttonText);
                break;
        }
        updateDisplay();
    }

    // Perform the calculation when "=" is pressed
    private void calculateResult() {
        if (operator != null && currentInput.length() > 0) {
            double secondOperand = Double.parseDouble(currentInput.toString());
            double result = 0;
            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        resultTextView.setText("Error");
                        return;
                    }
                    break;
            }
            currentInput.setLength(0);
            currentInput.append(result);
            operator = null;
        }
    }

    // Update the display with the current input
    private void updateDisplay() {
        resultTextView.setText(currentInput.toString());
    }
}
