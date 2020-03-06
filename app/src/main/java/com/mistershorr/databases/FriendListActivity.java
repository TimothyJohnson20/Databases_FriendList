package com.mistershorr.databases;

import android.content.Intent;
import android.os.Bundle;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FriendListActivity extends AppCompatActivity {

    private ListView friendsListView;
    private List<Friend> friendsList;
    private FloatingActionButton floatingActionButton;
    private FriendAdapter friendAdapter;
    public static final String FRIEND = "Friend";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        wireWidgets();
        setListeners();
        //  Search only for friends that have ownerIds that match the user's objectId

        String userId = Backendless.UserService.CurrentUser().getObjectId();
        String whereClause = "ownerId = '" + userId + "'";

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        Backendless.Data.of( Friend.class).find(new AsyncCallback<List<Friend>>(){
            @Override
            public void handleResponse( List<Friend> foundFriends )
            {
                Log.d("LOADED FRIENDS", "handleResponse: " + foundFriends.toString());
                //  TODO    make a custom adapter to display the friends and load the list that is retrieved into that adapter
                //  TODO    when a friend is clucked, it opens the detail activity that loads the info
                friendsList = foundFriends;

                FriendAdapter friendAdapter = new FriendAdapter(foundFriends);
                friendsListView.setAdapter(friendAdapter);
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(FriendListActivity.this, fault.getDetail(), Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton_friendList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newFriend = new Intent(FriendListActivity.this, FriendDetailActivity.class);
                startActivity(newFriend);
                wireWidgets();
            }
        });



    }

    public void wireWidgets(){
        friendsListView = findViewById(R.id.listView_Main_Friends);
        floatingActionButton = findViewById(R.id.floatingActionButton_friendList);
    }

    public void setListeners(){
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Friend clickedFriend = friendsList.get(i);
                Intent friendIntent = new Intent(FriendListActivity.this, FriendDetailActivity.class);
                friendIntent.putExtra(FRIEND, clickedFriend);
                startActivity(friendIntent);
                finish();
            }
        });
    }

    public void newFriend(Friend friend){
        friendsList.add(friend);
    }

    private class FriendAdapter extends ArrayAdapter<Friend> {
        private List<Friend> friendList;

        public FriendAdapter(List<Friend> friendList) {

            super(FriendListActivity.this, -1, friendList);
            this.friendList = friendList;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_friend, parent, false);
            }
            TextView name = convertView.findViewById(R.id.textView_friend_Name);
            TextView clumsiness = convertView.findViewById(R.id.TextView_Friend_Clumsiness);
            TextView moneyOwed = convertView.findViewById(R.id.textView_Friend_moneyOwed);

            name.setText(friendsList.get(position).getName());
            clumsiness.setText(friendList.get(position).getClumsiness()+  "");
            moneyOwed.setText("" + friendList.get(position).getMoneyOwed());
            return convertView;
        }
    }



}
