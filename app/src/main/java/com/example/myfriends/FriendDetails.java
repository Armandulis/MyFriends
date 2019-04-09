package com.example.myfriends;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class FriendDetails extends AppCompatActivity {

    FriendBE friend;
    private ISQLiteFriends dataAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);
        dataAccess = DataAccessFactory.getInstance(this);


        Intent intent = getIntent();
        friend = (FriendBE) intent.getSerializableExtra("friend");
        setUpDetails();

    }

    private void setUpDetails(){
        TextView txName = (TextView) findViewById(R.id.textview_name);
        txName.setText(friend.name);
        TextView txAddress = (TextView) findViewById(R.id.textview_address);
        txAddress.setText(friend.address);

        FloatingActionButton mapButton = (FloatingActionButton) findViewById(R.id.button_map);
        if (!friend.address.equals("Unknown")){

            mapButton.show(); //SHOW the button

        }else mapButton.hide();

        TextView txPhone = (TextView) findViewById(R.id.textview_phone);
        txPhone.setText(friend.phone);
        TextView txMail = (TextView) findViewById(R.id.textview_mail);
        txMail.setText(friend.mail);
        TextView txBirthday = (TextView) findViewById(R.id.textview_birthday);
        txBirthday.setText(friend.birthday);
        TextView txWebsite = (TextView) findViewById(R.id.textview_website);
        txWebsite.setText(friend.website);
        ImageView imagePicture = (ImageView) findViewById(R.id.imageview_picture);
        //imagePicture.setImage();

        if (!friend.birthday.equals("Unknown")){
            FloatingActionButton birthdayButton = (FloatingActionButton) findViewById(R.id.button_birthday);
            String partsOfDate[] = friend.birthday.split("/");
            String birthdayMonth = partsOfDate[1];
            String birthdayDay = partsOfDate[2];
            Calendar cal = Calendar.getInstance();
            String currentDay = cal.get(Calendar.DAY_OF_MONTH) + "";
            String currentMonth = cal.get(Calendar.MONTH) + 1 +"";

            if ( birthdayMonth.equals(currentMonth) && birthdayDay.equals(currentDay)) birthdayButton.show(); //SHOW the button
            else birthdayButton.hide();
        }



    }
    public void openEmailBtn(View view) {
    }

    public void openWebsiteBtn(View view) {
        String url = friend.website;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void openEditBtn(View view) {
        Intent editFriendIntent = new Intent(this, EditFriend.class);
        editFriendIntent.putExtra("friend", friend );
        startActivityForResult(editFriendIntent, Common.RESULT_CODE);
    }

    public void deleteFriendBtn(View view) {
        dataAccess.deleteFriend(friend.id);
        finish();
    }

    public void sendBirthdayMessageBtn(View view) {
    }

    public void openMapBtn(View view) {
        startActivity(new Intent(FriendDetails.this, MapActivity.class));
    }

    public void openMessageBtn(View view) {
    }

    public void openCallBtn(View view) {
    }

    public void openPictureBtn(View view) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if(requestCode == Common.RESULT_CODE){
                if(data.getSerializableExtra("friend") != null){
                    friend = (FriendBE) data.getSerializableExtra("friend");
                    setUpDetails();
                } else {
                    Toast.makeText(this, "Friend was not updated", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }



    }
}
