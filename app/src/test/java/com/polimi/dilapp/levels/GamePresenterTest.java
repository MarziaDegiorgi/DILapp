package com.polimi.dilapp.levels;


import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.polimi.dilapp.R;
import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.AppDatabase_Impl;
import com.polimi.dilapp.database.ChildDao;
import com.polimi.dilapp.database.DatabaseInitializer;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, AppDatabase.class, SystemClock.class, Toast.class, NfcAdapter.class, Intent.class, AsyncTask.class, DatabaseInitializer.class, DateUtils.class})
public class GamePresenterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IGame.View iGame;

    @Mock
    private AppDatabase_Impl appDatabaseImpl;

    @Mock
    private AppDatabase appDatabase;

    @Mock
    private GamePresenter gamePresenter;

    @Mock
    private ChildDao childDao;

    @Mock
    private ArrayList<String> mockedArray;

    @Mock
    private ArrayList<Integer> intArray;

    @Mock
    private Toast toast;

    @Mock
    private NfcAdapter nfcAdapter;

    @Mock
    private Bundle savedInstanceState;

    @Mock
    private Date date;

    @Mock
    private DatabaseInitializer databaseInitializer;

    @Mock
    private ArrayList<Date> datelist;

    @Mock
    private ArrayList<Float> progressList;

    @Mock
    private ArrayList<Integer> intList;



    @Before
    public void test() {

        //startGameTest initialization
        long longNumber = 1;
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(NfcAdapter.class);
        PowerMockito.mockStatic(SystemClock.class);
        PowerMockito.mockStatic(Intent.class);
        PowerMockito.mockStatic(Toast.class);
        PowerMockito.mockStatic(AsyncTask.class);
        PowerMockito.mockStatic(Handler.class);
        PowerMockito.mockStatic(Bundle.class);
        PowerMockito.mockStatic(DateUtils.class);
        PowerMockito.mockStatic(Calendar.class);

        when(Log.i(any(String.class), any(String.class))).thenReturn(1);
        when(Log.e(any(String.class), any(String.class), any(Throwable.class))).thenReturn(1);
        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabaseImpl);
        when(appDatabaseImpl.childDao()).thenReturn(childDao);
        when(iGame.getString()).thenReturn("ActivityOneOne");
        when(SystemClock.elapsedRealtime()).thenReturn(longNumber);
        when(Toast.makeText(any(Context.class), any(String.class), any(int.class))).thenReturn(toast);


        //isTheCurrentSessionArrayEmpty
        ArrayList<String> currentSessionArray = new ArrayList<>();
        when(iGame.getSessionArray(any(int.class))).thenReturn(currentSessionArray);


        try {
            gamePresenter = new GamePresenter(iGame);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //Here we test startGame(...);
    @Test
    public void gameIsStarted() {
        ArrayList<String> currentSequence = new ArrayList<>();
        currentSequence.add("a");
        gamePresenter.startGame(currentSequence);
        assertTrue(gamePresenter.isStarted());
    }


    @Test
    public void emptyCurrentSequenceInStartGame() {
        ArrayList<String> currentSequence = new ArrayList<>();
        gamePresenter.startGame(currentSequence);
        assertFalse(gamePresenter.isStarted());
    }

    //Here we test getResourceId(... , ...);
    @Test
    public void getResourceIdCorrectFunctioningTest() {
        String name = "lemon";
        int resourceId = gamePresenter.getResourceId(name, R.drawable.class);

        Assert.assertEquals(resourceId, R.drawable.lemon);
    }

    @Test(expected = Exception.class)
    public void getResourceIdThrowingExceptionTest() {
        gamePresenter.getResourceId(any(String.class), R.drawable.class);
    }


    //Here we test startNewSession(...);
    @Test
    public void startNewSessionTest() {
        String currentSequenceElement = "subset_names_one";
        when(iGame.getSessionArray(any(int.class))).thenReturn(mockedArray);
        try {
            Whitebox.invokeMethod(gamePresenter, "startNewSession", currentSequenceElement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(gamePresenter.getNewSessionStarted());
    }

    //Here we test startNewTurn with empty sequence
    @Test
    public void isTheGameEnded() {
        ArrayList<String> emptySequence = new ArrayList<>();
        emptySequence.add("test");
        gamePresenter.startGame(emptySequence);
        try {
            Whitebox.invokeMethod(gamePresenter, "startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int diff = gamePresenter.getTotalAttempts() - gamePresenter.getCorrectAnswers();
        int percentage = (20 * gamePresenter.getTotalAttempts()) / 100;
        if (diff > percentage) {
            verify(iGame, Mockito.times(

                    1)).setRepeatOrExitScreen();
        } else {
            verify(iGame, Mockito.times(1)).setGoOnOrExitScreen();
        }
        assertTrue(gamePresenter.isEnded());

    }

    @Test
    public void isTheNewTurnStarted() {
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        try {
            Whitebox.invokeMethod(gamePresenter, "startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(gamePresenter.getNewTurnStarted());
        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("startNewSession", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Here we test chooseElement();
    @Test
    public void isTheNewElementChosen() {
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.chooseElement();

        assertEquals("carrot", gamePresenter.getCurrentSequenceElement());
        assertEquals(1, gamePresenter.getNumberOfElements());
    }

    @Test
    public void isTheNewColoredElementChosen() {
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);
        gamePresenter.setColourLevel();

        gamePresenter.chooseElement();
        assertEquals(false, gamePresenter.getNewTurnStarted());
        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("chooseColour");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void isTheNewRecipeElementChosen() {
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);
        gamePresenter.setColourLevel();

        gamePresenter.chooseElement();

        assertEquals(false, gamePresenter.getNewTurnStarted());
        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("chooseRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noMoreElementsInTheTempArray() {
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.chooseElement();

        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Here we test askCurrentElement();
    @Test
    public void isTheCurrentElementAsked() {
        gamePresenter.askCurrentElement();
        verify(iGame, Mockito.times(1)).setPresentationAnimation(gamePresenter.getCurrentSequenceElement());
    }


    //Here we test correctAnswer()
    @Test
    public void correctAnswerTest() {
        int total = gamePresenter.getTotalAttempts();
        int correct = gamePresenter.getCorrectAnswers();
        total++;
        correct++;

        try {
            Whitebox.invokeMethod(gamePresenter, "correctAnswer");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(0, gamePresenter.getCounter());
        Assert.assertEquals(total, gamePresenter.getTotalAttempts());
        Assert.assertEquals(correct, gamePresenter.getCorrectAnswers());

        verify(iGame, Mockito.times(1)).setVideoCorrectAnswer();

    }

    //Here we test wrongAnswer()
    @Test
    public void wrongAnswerElseTest() {
        int total = gamePresenter.getTotalAttempts();
        total++;
        gamePresenter.setCounter(2);
        try {
            Whitebox.invokeMethod(gamePresenter, "wrongAnswer", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(0, gamePresenter.getCounter());
        Assert.assertEquals(total, gamePresenter.getTotalAttempts());
        verify(iGame, Mockito.times(1)).setVideoWrongAnswerAndGoOn();
    }

    @Test
    public void wrongAnswerIfTest() {
        int total = gamePresenter.getTotalAttempts();
        int counter = gamePresenter.getCounter();
        counter++;
        total++;
        gamePresenter.setCounter(0);
        try {
            Whitebox.invokeMethod(gamePresenter, "wrongAnswer", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(counter, gamePresenter.getCounter());
        Assert.assertEquals(total, gamePresenter.getTotalAttempts());
        verify(iGame, Mockito.times(1)).setVideoWrongAnswerToRepeat();
    }


    //Here we test notifyFirstSubElement()
    @Test
    public void notifyFirstSubElementTest() {
        gamePresenter.notifyFirstSubElement();
        verify(iGame, Mockito.times(1)).initTableView(gamePresenter.getCurrentSubElement());
    }

    //Here we test checkNfcAvailability()
    @Test
    public void nfcNotAvailableOrEnabledTest() {
        when(NfcAdapter.getDefaultAdapter(any(Context.class))).thenReturn(null);
        boolean check = gamePresenter.checkNfcAvailability();
        Assert.assertEquals(false, check);
    }

    @Test
    public void nfcAvailableTest() {
        when(NfcAdapter.getDefaultAdapter(any(Context.class))).thenReturn(nfcAdapter);
        boolean check = gamePresenter.checkNfcAvailability();
        Assert.assertEquals(true, check);
    }

    //Here we test checkAnswer()for basic items
    @Test
    public void checkAnswerBasicItemTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", "carrot");
            Whitebox.invokeMethod(gamePresenter, "checkAnswer", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("check", "carrot");
            } catch (Exception e) {
            e.printStackTrace();
            }

        Assert.assertEquals(false, gamePresenter.isTheNfcEnabled());
    }


    //Here we test checkAnswer() for color items
    @Test
    public void checkAnswerCorrectColorItemTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("yellow");
        notEmptySequence.add("red");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setColourLevel();

        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", "yellow");
            Whitebox.invokeMethod(gamePresenter, "checkAnswer", "yellow");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("correctAnswerColour");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(false, gamePresenter.isTheNfcEnabled());
    }

    @Test
    public void checkAnswerWrongColorItemTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("yellow");
        notEmptySequence.add("red");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setColourLevel();

        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", "white");
            Whitebox.invokeMethod(gamePresenter, "checkAnswer", "white");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("wrongAnswerColour", "white");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(false, gamePresenter.isTheNfcEnabled());
    }


    //Here we test checkAnswer() for recipe items
    @Test
    public void checkAnswerCorrectRecipeItemTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("apple");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setColourLevel();

        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", "apple");
            Whitebox.invokeMethod(gamePresenter, "checkAnswer", "apple");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("correctAnswerRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(false, gamePresenter.isTheNfcEnabled());

    }

    @Test
    public void checkAnswerWrongRecipeItemTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("apple");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setColourLevel();

        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", "pear");
            Whitebox.invokeMethod(gamePresenter, "checkAnswer", "pear");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("wrongAnswerRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(false, gamePresenter.isTheNfcEnabled());

    }

    //Here we test checkAnswer() for multiple items
    @Test
    public void checkCorrectAnswerMultipleItemTest() {
        String currentElement = "_peperone";
        String currentSubElement= "e";
        int numberOfElements = 8;

        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
            Whitebox.invokeMethod(gamePresenter, "checkAnswer", "p" );
        } catch (Exception e) {
            e.printStackTrace();
        }

        numberOfElements--;

        assertEquals(numberOfElements, gamePresenter.getNumberOfElements());
        assertEquals(currentSubElement, gamePresenter.getCurrentSubElement());

        verify(iGame, Mockito.times(1)).setSubItemAnimation(currentSubElement);
    }

    @Test
    public void checkWrongAnswerMultipleItem(){
        String currentElement = "_peperone";
        String currentSubElement= "p";
        int numberOfElements = 8;

        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
            Whitebox.invokeMethod(gamePresenter, "checkAnswer", "e" );
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(currentSubElement, gamePresenter.getCurrentSubElement());
        assertEquals(numberOfElements, gamePresenter.getNumberOfElements());

        verify(iGame, Mockito.times(1)).setVideoWrongAnswerToRepeat();
    }

    @Test
    public void isMultipleItemTest() {
       String currentElement = "_mela";
        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
       assertTrue(gamePresenter.getMultipleElement());
    }

    @Test
    public void isNotMultipleItemTest() {
        String currentElement = "ahcbjdckjernkjnvkerqvlkrnvlnrvjnrekjsssnjvqe";
        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(gamePresenter.getMultipleElement());
    }

    @Test
    public void singleSubElementTest() {
        String currentElement = "_1";
        try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(gamePresenter.getMultipleElement());
    }

    @Test
    public void checkSubElementTest() {
    String currentSubElement ="1";
    String currentElement="_12";

    try {
            Whitebox.invokeMethod(gamePresenter, "checkMultipleItems", currentElement);
        } catch (Exception e) {
            e.printStackTrace();
        }

    gamePresenter.notifyFirstSubElement();

    assertEquals(currentSubElement, gamePresenter.getCurrentSubElement());
    verify(iGame, Mockito.times(1)).initTableView(currentSubElement);
    }


    //Here we test onDestroy()
    @Test
    public void onDestroyTest() {
        gamePresenter.onDestroy();
        Assert.assertEquals(null, gamePresenter.getActivityInterface());
    }

    //Here we test verifyTotalAttempts() for single items
    @Test
    public void verifyTotalAttemptsIfTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setTotalAttempts();

        gamePresenter.setCounter(1);

        try {
            Whitebox.invokeMethod(gamePresenter, "verifyTotalAttempts");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1, gamePresenter.getTotalAttempts());
        Assert.assertEquals(2,gamePresenter.getCounter());
        verify(iGame, Mockito.times(1)).setVideoWrongAnswerToRepeat();
    }

    @Test
    public void verifyTotalAttemptsElseTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setTotalAttempts();

        gamePresenter.setCounter(2);

        try {
            Whitebox.invokeMethod(gamePresenter, "verifyTotalAttempts");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1, gamePresenter.getTotalAttempts());
        Assert.assertEquals(0,gamePresenter.getCounter());
        verify(iGame, Mockito.times(1)).setVideoWrongAnswerAndGoOn();
    }

    //Here we test check()
    @Test
    public void checkBasicTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setCurrentElement("carrot");
        try {
            Whitebox.invokeMethod(gamePresenter, "check", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("correctAnswer");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkWrongAndShapeTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemonshape");
        notEmptySequence.add("carrotshape");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setCurrentElement("carrotshape");
        try {
            Whitebox.invokeMethod(gamePresenter, "check", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("correctAnswer");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkWrongBasicTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemon");
        notEmptySequence.add("carrot");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setCurrentElement("carrot");

        try {
            Whitebox.invokeMethod(gamePresenter, "check", "apple");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("wrongAnswer", "apple");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void checkWrongShapeTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("lemonshape");
        notEmptySequence.add("carrotshape");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setCurrentElement("carrotshape");

        try {
            Whitebox.invokeMethod(gamePresenter, "check", "appleshape");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("wrongAnswer", "apple");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Here we test correctAnswerColor()
    @Test
    public void correctAnswerColorIfTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("red");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setTotalAttempts();
        gamePresenter.setCorrectAnswers(0);
        gamePresenter.setCounterColourSession(5);

        try {
            Whitebox.invokeMethod(gamePresenter, "correctAnswerColour");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(0, gamePresenter.getCounter());
        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void correctAnswerColorElseContinueTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("violet");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setTotalAttempts();
        gamePresenter.setCorrectAnswers(0);
        gamePresenter.setCounterColourSession(0);

        try {
            Whitebox.invokeMethod(gamePresenter, "correctAnswerColour");
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(iGame, Mockito.times(1)).setVideoCorrectAnswer();
    }

    @Test
    public void correctAnswerColorElseNewTurnTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("red");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setTotalAttempts();
        gamePresenter.setCorrectAnswers(0);
        gamePresenter.setCounterColourSession(3);

        try {
            Whitebox.invokeMethod(gamePresenter, "correctAnswerColour");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(0, gamePresenter.getCounter());
        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        }


    //Here we test wrongAnswerColour()
    @Test
    public void wrongAnswerColourIfTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("red");
        gamePresenter.startGame(notEmptySequence);

        int total = gamePresenter.getTotalAttempts();
        int counter = gamePresenter.getCounter();
        counter++;
        total++;

        gamePresenter.setCounter(0);
        try {
            Whitebox.invokeMethod(gamePresenter, "wrongAnswerColour", "carrot");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(counter, gamePresenter.getCounter());
        Assert.assertEquals(total, gamePresenter.getTotalAttempts());
        verify(iGame, Mockito.times(1)).setVideoWrongAnswerToRepeat();
    }

    @Test
    public void wrongAnswerColourElseTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("red");
        gamePresenter.startGame(notEmptySequence);

        int total = gamePresenter.getTotalAttempts();
        total++;

        gamePresenter.setCounter(3);

        try {
            Whitebox.invokeMethod(gamePresenter, "wrongAnswerColour", "red");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(0, gamePresenter.getCounterColourSession());
        Assert.assertEquals(total, gamePresenter.getTotalAttempts());
        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Here we test correctAnswerRecipe()
    @Test
    public void correctAnswerRecipeIfTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        ArrayList<String> emptySequence = new ArrayList<>();
        gamePresenter.setTempArray(emptySequence);
        try {
            Whitebox.invokeMethod(gamePresenter, "correctAnswerRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(0, gamePresenter.getCounter());
    }

    @Test
    public void correctAnswerRecipeElseTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        ArrayList<String> sequence = new ArrayList<>();
        sequence.add("apple");
        sequence.add("lemon");
        gamePresenter.setTempArray(sequence);

        try {
            Whitebox.invokeMethod(gamePresenter, "correctAnswerRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(iGame, Mockito.times(1)).setVideoCorrectAnswer();
        Assert.assertEquals(0, gamePresenter.getCounter());
    }

    //Here we test wrongAnswerRecipe()
    @Test
    public void wrongAnswerRecipeIfTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);
        gamePresenter.setCounter(5);

        try {
            Whitebox.invokeMethod(gamePresenter, "wrongAnswerRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(6, gamePresenter.getCounter());
        verify(iGame, Mockito.times(1)).setVideoWrongAnswerToRepeat();

    }

    @Test
    public void wrongAnswerRecipeElseTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);
        gamePresenter.setCounter(6);

        try {
            Whitebox.invokeMethod(gamePresenter, "wrongAnswerRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PowerMockito.verifyPrivate(gamePresenter, times(1)).invoke("startNewTurn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(0, gamePresenter.getCounter());

    }


    //Here we test chooseColour()
    @Test
    public void chooseColourTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        try {
            Whitebox.invokeMethod(gamePresenter, "chooseColour");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(false, gamePresenter.getNewTurnStarted());
        verify(iGame, Mockito.times(1)).setPresentationAnimation(gamePresenter.getCurrentSequenceElement());

    }

    //Here we test chooseRecipe()
    @Test
    public void chooseRecipeTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        try {
            Whitebox.invokeMethod(gamePresenter, "chooseRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(false, gamePresenter.getNewTurnStarted());
        verify(iGame, Mockito.times(1)).setPresentationAnimation(gamePresenter.getCurrentSequenceElement());

    }

    //Here we test setLevelCurrentPlayer()
    @Test
    public void setLevelCurrentPlayerOneOneTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        when(DatabaseInitializer.getLevelCurrentPlayer(any(AppDatabase.class))).thenReturn(11);
        when(iGame.getString()).thenReturn("ActivityOneOne");

       gamePresenter.setLevelCurrentPlayer();
       assertEquals(true, gamePresenter.getSavedNewLevel());
        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, 11);
    }

    @Test
    public void setLevelCurrentPlayerOneTwoTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        when(DatabaseInitializer.getLevelCurrentPlayer(any(AppDatabase.class))).thenReturn(11);
        when(iGame.getString()).thenReturn("ActivityOneTwo");

        gamePresenter.setLevelCurrentPlayer();
        assertEquals(true, gamePresenter.getSavedNewLevel());
        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, 11);
    }

    @Test
    public void setLevelCurrentPlayerOneThreeTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        when(DatabaseInitializer.getLevelCurrentPlayer(any(AppDatabase.class))).thenReturn(11);
        when(iGame.getString()).thenReturn("ActivityOneThree");

        gamePresenter.setLevelCurrentPlayer();
        assertEquals(true, gamePresenter.getSavedNewLevel());
        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, 11);
    }

    @Test
    public void setLevelCurrentPlayerTwoOneTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        when(DatabaseInitializer.getLevelCurrentPlayer(any(AppDatabase.class))).thenReturn(11);
        when(iGame.getString()).thenReturn("ActivityTwoOne");

        gamePresenter.setLevelCurrentPlayer();
        assertEquals(true, gamePresenter.getSavedNewLevel());
        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, 11);
    }

    @Test
    public void setLevelCurrentPlayerTwoTwoTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        when(DatabaseInitializer.getLevelCurrentPlayer(any(AppDatabase.class))).thenReturn(11);
        when(iGame.getString()).thenReturn("ActivityTwoTwo");

        gamePresenter.setLevelCurrentPlayer();
        assertEquals(true, gamePresenter.getSavedNewLevel());
        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, 11);
    }

    @Test
    public void setLevelCurrentPlayerTwoThreeTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        when(DatabaseInitializer.getLevelCurrentPlayer(any(AppDatabase.class))).thenReturn(11);
        when(iGame.getString()).thenReturn("ActivityTwoThree");

        gamePresenter.setLevelCurrentPlayer();
        assertEquals(true, gamePresenter.getSavedNewLevel());
        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, 11);
    }

    @Test
    public void setLevelCurrentPlayerThreeOneTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        when(DatabaseInitializer.getLevelCurrentPlayer(any(AppDatabase.class))).thenReturn(11);
        when(iGame.getString()).thenReturn("ActivityThreeOne");

        gamePresenter.setLevelCurrentPlayer();
        assertEquals(true, gamePresenter.getSavedNewLevel());
        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, 11);
    }

    @Test
    public void setLevelCurrentPlayerThreeTwoTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        when(DatabaseInitializer.getLevelCurrentPlayer(any(AppDatabase.class))).thenReturn(11);
        when(iGame.getString()).thenReturn("ActivityThreeTwo");

        gamePresenter.setLevelCurrentPlayer();
        assertEquals(true, gamePresenter.getSavedNewLevel());
        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setLevelCurrentPlayer(appDatabase, 11);
    }

    @Test
    public void setLevelCurrentPlayerElseTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        when(DatabaseInitializer.getLevelCurrentPlayer(any(AppDatabase.class))).thenReturn(21);
        when(iGame.getString()).thenReturn("ActivityOneOne");

        gamePresenter.setLevelCurrentPlayer();
        assertEquals(false, gamePresenter.getSavedNewLevel());
    }

    //Here we test setObjectCurrentPlayer()
    @Test
    public void setObjectCurrentPlayerTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setCurrentElement("banana");
        gamePresenter.setFlagSaveNewLevel();
        gamePresenter.setObjectCurrentPlayer();

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setObjectCurrentPlayer(appDatabase,"banana");
    }

    //Here we test setSubsringCurrentPlayer()
    @Test
    public void setSubstringCurrentPlayerTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.setCurrentSequenceElement();
        gamePresenter.setFlagSaveNewLevel();
        gamePresenter.setSubStringCurrentPlayer();

        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.setSubStringCurrentPlayer(appDatabase, "apple");
    }

    //Here we test storeCurrentPlayer()
    @Test
    public void storeCurrentPlayerTest(){
        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        gamePresenter.storeCurrentPlayer(savedInstanceState);

        verify(savedInstanceState, Mockito.times(1)).putInt("current_player", DatabaseInitializer.getCurrentPlayer(appDatabase));
        verify(savedInstanceState, Mockito.times(1)).putInt("level", DatabaseInitializer.getLevelCurrentPlayer(appDatabase));
        verify(savedInstanceState, Mockito.times(1)).putString("object", DatabaseInitializer.getObjectCurrentPlayer(appDatabase));
        verify(savedInstanceState, Mockito.times(1)).putString("subString", DatabaseInitializer.getSubStringCurrentPlayer(appDatabase));

    }

    //Here we test storeProgress()
    //TODO: TEST REMAINING BRANCHES OF STOREPROGRESS()

    @Test
    public void storeProgressIfIfTest(){

        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        ArrayList<String> list = new ArrayList<>();
        list.add("carrot");
        list.add("apple");

        ArrayList<Date> dateList = new ArrayList<>();
        dateList.add(new Date(11/ 2 /2016));
        dateList.add(new Date(12/ 2 /2016));

        when(Calendar.getInstance().getTime()).thenReturn(date);
        when(DateUtils.isToday(any(Long.class))).thenReturn(false);

        gamePresenter.setInitTime();
        gamePresenter.setEndTime();
        gamePresenter.setErrorList(list);
        gamePresenter.setDateList(dateList);

        gamePresenter.storeProgress();


        verify(databaseInitializer, Mockito.times(4));
        DatabaseInitializer.setProgressDate(appDatabase, DatabaseInitializer.getCurrentPlayer(appDatabase), datelist);
        DatabaseInitializer.setProgress(appDatabase, DatabaseInitializer.getCurrentPlayer(appDatabase), progressList);
        DatabaseInitializer.setCorrectAnswer(appDatabase, DatabaseInitializer.getCurrentPlayer(appDatabase), intList);
        DatabaseInitializer.setTime(appDatabase, DatabaseInitializer.getCurrentPlayer(appDatabase), intList);

    }

    @Test
    public void storeProgressElseTest(){

        ArrayList<String> notEmptySequence = new ArrayList<>();
        notEmptySequence.add("white");
        gamePresenter.startGame(notEmptySequence);

        ArrayList<String> list = new ArrayList<>();
        list.add("carrot");
        list.add("apple");

        ArrayList<Date> dateList = new ArrayList<>();


        when(Calendar.getInstance().getTime()).thenReturn(date);
        when(DateUtils.isToday(any(Long.class))).thenReturn(true);

        gamePresenter.setInitTime();
        gamePresenter.setEndTime();
        gamePresenter.setErrorList(list);
        gamePresenter.setDateList(dateList);

        gamePresenter.storeProgress();


        verify(databaseInitializer, Mockito.times(4));
        DatabaseInitializer.setProgress(appDatabase,DatabaseInitializer.getCurrentPlayer(appDatabase) , progressList);
        DatabaseInitializer.setProgressDate(appDatabase, DatabaseInitializer.getCurrentPlayer(appDatabase), dateList);
        DatabaseInitializer.setCorrectAnswer(appDatabase, DatabaseInitializer.getCurrentPlayer(appDatabase), intList);
        DatabaseInitializer.setTime(appDatabase, DatabaseInitializer.getCurrentPlayer(appDatabase), intList);
    }


}