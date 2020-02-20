package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class FriendDetailActivity extends AppCompatActivity {

    private EditText name;
    private TextView clumsinessText;
    private SeekBar clumsiness;
    private Switch isAwesome;
    private TextView gymFrequencyText;
    private SeekBar gymFrequency;
    private TextView trustText;
    private RatingBar trustRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
    }

    private void wireWidgets(){

    }


}
