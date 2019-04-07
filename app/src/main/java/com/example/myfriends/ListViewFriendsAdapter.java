package com.example.myfriends;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class ListViewFriendsAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<FriendBE> listFriends;
    private static LayoutInflater inflater = null;

    public ListViewFriendsAdapter(Activity context, ArrayList<FriendBE> listRolledGames){

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

        ImageView textViewPicture = itemView.findViewById(R.id.imageviewlist_picture);
        // setImage

        TextView textViewName = itemView.findViewById(R.id.textviewlist_name);
        textViewName.setText(selectedFriend.name);

        TextView textViewPhone = itemView.findViewById(R.id.textviewlist_phone);
        textViewPhone.setText(selectedFriend.phone);

        if (!selectedFriend.birthday.equals("Unknown")){
            ImageView birthdayImg = itemView.findViewById(R.id.image_for_birthday);
            String partsOfDate[] = selectedFriend.birthday.split("/");
            String birthdayMonth = partsOfDate[1];
            String birthdayDay = partsOfDate[2];
            Calendar cal = Calendar.getInstance();
            String currentDay = cal.get(Calendar.DAY_OF_MONTH) + "";
            String currentMonth = cal.get(Calendar.MONTH) + 1 +"";

            if ( birthdayMonth.equals(currentMonth) && birthdayDay.equals(currentDay)) birthdayImg.setVisibility(View.VISIBLE); //SHOW the button
            else birthdayImg.setVisibility(View.INVISIBLE);
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
