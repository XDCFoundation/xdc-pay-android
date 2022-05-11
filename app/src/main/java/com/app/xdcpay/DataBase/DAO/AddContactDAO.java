package com.app.xdcpay.DataBase.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import com.app.xdcpay.DataBase.Entity.ContactEntity;

@Dao
public interface AddContactDAO {

    @Insert
    void InsertContact(ContactEntity contactEntity);
}
