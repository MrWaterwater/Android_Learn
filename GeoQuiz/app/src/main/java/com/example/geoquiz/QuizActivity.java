package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_A_INDEX = "a_index";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mBackButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),
            new Question(R.string.question_australia,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_ocean,true)
    };
    private int length = mQuestionBank.length;
    private int[] p = new int[length];
    private int t = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            p = savedInstanceState.getIntArray(KEY_A_INDEX);
        }
        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        mTrueButton = findViewById(R.id.True_Button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = findViewById(R.id.False_Button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                while (true){
                    if(p[mCurrentIndex] == 0){
                        updateQuestion();
                        break;
                    }else {
                        mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                    }
                }
            }
        });
        mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex == 0)
                    mCurrentIndex = mQuestionBank.length-1;
                else
                    mCurrentIndex = (mCurrentIndex-1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        updateQuestion();
    }
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
        int messageResId = 0;
        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
            p[mCurrentIndex] = 1;
        }else {
            messageResId = R.string.incorrect_toast;
            p[mCurrentIndex] = 0;
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Log.i(TAG,"saveInstanceState");
        saveInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        saveInstanceState.putIntArray(KEY_A_INDEX,p);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
}