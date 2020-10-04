package com.example.brainteaser_java;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class PuzzleActivity extends AppCompatActivity {
    TextView equationLabel;
    TextView correctLabel;
    TextView timerLabel;
    TextView rationLabel;
    Random r = new Random();
    Button btn1, btn2, btn3, btn4;
    List<Button> btnList = new ArrayList<>();
    int x, y, solution;
    int answerTag;
    Map<String, String> correctMap = new HashMap<>();
    CountDownTimer timer;
    int rightQuestions = 0, totalQuestions = 0;
    int timerDuration = 30;


    public void answerClicked(View view){
        Button pressedBtn = (Button) view;
        totalQuestions++;

        if(answerTag == Integer.parseInt(pressedBtn.getTag().toString())){
            rightQuestions++;
            correctLabel.setText(correctMap.get("right"));
        }else {
            correctLabel.setText(correctMap.get("wrong"));
        }

        updateRatioLabel();
        updateEquationLabel();
        updateButtons();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        updateEquationLabel();
        buttonSetup();
        setUpCorrectLabel();
        startTimer();
        updateRatioLabel();

    }

    private void updateRatioLabel() {

      rationLabel  = findViewById(R.id.ratioLabel);
      rationLabel.setText(rightQuestions + "/" + totalQuestions);
    }

    private void startTimer() {
        timerLabel = findViewById(R.id.timerLabel);

        timer = new CountDownTimer(timerDuration * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) millisUntilFinished / 1000;
                timerLabel.setText(String.valueOf(secondsLeft)+"s");
            }

            @Override
            public void onFinish() {
                gameOverDialog();
            }
        }.start();
    }

    private void gameOverDialog() {

        new AlertDialog.Builder(this)
                .setMessage("Game Over! Play Again?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent gameActivity = new Intent(PuzzleActivity.this, MainActivity.class);
                        startActivity(gameActivity);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(), "Starting New Game!", 5).show();
                        restartGame();

                    }
                })
                .create().show();

    }

    private void restartGame() {
        rightQuestions = 0;
        totalQuestions = 0;
        correctLabel.setText("");
        updateRatioLabel();
        updateEquationLabel();
        updateButtons();
        startTimer();


    }

    private void setUpCorrectLabel() {
        correctLabel = (TextView)findViewById(R.id.correctLabel);
        correctMap.put("right", "correct!!");
        correctMap.put("wrong", "wrong :(");
   }

    private void updateEquationLabel() {

        x = r.nextInt(20);
        y = r.nextInt(20);
        solution = x + y;
        equationLabel = (TextView) findViewById(R.id.equationLabel);
        equationLabel.setText(String.valueOf(x) + " + " + String.valueOf(y));
    }

    private void buttonSetup() {


        btn1 = (Button) findViewById(R.id.multipleChoiceBtn1);
        btn2 = (Button) findViewById(R.id.multipleChoiceBtn2);
        btn3 = (Button) findViewById(R.id.multipleChoiceBtn3);
        btn4 = (Button) findViewById(R.id.multipleChoiceBtn4);

        btnList.add(btn1);
        btnList.add(btn2);
        btnList.add(btn3);
        btnList.add(btn4);

        updateButtons();
  }

    private void updateButtons() {

        Button randomBtn;
        randomBtn = btnList.get(r.nextInt(4));
        randomBtn.setText(String.valueOf(solution));
        answerTag = Integer.parseInt(randomBtn.getTag().toString());

        updateWrongButtons(randomBtn);

    }

    private void updateWrongButtons(Button randomBtn) {

        List<Integer> fakeAnswers = new ArrayList<>();
        int smallerNumber, fakeAnswer;
        boolean fakeAnswerFound = false;

        for (Button btn: btnList
        ) {
            smallerNumber = Math.min(x, y);


            do {

                fakeAnswer =  r.nextInt((
                        (solution +
                        r.nextInt((int) (solution + (solution * .30)))) - smallerNumber)
                        + smallerNumber);

              System.out.println("FAKE ANSWER TEST: " + fakeAnswer);


                if(!fakeAnswers.contains(fakeAnswer)){
                    fakeAnswers.add(fakeAnswer);
                    fakeAnswerFound = true;
                }

            }while(!fakeAnswerFound);

            fakeAnswerFound = false;

            if(btn != randomBtn){
                Log.i("INFO LOG", "btn tag is " + btn.getTag().toString() + " fake answer is " + String.valueOf(fakeAnswer));

                btn.setText(String.valueOf(fakeAnswer));
            }
        }

    }
}
