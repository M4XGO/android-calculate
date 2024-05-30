package fr.kody.calculatricesimple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private int op1 = 0;
    private int op2 = 0;
    private Ops operator = null;
    private char operatorChar;
    private boolean isOp1 = true;
    private int old_value;

    private String currentOperation = String.valueOf(' ');

    private enum Ops {
        PLUS, MOINS, FOIS, DIV, C
    }

    public void setOperator(View v) {
        Button b = (Button) v;
        switch (b.getText().toString()) {
            case "+":
                operator = Ops.PLUS;
                operatorChar = '+';
                break;
            case "-":
                operator = Ops.MOINS;
                operatorChar = '-';
                break;
            case "*":
                operator = Ops.FOIS;
                operatorChar = '*';
                break;
            case "/":
                operator = Ops.DIV;
                operatorChar = '/';
                break;
        }
        isOp1 = false;
    }

    public void addNumber(View v) {
        Button b = (Button) v;
        try {
            old_value = op1;
            int val = Integer.parseInt(b.getText().toString());


            if (isOp1) {
                op1 = op1 * 10 + val;
                updateDisplay(op1);
            } else {
                op2 = op2 * 10 + val;
                currentCalcul(op2);
            }
        } catch (NumberFormatException | ClassCastException e) {
            Toast.makeText(this, "Valeur erronée",Toast.LENGTH_LONG).show();
        }
    }
    public void resetView(View v) {
        op1 = 0;
        op2 = 0;
        operatorChar = ' ';
        isOp1 = true;
        updateDisplay( 0);
    }
    public void computeResult(View v) {
        if (operator != null) {

            switch (operator) {

                case PLUS:
                    op1 += op2;
                    break;
                case MOINS:
                    op1 -= op2;
                    break;
                case FOIS:
                    op1 *= op2;
                    break;
                case DIV:
                    if (op2 == 0) {
                        Toast.makeText(this, "Division par zéro",Toast.LENGTH_LONG).show();
                        updateDisplay( 0);
                        return;
                    }
                    else {
                        op1 /= op2;
                        break;
                    }

            }
            op2 = 0;
            isOp1 = true;
            updateDisplay(op1);
        }
    }

    public  void currentCalcul(int value){
        currentOperation = String.format("%d %s %d",old_value, operatorChar, value);
        screen.setText(String.valueOf(currentOperation ));
    }
    private void updateDisplay(int value) {
        screen.setText(String.valueOf(value));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        screen = findViewById(R.id.screen);
    }
}