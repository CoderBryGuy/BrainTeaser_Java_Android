package com.example.brainteaser_java;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class PuzzleActivity extends AppCompatActivity {
    TextView equationLabel;
    TextView correctLabel;
    TextView timerLabel;
    Random r = new Random();
    Button btn1, btn2, btn3, btn4;
    List<Button> btnList = new ArrayList<>();
    int x, y, solution;
    int answerTag;
    Map<String, String> correctMap = new HashMap<>();
    CountDownTimer timer;


    public void answerClicked(View view){
        Button pressedBtn = (Button) view;
        if(answerTag == Integer.parseInt(pressedBtn.getTag().toString())){
            correctLabel.setText(correctMap.get("right"));
        }else {
            correctLabel.setText(correctMap.get("wrong"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        updateEquationLabel();
        buttonSetup();
        setUpCorrectLabel();
        startTimer();

    }

    private void startTimer() {
        timerLabel = findViewById(R.id.timerLabel);

        timer = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) millisUntilFinished / 1000;
                timerLabel.setText(String.valueOf(secondsLeft)+"s");
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void setUpCorrectLabel() {
        correctLabel = (TextView)findViewById(R.id.correctLabel);
        correctMap.put("right", "correct!!");
        correctMap.put("wrong", "incorrect!!");
    }

    private void updateEquationLabel() {

        x = r.nextInt(20);
        y = r.nextInt(20);
        solution = x + y;
        equationLabel = (TextView) findViewById(R.id.equationLabel);
        equationLabel.setText(String.valueOf(x) + " + " + String.valueOf(y));
    }

    private void buttonSetup() {
        Button randomBtn;

        btn1 = (Button) findViewById(R.id.multipleChoiceBtn1);
        btn2 = (Button) findViewById(R.id.multipleChoiceBtn2);
        btn3 = (Button) findViewById(R.id.multipleChoiceBtn3);
        btn4 = (Button) findViewById(R.id.multipleChoiceBtn4);

        btnList.add(btn1);
        btnList.add(btn2);
        btnList.add(btn3);
        btnList.add(btn4);

        randomBtn = btnList.get(r.nextInt(4));
        randomBtn.setText(String.valueOf(solution));
        answerTag = Integer.parseInt(randomBtn.getTag().toString());

    }
}