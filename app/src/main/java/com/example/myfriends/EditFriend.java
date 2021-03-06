package com.example.myfriends;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditFriend extends AppCompatActivity {

    EditText textName;
    TextView textAddress;
    EditText textPhoneNumber;
    EditText textMail;
    TextView textBirthday;
    EditText textWebsite;
    ImageView imgPicture;

    FriendBE friend;

    String picsPath = null;

    File mFile;

    private DatePickerDialog.OnDateSetListener mDateSetPicker;

    private ISQLiteFriends dataAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = getIntent();
        friend = (FriendBE) intent.getSerializableExtra("friend");
        picsPath = friend.picture;
        setUpInputs();

        dataAccess = DataAccessFactory.getInstance(this);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},1);


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

        imgPicture = findViewById(R.id.imageView_picture_edit);
        if (!friend.birthday.equals("Unknown")){

            imgPicture.setImageURI(Uri.fromFile(new File(friend.picture)));
            imgPicture.setRotation(90);
        }

       // imagePicture.setImage();
    }

    public void locationButton(View view) {
        Intent mapIntent = new Intent(this, MapActivity.class);
        mapIntent.putExtra("hasSearch", true);
        startActivityForResult(mapIntent, Common.GET_MAP_ACTIVITY);
    }


    public void updateFriendButton(View view) {
        String name = textName.getText().toString();
        String address = textAddress.getText().toString();
        String phoneNumber = textPhoneNumber.getText().toString();
        String mail = textMail.getText().toString();
        String birthday = textBirthday.getText().toString();
        String website = textWebsite.getText().toString();
        String picture = picsPath;

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
        } else if(picsPath == null){

            Toast.makeText(this, "Take a picture of you Friend!", Toast.LENGTH_SHORT).show();
        }
        else{

            if (!website.contains("http://") && !website.contains("Unknown")) {
                website = "http://" + website;
            }

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



    public void takePictureButton(View view){

        mFile = getOutputMediaFile(); // create a file to save the image
        if (mFile == null)
        {
            Toast.makeText(this, "Could not create file...", Toast.LENGTH_LONG).show();
            return;
        }
        // create Intent to take a picture
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));

        Log.d(Common.LOGTAG, "file uri = " + Uri.fromFile(mFile).toString());

        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.d(Common.LOGTAG, "camera app will be started");
            startActivityForResult(intent, Common.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
        else
            Log.d(Common.LOGTAG, "camera app could NOT be started");

    }


    /** Create a File for saving an image */
    private File getOutputMediaFile(){


        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyFriends");

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Container of pics","failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String postfix = "jpg";
        String prefix = "IMG";

        picsPath = mediaStorageDir.getPath() +
                File.separator + prefix +
                "_"+ timeStamp + "." + postfix;

        return new File(picsPath);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Common.GET_MAP_ACTIVITY && data !=null){
            friend.address =  data.getStringExtra("address");
        }
        if (requestCode == Common.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showPictureTaken(mFile);

            } else
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Canceled...", Toast.LENGTH_LONG).show();
                picsPath = null;
                return;

            } else{
                picsPath = null;
                Toast.makeText(this, "Picture NOT taken - unknown error...", Toast.LENGTH_LONG).show();
            }

        }
    }
    private void showPictureTaken(File f) {

        imgPicture.setImageURI(Uri.fromFile(f));
        imgPicture.setImageURI(Uri.fromFile(f));
        imgPicture.setBackgroundColor(Color.TRANSPARENT);
        imgPicture.setRotation(90);
    }

}
