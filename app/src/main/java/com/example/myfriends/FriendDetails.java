package com.example.myfriends;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapsInitializer;

import org.w3c.dom.Text;

import java.io.File;
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
        if(!friend.mail.equals("Unknown")) {
            SpannableString spannableStringEmail = new SpannableString(friend.mail);
            ClickableSpan clickEmail = new ClickableSpan() {
                @Override
                public void onClick(@Nullable View widget) {
                    //TODO
                    //SEND TO EMAIL
                }
            };
            spannableStringEmail.setSpan(clickEmail, 0, friend.mail.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txMail.setText(spannableStringEmail);
            txMail.setMovementMethod(LinkMovementMethod.getInstance());
        }

        TextView txBirthday = (TextView) findViewById(R.id.textview_birthday);
        txBirthday.setText(friend.birthday);

        TextView txWebsite = (TextView) findViewById(R.id.textview_website);
        txWebsite.setText(friend.website);

        if(!friend.website.equals("Unknown")){
            SpannableString spannableStringWebsite = new SpannableString(friend.website);
            ClickableSpan clickWebsite = new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    String url = friend.website;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            };
            spannableStringWebsite.setSpan(clickWebsite, 0, friend.website.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
          txWebsite.setText(spannableStringWebsite);
          txWebsite.setMovementMethod(LinkMovementMethod.getInstance());


        }


        if (!friend.picture.equals( "Unknown")){
        ImageView imagePicture = (ImageView) findViewById(R.id.imageview_picture);
            imagePicture.setImageURI(Uri.fromFile(new File(friend.picture)));
            imagePicture.setRotation(90);}

        if (!friend.birthday.equals("Unknown")){
            FloatingActionButton birthdayButton = (FloatingActionButton) findViewById(R.id.button_birthday);
            String partsOfDate[] = friend.birthday.split("/");
            if(partsOfDate.length >= 2) {
                String birthdayMonth = partsOfDate[1];
                String birthdayDay = partsOfDate[2];
                Calendar cal = Calendar.getInstance();
                String currentDay = cal.get(Calendar.DAY_OF_MONTH) + "";
                String currentMonth = cal.get(Calendar.MONTH) + 1 + "";

                if (birthdayMonth.equals(currentMonth) && birthdayDay.equals(currentDay))
                    birthdayButton.show(); //SHOW the button
                else birthdayButton.hide();
            }
        }



    }
    public void openEmailBtn(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] receivers = { friend.mail };
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
        startActivity(emailIntent);
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
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + friend.phone));
        sendIntent.putExtra("sms_body", "Hi there! Happy Birthday! :)");
        startActivity(sendIntent);
    }

    public void openMapBtn(View view) {
        Intent mapIntent = new Intent(FriendDetails.this, MapActivity.class);
        mapIntent.putExtra("friend", friend);
        mapIntent.putExtra("hasSearch", false);
        startActivity(mapIntent);
    }

    public void openMessageBtn(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + friend.phone));
        startActivity(sendIntent);
    }

    public void openCallBtn(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + friend.phone));
        startActivity(intent);
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
