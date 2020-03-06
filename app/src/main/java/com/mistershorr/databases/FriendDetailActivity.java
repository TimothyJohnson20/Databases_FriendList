package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.social.BackendlessSocialJSInterface;

import java.util.HashMap;
import java.util.Map;

public class FriendDetailActivity extends AppCompatActivity {

    private EditText name;
    private TextView clumsinessText;
    private SeekBar clumsiness;
    private Switch isAwesome;
    private TextView gymFrequencyText;
    private SeekBar gymFrequency;
    private TextView trustText;
    private RatingBar trustRating;
    private TextView moneyOwed;
    private Button finishedCreate;

    private String newName;
    private int newClumsiness;
    private boolean newIsAwesome;
    private int newGymFrequency;
    private int newTrust;
    private int newMoneyOwed;
    private Friend friend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        wireWidgets();
        setListeners();

        Intent lastIntent = getIntent();
        friend = lastIntent.getParcelableExtra(FriendListActivity.FRIEND);
        if(friend == null){
            friend = new Friend();
//            clumsiness.setProgress(0);
//            isAwesome.setChecked(false);
//            gymFrequency.setProgress(0);
//            trustRating.setNumStars(0);
//            moneyOwed.setText("" + 0);
        }
        else{
            name.setText(friend.getName());
            clumsiness.setProgress(friend.getClumsiness());
            isAwesome.setChecked(friend.isAwesome());
            gymFrequency.setProgress(((int) friend.getGymFrequency()));
            trustRating.setNumStars(friend.getTrustworthiness());
            moneyOwed.setText("" + friend.getMoneyOwed());
        }


        clumsinessText.setText("Clumsiness: ");
        gymFrequencyText.setText("Gym Frequency: ");
        trustText.setText("Trust Rating: ");


    }

    private void wireWidgets(){
        name = findViewById(R.id.editText_friendDetail_name);
        clumsinessText = findViewById(R.id.textView_friendDetail_clumsy);
        clumsiness = findViewById(R.id.seekBar_friendDetail_Clumsiness);
        isAwesome = findViewById(R.id.switch_friendDetail_isAwesome);
        gymFrequencyText = findViewById(R.id.textView_friendDetail_gymFrequency);
        gymFrequency = findViewById(R.id.seekBar_friendDetail_gymFrequency);
        trustText = findViewById(R.id.textView_friendDetail_trust);
        trustRating = findViewById(R.id.ratingBar_friendDetail_trustRating);
        finishedCreate = findViewById(R.id.button_friendDetail_create);
        moneyOwed = findViewById(R.id.editText_friendDetail_moneyOwed);

        clumsiness.setMax(10);
        clumsiness.setKeyProgressIncrement(1);
        gymFrequency.setKeyProgressIncrement(1);
        gymFrequency.setMax(7);

    }

    private void setListeners(){
        finishedCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newName = name.getText().toString();
                newClumsiness = clumsiness.getProgress();
                if (isAwesome.isChecked()){
                    newIsAwesome = true;
                }
                else{
                    newIsAwesome = false;
                }
                newGymFrequency = gymFrequency.getProgress();
                newTrust = trustRating.getNumStars();

                friend.setName(newName);
                friend.setClumsiness(newClumsiness);
                friend.setAwesome(newIsAwesome);
                friend.setGymFrequency(newGymFrequency);
                friend.setTrustworthiness(newTrust);
                friend.setMoneyOwed(newMoneyOwed);
                // make a backendless call to create a new object on the server
                Backendless.Persistence.save( friend, new AsyncCallback<Friend>() {
                    public void handleResponse( Friend response )
                    {
                        Intent newFriendIntent = new Intent(FriendDetailActivity.this, FriendListActivity.class);
                        startActivity(newFriendIntent);
                    }

                    public void handleFault( BackendlessFault fault )
                    {
                        Toast.makeText(FriendDetailActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }


}
