package com.polimi.dilapp.report;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.polimi.dilapp.R;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MenuItem.class, Intent.class})
public class ReportMainPresenterTest {

    @Before
    public void test(){
        PowerMockito.mockStatic(MenuItem.class);
        PowerMockito.mockStatic(Intent.class);

        reportMainPresenter = new ReportMainPresenter(view);
    }

    @Mock
    private IReport.View view;

    @Mock
    private ReportMainPresenter reportMainPresenter;

    @Mock
    private MenuItem menuItem;

    @Mock
    private int num;

    @Mock
    private Context context;

    //Here we test onDestroy()
    @Test
    public void onDestroyTest(){
        reportMainPresenter.onDestroy();

        Assert.assertEquals(null, reportMainPresenter.getView());
    }

}
