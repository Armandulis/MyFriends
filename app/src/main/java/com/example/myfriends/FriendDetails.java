package com.example.myfriends;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class FriendDetails extends AppCompatActivity {

    FriendBE friend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);
        setUpDetails();

    }

    private void setUpDetails(){
        Intent intent = getIntent();
        friend = (FriendBE) intent.getSerializableExtra("friend");

        TextView txName = (TextView) findViewById(R.id.textview_name);
        txName.setText(friend.name);
        TextView txAddress = (TextView) findViewById(R.id.textview_address);
        txAddress.setText(friend.address);
        TextView txPhone = (TextView) findViewById(R.id.textview_phone);
        txAddress.setText(friend.phone);
        TextView txMail = (TextView) findViewById(R.id.textview_mail);
        txMail.setText(friend.mail);
        TextView txBirthday = (TextView) findViewById(R.id.textview_birthday);
        txBirthday.setText(friend.birthday);
        TextView txWebsite = (TextView) findViewById(R.id.textview_website);
        txWebsite.setText(friend.website);
        ImageView imagePicture = (ImageView) findViewById(R.id.imageview_picture);
        //imagePicture.setImage();

    }
    public void openEmailBtn(View view) {
    }

    public void openWebsiteBtn(View view) {
    }

    public void openEditBtn(View view) {
    }

    public void deleteFriendBtn(View view) {
    }

    public void sendBirthdayMessageBtn(View view) {
    }

    public void openMapBtn(View view) {
    }

    public void openMessageBtn(View view) {
    }

    public void openCallBtn(View view) {
    }

    public void openPictureBtn(View view) {
    }
}
