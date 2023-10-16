package com.example.mycalcultor;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class MainActivity extends Activity implements View.OnClickListener{

    private EditText edtResult;
    private Button btnZero, btnOne, btnTwo,
            btnThree, btnFour, btnFive,
            btnSix, btnSeven, btnEight, btnNine;
    private Button btnPlus, btnMinus, btnDivide, btnMultiply, btnClear, btnEqual;

    private StringBuilder inputStringBuilder;
    private String inputOperator;
    private boolean isReturnResult=false;
    private boolean isResultValidated =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //edit text to return result
        edtResult=(EditText) findViewById(R.id.numberEditText);
        inputStringBuilder=new StringBuilder();

        //numbers 0 to 9
        btnZero=(Button) findViewById(R.id.btnZero);
        btnOne=(Button) findViewById(R.id.btnOne);
        btnTwo=(Button) findViewById(R.id.btnTwo);
        btnThree=(Button) findViewById(R.id.btnThree);
        btnFour=(Button) findViewById(R.id.btnFour);
        btnFive=(Button) findViewById(R.id.btnFive);
        btnSix=(Button) findViewById(R.id.btnSix);
        btnSeven=(Button) findViewById(R.id.btnSeven);
        btnEight=(Button) findViewById(R.id.btnEight);
        btnNine=(Button) findViewById(R.id.btnNine);

        //button of operation
        btnPlus=(Button) findViewById(R.id.btnPlus);
        btnMinus=(Button) findViewById(R.id.btnMinus);
        btnDivide=(Button) findViewById(R.id.btnDivide);
        btnMultiply=(Button) findViewById(R.id.btnMultiply);
        btnEqual =(Button) findViewById(R.id.btnEqual);
        btnClear=(Button) findViewById(R.id.btnClear);


        //set click on button numbers 0 to 9
        btnZero.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);

        //set click on operator button
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnEqual.setOnClickListener(this);




    }
    @Override public void  onClick(View v){
        switch (v.getId()){
            //handle the number
            case R.id.btnZero:
                inputStringBuilder.append("0");
                break;
            case R.id.btnOne:
                inputStringBuilder.append("1");
                break;
            case R.id.btnTwo:
                inputStringBuilder.append("2");
                break;
            case R.id.btnThree:
                inputStringBuilder.append("3");
                break;
            case R.id.btnFour:
                inputStringBuilder.append("4");
                break;
            case R.id.btnFive:
                inputStringBuilder.append("5");
                break;
            case R.id.btnSix:
                inputStringBuilder.append("6");
                break;
            case R.id.btnSeven:
                inputStringBuilder.append("7");
                break;
            case R.id.btnEight:
                inputStringBuilder.append("8");
                break;
            case R.id.btnNine:
                inputStringBuilder.append("9");
                break;

            //handle operator and clear
            case R.id.btnPlus:
                inputStringBuilder.append("+");
                inputOperator="+";
                break;
            case R.id.btnMinus:
                inputStringBuilder.append("-");
                inputOperator="-";
                break;
            case R.id.btnMultiply:
                inputStringBuilder.append("*");
                inputOperator="*";
                break;
            case R.id.btnDivide:
                inputStringBuilder.append("/");
                inputOperator="/";
                break;
            case R.id.btnClear:
                if(inputStringBuilder.length()!=0)
                    inputStringBuilder.deleteCharAt(inputStringBuilder.length()-1);
                break;
            case R.id.btnEqual:
                if(inputStringBuilder.length()!=0 && inputOperator != null){
                    double result=evaluateExpression();
                    isReturnResult = true;
                    if(isResultValidated) {
                        edtResult.setText(String.valueOf(result));
                    }
                    else{
                        //Toast.makeText((Context) this, "NaN",Toast.LENGTH_SHORT).show();
                        edtResult.setText("NaN");
                        isResultValidated =true;
                    }
                }
                break;
        }
        if(isReturnResult)
            isReturnResult=false;
        else if (inputStringBuilder.length()!=0)
            edtResult.setText(inputStringBuilder.toString());
        else edtResult.setText("");
    }

    private double evaluateExpression() {
        double result = 0;
        String numOne="", numTwo="";
        int posOfOperator=inputStringBuilder.indexOf(inputOperator);
        //check the last char is a operator
        try{
            if(String.valueOf(inputStringBuilder.charAt(inputStringBuilder.length() - 1)).equals(inputOperator)){
                //Toast.makeText((Context) this, "Not Valid input: Last character is a operator",Toast.LENGTH_SHORT).show();
                openWarmingDialog("Not Valid input: Last character is a operator.");
            }
            else {
                numOne = inputStringBuilder.substring(0, posOfOperator);
                numTwo = inputStringBuilder.substring(posOfOperator + 1, inputStringBuilder.length());

                // count number of operators
                String operators = "+-*/";
                int operatorCount = 0;
                for (int i = 0; i < inputStringBuilder.length(); i++) {
                    char currentChar = inputStringBuilder.charAt(i);
                    if (operators.contains(String.valueOf(currentChar))) {
                        operatorCount++;
                    }
                }

                if (Integer.parseInt(numTwo) == 0 && inputOperator == "/" )
    //                Toast.makeText((Context) this, "Not Valid input: Can't divide to Zero", Toast.LENGTH_SHORT).show();
                    openWarmingDialog("Not Valid input: Can't divide to Zero.");

                else if (numOne.isEmpty() || numTwo.isEmpty())
    //                Toast.makeText((Context) this, "Not Valid input: Number one or number two is empty" , Toast.LENGTH_SHORT).show();
                    openWarmingDialog("Not Valid input: Number one or number two is empty.");

                else if (inputStringBuilder.indexOf(inputOperator) == 0)
    //                Toast.makeText((Context) this, "Not Valid input: First character is a operator", Toast.LENGTH_SHORT).show();
                    openWarmingDialog("Not Valid input: First character is a operator.");

                else if (operatorCount>1)
    //                Toast.makeText((Context) this, "Not Valid input: Have to many operator",Toast.LENGTH_SHORT).show();
                    openWarmingDialog("Not Valid input: Have to many operator.");
                //calculate numbers
                else switch (inputOperator) {
                        case "+":
                            result = Double.parseDouble(numOne) + Double.parseDouble(numTwo);
                            break;

                        case "-":
                            result = Double.parseDouble(numOne) - Double.parseDouble(numTwo);
                            break;

                        case "*":
                            result = Double.parseDouble(numOne) * Double.parseDouble(numTwo);
                            break;

                        case "/":
                            result = Double.parseDouble(numOne) / Double.parseDouble(numTwo);
                            result = Math.ceil(result*100.0)/100.0;
                            break;
                }
            }
        }
        catch (Exception exception){
            openWarmingDialog("Not Valid input: Only calculating between 2 numbers.");
        }

        inputStringBuilder.setLength(0);
        inputOperator=null;
        return result;
    }

    private void openWarmingDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Warming Alert")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.show();
        isResultValidated =false;
    }

}