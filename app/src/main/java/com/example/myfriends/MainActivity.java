package com.example.myfriends;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewFriends;
    private ArrayList<FriendBE> listFriends;
    private ListViewFriendsAdapter adapter;
    private ISQLiteFriends dataAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataAccess = DataAccessFactory.getInstance(this);

        listViewFriends = findViewById(R.id.listview_friends);

        setUpFriendsList();
        listViewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                   Intent intent = new Intent(MainActivity.this, FriendDetails.class);
                FriendBE friend = (FriendBE) parent.getItemAtPosition(position);

                  intent.putExtra("friend", friend );
                startActivityForResult(intent, Common.RESULT_CODE);
            }
        });
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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

            case R.id.menu_import:
                importContacts();
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

    private void viewAsList() {
        listFriends = dataAccess.getFriends();
        listViewFriends.setAdapter(null);
        adapter = new ListViewFriendsAdapter(this, listFriends);
        listViewFriends.setAdapter(adapter);
    }

    private void viewAsMap() {
        Intent mapIntent = new Intent(this, MapActivity.class);
        mapIntent.putExtra("listFriends", listFriends);
        mapIntent.putExtra("hasSearch", false);
        startActivity(mapIntent);
    }

    private void importContacts() {
        showContacts();
    }

    private void openAddFriendActivity(){
       Intent addFriendIntent = new Intent(this, AddFriend.class);
       startActivityForResult(addFriendIntent, Common.RESULT_CODE);
    }

    private void setUpFriendsList(){

        listFriends = dataAccess.getFriends();
        adapter = new ListViewFriendsAdapter(this, listFriends);
        listViewFriends.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setUpFriendsList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Common.REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                getContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot import contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, Common.REQUEST_READ_CONTACTS);
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getContacts();
        }
    }
    private void getContacts() {
        ContentResolver resolver = this.getContentResolver();

        Cursor contactCursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (contactCursor != null) {
            while (contactCursor.moveToNext()) {
                FriendBE friend = new FriendBE(0, "Unknown", "Unknown", "Unknown", "Unknown", "Unknown", "Unknown", "Unknown");
                String id = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                friend.name = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String picture = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_FILE_ID));
                if (picture != null) {
                    friend.picture = picture;
                }

                Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                if (phoneCursor != null) {
                    while (phoneCursor.moveToNext()) {
                        friend.phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phoneCursor.close();
                }

                Cursor emailCursor = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);

                if (emailCursor != null) {
                    while (emailCursor.moveToNext()) {
                        String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        if (email != null) {
                            friend.mail = email;
                        }
                    }
                    emailCursor.close();
                }
                dataAccess.createFriend(friend);

            }
            contactCursor.close();
        }
    }

}
