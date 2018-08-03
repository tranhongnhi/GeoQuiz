package com.example.tranhongnhi.geoquiz;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private  static  final String TAG = "QuizActivity";
    private  static  final  String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button  mFalseButton;
    private Button mNextButton;
    private Button mBackButton;
    private Button mCheatButton;
    private TextView mTextView;
    private boolean mIsCheater;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private  Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, true),
            new Question(R.string.question_3, false),
            new Question(R.string.question_4, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        mTrueButton = (Button)findViewById(R.id.btn_true);
        mFalseButton = (Button)findViewById(R.id.btn_false);
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                checkAnswer(false);
            }
        });

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mBackButton = (Button)findViewById(R.id.btn_back);
        mNextButton = (Button)findViewById(R.id.btn_next);
        if (mNextButton != null) {
            mNextButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    updateQuestion(true);
                }
            });
        }
        if (mBackButton != null) {
            mBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateQuestion(false);
                }
            });
        }

        mCheatButton = (Button)findViewById(R.id.btn_cheat);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        mTextView = (TextView)findViewById(R.id.textview_version);
        mTextView.setText(Build.VERSION.SDK_INT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "OnStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy called");
    }

    private void updateQuestion(boolean next) {
        int step = next ? 1 : - 1;
        mCurrentIndex = (Math.max(mCurrentIndex + step, 0)) % mQuestionBank.length;
        int resId = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(resId);
    }

    private void checkAnswer(boolean inputAnswer) {
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int resId = 0;
        if (answer == inputAnswer) {
            resId = R.string.correct_toast;
        } else {
            resId = R.string.incorrect_toast;
        }
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}
