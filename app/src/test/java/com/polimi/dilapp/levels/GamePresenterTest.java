package com.polimi.dilapp.levels;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.Assert.assertEquals;


public class GamePresenterTest {

    private GamePresenter presenter;
    private List<String> elements = new ArrayList<>();
    private IGame.View view = new IGame.View() {
        @Override
        public Context getScreenContext() {
            return null;
        }

        @Override
        public Intent newIntent() {
            return null;
        }

        @Override
        public void setVideoView(int videoID) {

        }

        @Override
        public ArrayList<String> getSessionArray(int vectorID) {
            return null;
        }

        @Override
        public void setPresentationAnimation(String currentElement) {

        }

        @Override
        public Class getApplicationClass() {
            return null;
        }

        @Override
        public void setVideoCorrectAnswer() {

        }

        @Override
        public void setVideoWrongAnswerToRepeat() {

        }

        @Override
        public void setVideoWrongAnswerAndGoOn() {

        }

        @Override
        public void setRepeatOrExitScreen() {

        }

        @Override
        public void setGoOnOrExitScreen() {

        }
    };

    @Before
    public void setUp() {
        presenter = new GamePresenter(view);
        elements.add("apple");
        elements.add("_1");
        elements.add("_10");
        elements.add("_apple");
        presenter.startGame((ArrayList<String>) elements);
    }


    @Test
    public void isItemMultiple() throws  Exception {

        //assertEquals("Multiple Item :", result,  );

    }


}