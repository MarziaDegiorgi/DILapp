package com.polimi.dilapp.main;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;

import com.polimi.dilapp.database.AppDatabase;
import com.polimi.dilapp.database.AppDatabase_Impl;
import com.polimi.dilapp.database.ChildEntity;
import com.polimi.dilapp.database.DatabaseInitializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, DatabaseInitializer.class, AppDatabase.class, List.class,ImageButton.class, ChildEntity.class, Uri.class, ContentResolver.class, MediaStore.class, Image.class, MediaStore.Images.Media.class})
public class NewAccountPresenterTest {

    @Before
    public void test(){
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(AppDatabase.class);
        PowerMockito.mockStatic(DatabaseInitializer.class);
        PowerMockito.mockStatic(List.class);
        PowerMockito.mockStatic(ChildEntity.class);
        PowerMockito.mockStatic(Uri.class);
        PowerMockito.mockStatic(ContentResolver.class);
        PowerMockito.mockStatic(MediaStore.class);
        PowerMockito.mockStatic(Image.class);
        PowerMockito.mockStatic(MediaStore.Images.Media.class);
        PowerMockito.mockStatic(ImageButton.class);
        when(AppDatabase.getAppDatabase(any(Context.class))).thenReturn(appDatabaseImpl);
        newAccountPresenter = new NewAccountPresenter(addAccountView);
    }

    @Mock
    private AppDatabase appDatabase;

    @Mock
    private DatabaseInitializer databaseInitializer;

    @Mock
    private NewAccountPresenter newAccountPresenter;

    @Mock
    private INewAccount.View addAccountView;

    @Mock
    private AppDatabase_Impl appDatabaseImpl;

    @Mock
    private List<ChildEntity> childEntityList;

    @Mock
    private ChildEntity childEntity;

    @Mock
    private int num;

    @Mock
    private ImageButton imageButton;

    @Mock
    private Bitmap bitmap;


    //Here we test insertChild(...)
    //TODO: TEST INSERT CHILD
   /* @Test
    public void insertChildTest(){
        when(DatabaseInitializer.getListOfChildren(appDatabase)).thenReturn(childEntityList);
       // when(childEntityList.get(num).getId()).thenReturn(num);

        newAccountPresenter.insertChild(eq("string"),eq("string"),eq("string"));


        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.insertChild(appDatabase, eq("string"),eq("string"),eq("string"));
        verify(databaseInitializer, Mockito.times(1));
        DatabaseInitializer.insertReports(appDatabase, num);
    }*/


   //Here we test setPhoto(...);
    //TODO: TEST SET PHOTO
   /* @Test
    public void setPhotoExistingAccountTest(){

        when(appDatabase.childDao().getAll()).thenReturn(childEntityList);

        Uri uri = Uri.parse("uri");
        newAccountPresenter.setPhoto(uri);

        verify(addAccountView, Mockito.times(1)).setPhoto(any(String.class));
    }*/


    //Here we test reloadPhoto(...)
    @Test
    public void reloadPhotoTest(){
        String image = "image";
        //when(addAccountView.getContentRes()).thenReturn(any(ContentResolver.class));
        try {
            when(MediaStore.Images.Media.getBitmap(any(ContentResolver.class), any(Uri.class))).thenReturn(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        when(addAccountView.getAvatar()).thenReturn(imageButton);
        try {
            newAccountPresenter.reloadPhoto(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        verify(addAccountView, Mockito.times(1)).setBitmap(any(Bitmap.class));
        verify(imageButton, Mockito.times(1)).setImageBitmap(any(Bitmap.class));
    }


}
