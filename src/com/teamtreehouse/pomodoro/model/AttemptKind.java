package com.teamtreehouse.pomodoro.model;

/**
 * Created by akumaldo on 2/6/2017.
 */
public enum AttemptKind {
    FOCUS(25*60, "focus"),
    BREAK(5*60, "break");

    private int mTotalSeconds;
    private String mDisplayName;

    AttemptKind(int totalSeconds, String displayName) {
        mTotalSeconds = totalSeconds;
        mDisplayName = displayName;
    }

    public int getTotalSeconds() {
        return mTotalSeconds;
    }

    public String getDisplayName() {
    return mDisplayName;
    }
}
