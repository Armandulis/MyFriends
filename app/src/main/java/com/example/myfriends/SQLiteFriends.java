package com.example.myfriends;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public final class SQLiteFriends implements ISQLiteFriends {

    private static final String DATABASE_NAME = "sqlite.mDatabase";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Friends";

    private SQLiteDatabase database;
     private SQLiteStatement insertStmt;

    SQLiteFriends(Context context){
        OpenHelper openHelper = new OpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    @Override
    public long createFriend(FriendBE newFriend) {
        String INSERT = "insert into " + TABLE_NAME
                + "(name, address, phoneNumber, mail, birthday, website, picture) values (?, ?, ?, ?, ?, ? ,?)";

        insertStmt = database.compileStatement(INSERT);

        insertStmt.bindString(1, newFriend.name);
        insertStmt.bindString(2, newFriend.address);
        insertStmt.bindString(3, newFriend.phone);
        insertStmt.bindString(4, newFriend.mail);
        insertStmt.bindString(5, newFriend.birthday);
        insertStmt.bindString(6, newFriend.website);
        insertStmt.bindString(7, newFriend.picture);

        return insertStmt.executeInsert();
    }

    @Override
    public void updateFriend(FriendBE updateFriend) {

        ContentValues cv = new ContentValues();
        cv.put("name", updateFriend.name); //These Fields should be your String values of actual column names
        cv.put("address",updateFriend.address);
        cv.put("phoneNumber",updateFriend.phone);
        cv.put("mail",updateFriend.mail);
        cv.put("birthday",updateFriend.birthday);
        cv.put("website",updateFriend.website);
        cv.put("picture",updateFriend.picture);
        Log.d("Update Friend inside:", updateFriend.id+"");


        this.database.update(TABLE_NAME, cv, "id=" + updateFriend.id, null);
        /*
        String UPDATE = "UPDATE " + TABLE_NAME + " SET " +
                "name = ?, address = ?, phoneNumber = ?, mail = ?, birthday = ?, website = ?, picture = ? WHERE id = ?";
        insertStmt = database.compileStatement(UPDATE);

        insertStmt.bindString(1, updateFriend.name);
        insertStmt.bindString(2, updateFriend.address);
        insertStmt.bindString(3, updateFriend.phone);
        insertStmt.bindString(4, updateFriend.mail);
        insertStmt.bindString(5, updateFriend.birthday);
        insertStmt.bindString(6, updateFriend.website);
        insertStmt.bindString(7, updateFriend.picture);
        insertStmt.bindLong(8, updateFriend.id);

        insertStmt.executeUpdateDelete(); */
    }


    @Override
    public ArrayList<FriendBE> getFriends() {
        ArrayList<FriendBE> friendsList = new ArrayList<>();
        String[] TABLE_COLUMN_NAMES=  { "id", "name", "address", "phoneNumber", "mail","birthday", "website", "picture" };

        Cursor cursor = database.query(TABLE_NAME, TABLE_COLUMN_NAMES,
                null, null, null, null, "name");


            while(cursor.moveToNext()){
                friendsList.add(new FriendBE(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));
            }
          cursor.close();


        return friendsList;
    }


    @Override
    public void deleteFriend(long friendsID) {
        this.database.delete(TABLE_NAME, "id = ?", new String[]{""+friendsID});
    }


    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.d("Database Operations", "Database created");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY, name TEXT, address TEXT, phoneNumber TEXT, mail TEXT, website TEXT, birthday TEXT, picture TEXT )");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
