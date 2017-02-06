package com.teamtreehouse.pomodoro.model;

/**
 * Created by akumaldo on 2/6/2017.
 */
public class Attempt {
    private String mMessage;
    private int mTimeCounter;
    private AttemptKind mKind;

    public Attempt(String message, AttemptKind kind) {
        mMessage = message;
        mKind = kind;
        mTimeCounter = mKind.getTotalSeconds();
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getTimeCounter() {
        return mTimeCounter;
    }

    public void setTimeCounter(int timeCounter) {
        mTimeCounter = timeCounter;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "mMessage='" + mMessage + '\'' +
                ", mTimeCounter=" + mTimeCounter +
                ", mKind=" + mKind +
                '}';
    }

    public AttemptKind getKind() {
        return mKind;
    }

    public void setKind(AttemptKind kind) {
        mKind = kind;
    }

    public void tick() {
        mTimeCounter--;
    }

    public void save() {
        System.out.printf("Saving %s%n", mMessage);
    }
}
