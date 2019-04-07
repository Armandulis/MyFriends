package com.example.myfriends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddFriend extends AppCompatActivity {

    EditText textName;
    EditText textAddress;
    EditText textPhoneNumber;
    EditText textMail;
    EditText textBithday;
    EditText textWebsite;
    ImageView imgPicture;

    FriendBE newFriend;


    private ISQLiteFriends dataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        dataAccess = DataAccessFactory.getInstance(this);

        textName = findViewById(R.id.editText_name_edit);
        textAddress = findViewById(R.id.editText_address_edit);
        textPhoneNumber = findViewById(R.id.editText_phone_edit);
        textMail = findViewById(R.id.editText_mail_edit);
        textBithday = findViewById(R.id.editText_birthday_edit);
        textWebsite = findViewById(R.id.editText_website_edit);
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
        }else if( birthday.equals("")){
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
}
