package com.polimi.dilapp.main;


import android.arch.persistence.room.Database;
import android.content.Context;
import android.os.Bundle;

import com.polimi.dilapp.database.ChildEntity;

import java.util.List;

public interface ICreateAccount {
    interface  View {
        Context getContext();
    }

    interface  Presenter {
        List<ChildEntity> getListOfChildren();
        void resumeCurrentPlayer(Bundle savedInstanceState);
        void storeCurrentPlayer(Bundle savedInstanceState);
    }

}
