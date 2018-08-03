package com.example.tranhongnhi.geoquiz;

public class Question {
    private int mTextResId;
    private  boolean mAnswerTrue;
    public  Question(int textResiD, boolean answerTrue) {
        mTextResId = textResiD;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
