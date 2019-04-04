package com.example.myfriends;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


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

        TextView textViewBirthday = itemView.findViewById(R.id.textviewlist_birthday);
        textViewBirthday.setText(selectedFriend.birthday);

        return itemView;
    }
}
