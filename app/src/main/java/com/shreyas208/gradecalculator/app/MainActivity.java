package com.shreyas208.gradecalculator.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText etCurrentGrade;
    private EditText etExamGrade;
    private EditText etExamValue;
    private EditText etFinalGrade;
    private EditText lastCalculated;

    private String strCurrentGrade;
    private String strExamGrade;
    private String strExamValue;
    private String strFinalGrade;

    private double currentGrade;
    private double examGrade;
    private double examValue;
    private double finalGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCurrentGrade = (EditText)findViewById(R.id.etCurrentGrade);
        etExamGrade = (EditText)findViewById(R.id.etExamGrade);
        etExamValue = (EditText)findViewById(R.id.etExamValue);
        etFinalGrade = (EditText)findViewById(R.id.etFinalGrade);
    }

    private void calculate() {
        updateStrings();
        if (strCurrentGrade.isEmpty()) {
            if (strExamGrade.isEmpty() || strExamValue.isEmpty() || strFinalGrade.isEmpty()) {
                notifyEnterThree();
            } else {
                updateDoubles(etCurrentGrade);
                currentGrade = (finalGrade - (examGrade * examValue)) / (1 - examValue);
                etCurrentGrade.setText(getRoundedStringFromValue(currentGrade));
                lastCalculated = etCurrentGrade;
            }
        } else if (strExamGrade.isEmpty()) {
            if (strExamValue.isEmpty() || strFinalGrade.isEmpty()) {
                notifyEnterThree();
            } else {
                updateDoubles(etCurrentGrade);
                examGrade = (finalGrade - (currentGrade * (1 - examValue))) / examValue;
                etExamGrade.setText(getRoundedStringFromValue(examGrade));
                lastCalculated = etExamGrade;
            }
        } else if (strExamValue.isEmpty()) {
            if (strFinalGrade.isEmpty()) {
                notifyEnterThree();
            } else {
                updateDoubles(etExamValue);
                examValue = 100.0 * (finalGrade - currentGrade) / (examGrade - currentGrade);
                etExamValue.setText(getRoundedStringFromValue(examValue));
                lastCalculated = etExamValue;
            }
        } else if (strFinalGrade.isEmpty()) {
            updateDoubles(etFinalGrade);
            finalGrade = (currentGrade * (1 - examValue)) + (examGrade * examValue);
            etFinalGrade.setText(getRoundedStringFromValue(finalGrade));
            lastCalculated = etFinalGrade;
        } else {
            lastCalculated.setText("");
            calculate();
        }
    }

    private void updateStrings() {
        strCurrentGrade = etCurrentGrade.getText().toString();
        strExamGrade = etExamGrade.getText().toString();
        strExamValue = etExamValue.getText().toString();
        strFinalGrade = etFinalGrade.getText().toString();
    }

    private void updateDoubles(EditText except) {
        if (except != etCurrentGrade) currentGrade = Double.parseDouble(etCurrentGrade.getText().toString());
        if (except != etExamGrade) examGrade = Double.parseDouble(etExamGrade.getText().toString());
        if (except != etExamValue) examValue = Double.parseDouble(etExamValue.getText().toString()) / 100;
        if (except != etFinalGrade) finalGrade = Double.parseDouble(etFinalGrade.getText().toString());
    }

    private void notifyEnterThree() {
        Toast.makeText(getApplicationContext(), getString(R.string.toast_enter_three), Toast.LENGTH_SHORT).show();
    }

    private void clearAllFields() {
        etCurrentGrade.setText("");
        etExamGrade.setText("");
        etExamValue.setText("");
        etFinalGrade.setText("");
    }

    private String getRoundedStringFromValue(double value) {
        return String.valueOf(Math.round(value * 100)/100.0);
    }

    public void mainOnButtonClick(View view) {
        if (view.getId() == R.id.main_btnCalculate) {
            calculate();
        } else if (view.getId() == R.id.main_btnClearAll) {
            clearAllFields();
        } else if (view.getId() == R.id.main_btnClearCurrentGrade) {
            etCurrentGrade.setText("");
        } else if (view.getId() == R.id.main_btnClearExamGrade) {
            etExamGrade.setText("");
        } else if (view.getId() == R.id.main_btnClearExamValue) {
            etExamValue.setText("");
        } else if (view.getId() == R.id.main_btnClearFinalGrade) {
            etFinalGrade.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
