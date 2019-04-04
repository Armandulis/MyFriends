package com.example.myfriends;

import java.io.Serializable;
import java.util.Date;

public class FriendBE implements Serializable {

    int id;
    String name;
    String address;
    String phone;
    String mail;
    String birthday;
    String website;
    String picture;

    public FriendBE(int id,
                    String name,
                    String address,
                    String phone,
                    String mail,
                    String birthday,
                    String website,
                    String picture)

    {   this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
        this.birthday = birthday;
        this.website = website;
        this.picture = picture;
    }
}
