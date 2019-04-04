package com.example.myfriends;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewFriends;
    private ArrayList<FriendBE> listFriends = new ArrayList<>();
    private ListViewFriendsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewFriends = findViewById(R.id.listview_friends);

        someMockData();
        setUpFriendsList();
        listViewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                   Intent intent = new Intent(MainActivity.this, FriendDetails.class);
                FriendBE friend = (FriendBE) parent.getItemAtPosition(position);

                  intent.putExtra("friend", friend );
                  startActivity(intent);
            }
        });
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    private void someMockData(){
        FriendBE f = new FriendBE(1,
                "Armandas Bruzas",
                "Denmark Esbjerg Random Streed",
                "+8445751256",
                "Armandulis@gmail.com",
                "1998-02-2",
                "https://github.com/Armandulis",
                "big pic");
        FriendBE f2 = new FriendBE(1,
                "Lards Bilde",
                "Denmark Esbjerg more 6351 -8 Random Streed",
                "+84653218",
                "Armrandomis@gmail.com",
                "1985-02-2",
                "https://github.com/github",
                "small pic");
        listFriends.add(f);
        listFriends.add(f2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_add_friend:
                openAddFriendActivity();
                return true;

            case R.id.menu_group_import:
                importContacts();
                return true;


            case R.id.menu_order_name:
                orderByName();
                return true;

            case R.id.menu_order_distance:
                orderByDistance();
                return true;

            case R.id.menu_view_list:
                viewAsList();
                return true;

            case R.id.menu_view_map:
                viewAsMap();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void orderByDistance() {
    }

    private void orderByName() {
    }

    private void viewAsList() {
        listViewFriends.setAdapter(null);
        adapter = new ListViewFriendsAdapter(this, listFriends);
        listViewFriends.setAdapter(adapter);
    }

    private void viewAsMap() {
        //Implement Map!
        listViewFriends.setAdapter(null);
        adapter = new ListViewFriendsAdapter(this, listFriends);
        listViewFriends.setAdapter(adapter);
    }

    private void importContacts() {
    }

    private void openAddFriendActivity(){
        Intent addFriendIntent = new Intent(this, AddFriend.class);
        startActivity(addFriendIntent);
    }

    private void setUpFriendsList(){
        adapter = new ListViewFriendsAdapter(this, listFriends);
        listViewFriends.setAdapter(adapter);

    }
    public void openFriendDetails(View view){

    }
}
