package com.example.myfriends;

import android.content.Context;

public class DataAccessFactory {
    public static ISQLiteFriends getInstance(Context c)
    { return new SQLiteFriends(c); }
}
