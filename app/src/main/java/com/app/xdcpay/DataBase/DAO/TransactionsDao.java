package com.app.xdcpay.DataBase.DAO;

import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.Entity.TransactionsEntity;

import static com.app.xdcpay.Utils.DatabaseConstants.TRANSACTIONS_TABLE_NAME;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static com.app.xdcpay.Utils.DatabaseConstants.NETWORK_TABLE_NAME;

@Dao
public interface TransactionsDao {
    @Insert
    void insertTransaction(TransactionsEntity transactionsEntity);

    @Update
    void updateTransaction(TransactionsEntity TransactionsEntity);

    @Delete
    void deleteTransaction(TransactionsEntity TransactionsEntity);

    // on below line we are making query to
    // delete all courses from our database.

    @Query("DELETE FROM transactions_table WHERE id = :id")
    void deleteById(int id);

    // below line is to read all the data from our database.
    @Query("SELECT * FROM " + TRANSACTIONS_TABLE_NAME)
    List<TransactionsEntity> getTransactionsList();
}
