package com.teamtreehouse.pomodoro.Controllers;


import com.teamtreehouse.pomodoro.model.Attempt;
import com.teamtreehouse.pomodoro.model.AttemptKind;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 * Created by akumaldo on 2/6/2017.
 */
public class Controller {
    private final AudioClip mApplause;
    private Attempt mCurrentAttemp;
    @FXML private VBox container;
    @FXML private Label title;
    @FXML private TextArea message;
    private StringProperty mTimerText;
    private Timeline mTimeline;

    public Controller()
    {
        mTimerText = new SimpleStringProperty();
        setTimerText(00);
        mApplause = new AudioClip(getClass().getResource("/sounds/applause.mp3").toExternalForm());
    }

    public String getTimerText() {
        return mTimerText.get();
    }

    public StringProperty timerTextProperty() {
        return mTimerText;
    }

    public void setTimerText(String timerText) {
        this.mTimerText.set(timerText);
    }

    public void setTimerText(int remainingSeconds) {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        setTimerText(String.format("%02d:%02d",minutes,seconds));
    }




    private void prepareAttempt(AttemptKind kind)
    {
        reset();
        mCurrentAttemp = new Attempt("", kind);
        addAttemptStyle(kind);
        title.setText(kind.getDisplayName());
        setTimerText(mCurrentAttemp.getTimeCounter());
        mTimeline = new Timeline();
        mTimeline.setCycleCount(kind.getTotalSeconds());
        mTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event ->{
            mCurrentAttemp.tick();
            setTimerText(mCurrentAttemp.getTimeCounter());
        }));
        mTimeline.setOnFinished(event -> {
            saveCurrentAttempt();
            mApplause.play();
            prepareAttempt(mCurrentAttemp.getKind() == AttemptKind.FOCUS?
                            AttemptKind.BREAK:AttemptKind.FOCUS);
        });

    }

    private void saveCurrentAttempt() {
        mCurrentAttemp.setMessage(message.getText());
        mCurrentAttemp.save();
    }

    private void reset() {
        clearAttemptStyle();
        if(mTimeline!= null && mTimeline.getStatus() == Animation.Status.RUNNING){
            mTimeline.stop();
        }
    }


    public void playTimer(){
        container.getStyleClass().add("playing");
        mTimeline.play();
    }

    public void pauseTimer(){
        container.getStyleClass().remove("playing");
        mTimeline.pause();
    }

    private void addAttemptStyle(AttemptKind kind) {
        container.getStyleClass().add(kind.toString().toLowerCase());
    }
    private void clearAttemptStyle() {
        for (AttemptKind kind : AttemptKind.values())
        {
            container.getStyleClass().remove(kind.toString().toLowerCase());
        }
    }


    public void handleRestart(ActionEvent actionEvent) {
        prepareAttempt(AttemptKind.FOCUS);
        playTimer();
    }

    public void handlePlay(ActionEvent actionEvent) {
        if(mCurrentAttemp == null){
            handleRestart(actionEvent);
        }else{
            playTimer();
        }
    }

    public void handlePause(ActionEvent actionEvent) {
        pauseTimer();
    }
}
