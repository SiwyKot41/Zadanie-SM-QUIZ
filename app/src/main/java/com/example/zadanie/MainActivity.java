package com.example.zadanie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "com.example.zadanie";
    private static final int REQUEST_CODE_PROMPT = 0;
    private boolean promptWasShown = false;
    private int currentIndex = 0;
    TextView textView;

    List<Question> listOfQuestion = new ArrayList<Question>() {{
        add(new Question(R.string.q_a, true));
        add(new Question(R.string.q_b, true));
        add(new Question(R.string.q_c, true));
        add(new Question(R.string.q_d, false));
        add(new Question(R.string.q_e, true));
    }};

    int[] prompts = {
       R.string.prompt1,
       R.string.prompt2,
       R.string.prompt3,
       R.string.prompt4,
       R.string.prompt5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);

        textView = findViewById(R.id.Title);
        Button buttonTrue = findViewById(R.id.True);
        Button buttonFalse = findViewById(R.id.False);
        Button buttonNext = findViewById(R.id.Next);
        Button buttonPrompt = findViewById(R.id.Prompt);

        Log.d(QUIZ_TAG, activityLog + "onCreate");
        setNextQuestion();

        buttonTrue.setOnClickListener(v -> {
            int resultMessageId = 0;
            if (promptWasShown) {
                resultMessageId = R.string.promptWasShown;
            } else {
                resultMessageId = listOfQuestion.get(currentIndex).isTrueAnswer()
                        ? R.string.correct
                        : R.string.wrong;
            }

            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        });

        buttonFalse.setOnClickListener(v -> {
            int resultMessageId = 0;
            if (promptWasShown) {
                resultMessageId = R.string.promptWasShown;
            } else {
                resultMessageId = !listOfQuestion.get(currentIndex).isTrueAnswer()
                        ? R.string.correct
                        : R.string.wrong;
            }

            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        });

        buttonNext.setOnClickListener(v -> {
            currentIndex++;
            currentIndex %= listOfQuestion.size();
            promptWasShown = false;
            setNextQuestion();
        });

        buttonPrompt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            int prompt = prompts[currentIndex];
            intent.putExtra(KEY_EXTRA_ANSWER, prompt);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) return;
            promptWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_PROMPT_SHOWN, false);

        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, activityLog + "onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

}