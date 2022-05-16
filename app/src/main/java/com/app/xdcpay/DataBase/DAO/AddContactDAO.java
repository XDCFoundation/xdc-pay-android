package com.app.xdcpay.DataBase.DAO;

import static com.app.xdcpay.Utils.DatabaseConstants.CONTACT_TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.xdcpay.DataBase.Entity.ContactEntity;

import java.util.List;

@Dao
public interface AddContactDAO {

    @Insert
    void InsertContact(ContactEntity contactEntity);

    @Update
    void UpdateContact(ContactEntity contactEntity);

    @Delete
    void DeleteContact(ContactEntity contactEntity);

    @Query("UPDATE AddContact_table SET contactName= :name, " +
            "contactWalletAddress = :address," +
            " nameFirstLetter= :firstLetter WHERE contactId = :id")
    void updateByID(String name, String firstLetter, String address, int id);

    @Query("DELETE FROM AddContact_table WHERE contactId = :id")
    void DeleteById(int id);

    // below line is to read all the data from our database.
    @Query("SELECT * FROM " + CONTACT_TABLE_NAME)
    List<ContactEntity> getContactList();
}