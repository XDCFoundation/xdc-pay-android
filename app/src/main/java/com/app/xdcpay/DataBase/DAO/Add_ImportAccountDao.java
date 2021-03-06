package com.app.xdcpay.DataBase.DAO;

import static com.app.xdcpay.Utils.DatabaseConstants.ACCOUNT_FIELD_2;
import static com.app.xdcpay.Utils.DatabaseConstants.ACCOUNT_TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.xdcpay.DataBase.Entity.AccountEntity;

import java.util.List;

@Dao
public interface Add_ImportAccountDao {

    @Insert
    void insertAccount(AccountEntity networkModel);

    @Update
    void updateAccount(AccountEntity networkModel);

    @Delete
    void deleteAccount(AccountEntity networkModel);

    // on below line we are making query to delete data by id.
    @Query("DELETE FROM importAccount_table WHERE id = :id")
    void deleteById(int id);

    // below line is to read all the data from our database.
    @Query("SELECT * FROM " + ACCOUNT_TABLE_NAME)
    List<AccountEntity> getAccountList();
}
