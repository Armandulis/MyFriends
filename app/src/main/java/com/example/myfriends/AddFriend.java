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

public class AddFriend extends AppCompatActivity {

    EditText textName;
    EditText textAddress;
    EditText textPhoneNumber;
    EditText textMail;
    TextView textBithday;
    EditText textWebsite;
    ImageView imgPicture;

    FriendBE newFriend;


    private ISQLiteFriends dataAccess;

    private DatePickerDialog.OnDateSetListener mDateSetPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        dataAccess = DataAccessFactory.getInstance(this);

        textName = findViewById(R.id.editText_name_add);
        textAddress = findViewById(R.id.editText_address_add);
        textPhoneNumber = findViewById(R.id.editText_phone_add);
        textMail = findViewById(R.id.editText_mail_add);
        textBithday = findViewById(R.id.textview_birthday_add);
        textWebsite = findViewById(R.id.editText_website_add);

        mDateSetPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String dateOfBirth =year +"/"+month+"/"+dayOfMonth;
                textBithday.setText(dateOfBirth);

            }
        };
    }

    public void locationButton(View view){

    }

    public void takePictureButton(View view){

    }

    public void saveFriend(View view) {
        createFriend();
    }

    private void createFriend(){
        String name = textName.getText().toString();
        String address = textAddress.getText().toString();
        String phoneNumber = textPhoneNumber.getText().toString();
        String mail = textMail.getText().toString();
        String birthday = textBithday.getText().toString();
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
        }else if( birthday.equals("Birthday:")){
            textBithday.setError( "Birthday is required!" );
        }else if( website.equals("")){
            website = "Unknown";
        } else if(picture.equals("")){

            Toast.makeText(this, "Take a picture of you Friend!", Toast.LENGTH_SHORT);
        }
        else{
            newFriend = new FriendBE(0,
                    name,
                    address,
                    phoneNumber,
                    mail,
                    birthday,
                    website,
                    picture);

            dataAccess.createFriend(newFriend);
            Intent intent = new Intent();
            setResult(Common.RESULT_CODE, intent);

            finish();
        }


    }

    public void setBirthdayAdd(View view) {
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
