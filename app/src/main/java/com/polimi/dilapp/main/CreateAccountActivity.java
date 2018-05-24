package com.polimi.dilapp.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.polimi.dilapp.R;
import com.polimi.dilapp.database.ChildEntity;
import com.polimi.dilapp.database.DatabaseInitializer;
import com.polimi.dilapp.startgame.StartGameActivity;
import java.io.IOException;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class CreateAccountActivity extends AppCompatActivity implements ICreateAccount.View{

    ICreateAccount.Presenter presenter;
    public static final int CONTEXT_MENU_DELETE = 1;
    private ChildEntity childSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_createaccount);
        TextView mTextView = findViewById(R.id.createAccount);

        //Set up the presenter
        presenter = new CreateAccountPresenter(this);

        final List<ChildEntity> listOfChildren = presenter.getListOfChildren();

        // recovering the instance state
        if (listOfChildren.isEmpty()) {
            mTextView.setText(R.string.create_account);
        } else {
            mTextView.setText(R.string.select_account);

        }

        //link to already existing account of children
        LinearLayout account = findViewById(R.id.account);
        LinearLayout layout = findViewById(R.id.listOfAccounts);

        // get reference to LayoutInflater
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < listOfChildren.size(); i++) {

            //Creating copy of imagebutton by inflating it
            final LinearLayout box = (LinearLayout) inflater.inflate(R.layout.account_box, null);
            final ImageButton btn = box.findViewById(R.id.avatar);
            TextView name = box.findViewById(R.id.name);
            final ChildEntity temporaryChild = listOfChildren.get(i);
            final int temporaryChildId = temporaryChild.getId();
            btn.setId(temporaryChildId);
            Drawable drawable = null;
            try {
                drawable = new BitmapDrawable(getResources(), DatabaseInitializer.getChildPhoto(getContentResolver(),listOfChildren.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            btn.setImageDrawable(drawable);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent startGame = new Intent(getApplicationContext(), StartGameActivity.class);
                    Log.i("[CREATEACC ACTIVITY]", " Intent extra saved is "+ btn.getId());
                    startGame.putExtra(EXTRA_MESSAGE, btn.getId());
                    startActivity(startGame);
                    finish();
                }
            });
            btn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    for(ChildEntity c:listOfChildren){
                        if(c.getId() == btn.getId()){
                            childSelected = c;
                        }
                    }
                    registerForContextMenu(btn);
                    openContextMenu(btn);
                    return true;
                }
            });


            name.setText(listOfChildren.get(i).getName());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(account.getLayoutParams());
            params.setMargins(20,0,20,0);
            btn.setScaleType(ImageButton.ScaleType.CENTER_CROP);

            box.setLayoutParams(params);
            layout.addView(box);

        }


        //link to the add button that enables the user to create a new account
        LinearLayout newBox = (LinearLayout) inflater.inflate(R.layout.account_box, null);
        ImageButton newAccountButton = newBox.findViewById(R.id.avatar);
        newBox.findViewById(R.id.name).setVisibility(View.GONE);

        newAccountButton.setImageDrawable(getDrawable(R.drawable.avatar_add));
        newAccountButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        Intent newAccountIntent = new Intent(getApplicationContext(), NewAccountActivity.class);
        startActivity(newAccountIntent);
        finish();
    }
    });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(account.getLayoutParams());
        params.setMargins(20,0,20,0);
        newAccountButton.setScaleType(ImageButton.ScaleType.CENTER_CROP);
        newBox.setLayoutParams(params);
        layout.addView(newBox);
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i("[CREATEACC ACTIVITY]", "I'm in onSaveInstanceState");
        // call superclass to save any view hierarchy
        presenter.storeCurrentPlayer(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);

    }

    // This callback is called only when there is a saved instance previously saved using
    // onSaveInstanceState(). We restore some state in onCreate() while we can optionally restore
    // other state here, possibly usable after onStart() has completed.
    // The savedInstanceState Bundle is same as the _1 used in onCreate().
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("[CREATEACC ACTIVITY]", "I'm in onRestoreInstanceState");

        presenter.resumeCurrentPlayer(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        //Context menu
        menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Elimina");
    }

    @Override
    public boolean onContextItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE: {
                presenter.deletePlayer(childSelected);
                Intent createAccountIntent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(createAccountIntent);
                finish();
            }
        }
        return true;
    }

}
