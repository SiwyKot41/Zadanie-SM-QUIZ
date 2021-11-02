package com.example.zadanie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String QUIZ_TAG = "MainActivity";
    public static final String activityLog = "Wywołana została metoda z cyklu życia: ";
    private int currentIndex = 0;
    TextView textView;

    List<Question> listOfQuestion = new ArrayList<Question>() {{
        add(new Question(R.string.q_a, true));
        add(new Question(R.string.q_b, true));
        add(new Question(R.string.q_c, true));
        add(new Question(R.string.q_d, false));
        add(new Question(R.string.q_e, true));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.Title);
        Button buttonTrue = findViewById(R.id.True);
        Button buttonFalse = findViewById(R.id.False);
        Button buttonNext = findViewById(R.id.Next);

        setNextQuestion();

        buttonTrue.setOnClickListener(v -> {
            int resultMessageId = listOfQuestion.get(currentIndex).isTrueAnswer()
                    ? R.string.correct
                    : R.string.wrong;
            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        });

        buttonFalse.setOnClickListener(v -> {
            int resultMessageId = !listOfQuestion.get(currentIndex).isTrueAnswer()
                    ? R.string.correct
                    : R.string.wrong;
            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        });

        buttonNext.setOnClickListener(v -> {
            currentIndex++;
            currentIndex %= listOfQuestion.size();
            setNextQuestion();
        });


    }

    public void setNextQuestion() {
        textView.setText(listOfQuestion.get(currentIndex).getQuestionId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG, activityLog + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG, activityLog + "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG, activityLog + "onPause");

    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG, activityLog + "onStop");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG, activityLog + "onDestroy");

    }

}