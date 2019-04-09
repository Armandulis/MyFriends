package com.example.myfriends;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;


public class ListViewFriendsAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<FriendBE> listFriends;
    private static LayoutInflater inflater = null;

    ListViewFriendsAdapter(Activity context, ArrayList<FriendBE> listRolledGames){

        this.context = context;
        this.listFriends = listRolledGames;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listFriends.size();
    }

    @Override
    public Object getItem(int position) {
        return listFriends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listFriends.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.listview_friends_layout, null) : itemView;

        FriendBE selectedFriend = listFriends.get(position);

        ImageView imageViewPicture = itemView.findViewById(R.id.imageviewlist_picture);
        imageViewPicture.setVisibility(View.INVISIBLE);
        if (!selectedFriend.picture.equals("Unknown")){

        imageViewPicture.setImageURI(Uri.fromFile(new File(selectedFriend.picture)));
        imageViewPicture.setRotation(90);

            imageViewPicture.setVisibility(View.VISIBLE);}

        TextView textViewName = itemView.findViewById(R.id.textviewlist_name);
        textViewName.setText(selectedFriend.name);

        TextView textViewPhone = itemView.findViewById(R.id.textviewlist_phone);
        textViewPhone.setText(selectedFriend.phone);

        ImageView birthdayImg = itemView.findViewById(R.id.image_for_birthday);
        birthdayImg.setVisibility(View.INVISIBLE);
        if (!selectedFriend.birthday.equals("Unknown")){

            String partsOfDate[] = selectedFriend.birthday.split("/");
            if(partsOfDate.length >= 2){
                String birthdayMonth = partsOfDate[1];
                String birthdayDay = partsOfDate[2];
                Calendar cal = Calendar.getInstance();
                String currentDay = cal.get(Calendar.DAY_OF_MONTH) + "";
                String currentMonth = cal.get(Calendar.MONTH) + 1 +"";

                if ( birthdayMonth.equals(currentMonth) && birthdayDay.equals(currentDay)){ birthdayImg.setVisibility(View.VISIBLE);} //SHOW the button
            }
        }

        if (!selectedFriend.mail.equals("Unknown")){
        TextView textViewMail = itemView.findViewById(R.id.textviewlist_mail);
            textViewMail.setText(selectedFriend.mail);}
        else{
            TextView textViewMail = itemView.findViewById(R.id.textviewlist_mail);
            textViewMail.setText("");
        }




        return itemView;
    }
}
