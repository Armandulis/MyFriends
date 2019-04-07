package com.example.myfriends;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditFriend extends AppCompatActivity {

    EditText textName;
    EditText textAddress;
    EditText textPhoneNumber;
    EditText textMail;
    TextView textBirthday;
    EditText textWebsite;
    ImageView imgPicture;

    FriendBE friend;

    private DatePickerDialog.OnDateSetListener mDateSetPicker;

    private ISQLiteFriends dataAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);
        Intent intent = getIntent();
        friend = (FriendBE) intent.getSerializableExtra("friend");
        setUpInputs();

        dataAccess = DataAccessFactory.getInstance(this);

        mDateSetPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String dateOfBirth =year +"/"+month+"/"+dayOfMonth;
                textBirthday.setText(dateOfBirth);

            }
        };
    }

    private void setUpInputs(){
        textName =  findViewById(R.id.editText_name_add);
        textName.setText(friend.name);
        textAddress =  findViewById(R.id.editText_address_add);
        if (!friend.address.equals("Unknown")) textAddress.setText(friend.address);
        textPhoneNumber =  findViewById(R.id.editText_phone_add);
        textPhoneNumber.setText(friend.phone);
        textMail =  findViewById(R.id.editText_mail_add);
        if (!friend.mail.equals("Unknown")) textMail.setText(friend.mail);
        textBirthday =  findViewById(R.id.editText_birthday_edit);
        if (!friend.birthday.equals("Unknown")) textBirthday.setText(friend.birthday);
        textWebsite = findViewById(R.id.editText_website_add);
        if (!friend.birthday.equals("Unknown")) textWebsite.setText(friend.website);
        //ImageView imagePicture = (ImageView) findViewById(R.id.imageview_picture);
        //imagePicture.setImage();
    }

    public void takePictureButton(View view) {
    }

    public void locationButton(View view) {

    }

    public void updateFriendButton(View view) {
        String name = textName.getText().toString();
        String address = textAddress.getText().toString();
        String phoneNumber = textPhoneNumber.getText().toString();
        String mail = textMail.getText().toString();
        String birthday = textBirthday.getText().toString();
        String website = textWebsite.getText().toString();
        String picture = "Link to picture";

        if( name.equals("")){
            textName.setError( "Name is required!" );
        }else if( address.equals("")){
            textAddress.setError( "Address is required!" );
        }else if(phoneNumber.equals("")){
            textPhoneNumber.setError( "Phone number is required!" );
        }else if(mail.equals("")){
            textMail.setError( "Mail is required!" );
        }else if( birthday.equals("")){
            textBirthday.setError( "Birthday is required!" );
        }else if( website.equals("")){
            website = "Unknown";
        } else if(picture.equals("")){

            Toast.makeText(this, "Take a picture of your Friend!", Toast.LENGTH_SHORT);
        }
        else{

            friend.name = name;
            friend.address = address;
            friend.phone = phoneNumber;
            friend.mail = mail;
            friend.birthday = birthday;
            friend.website = website;
            friend.picture = picture;


            dataAccess.updateFriend(friend);
            Intent intent = new Intent();
            intent.putExtra("friend", friend);
            setResult(Common.RESULT_CODE, intent);

            finish();
        }
    }

    public void setBirthday(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                mDateSetPicker,
                year, month, day);
        dialog.show();
    }


}
