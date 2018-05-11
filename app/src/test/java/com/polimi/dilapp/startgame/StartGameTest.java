package com.polimi.dilapp.startgame;


import com.polimi.dilapp.database.AppDatabase;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AppDatabase.class})
public class StartGameTest {

    @Before
    public void test(){
        PowerMockito.mockStatic(AppDatabase.class);
    }

}
