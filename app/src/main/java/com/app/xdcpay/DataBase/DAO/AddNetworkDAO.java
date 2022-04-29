package com.app.xdcpay.DataBase.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.xdcpay.DataBase.Entity.NetworkEntity;

import java.util.List;

@Dao
public interface AddNetworkDAO {

    @Insert
    void insertNetwork(NetworkEntity networkModel);

    @Update
    void updateNetwork(NetworkEntity networkModel);

    @Delete
    void deleteNetwork(NetworkEntity networkModel);

    // on below line we are making query to
    // delete all courses from our database.

    /*@Query("DELETE FROM course_table")
    void deleteAllCourses();*/

    // below line is to read all the data from our database.
    @Query("SELECT * FROM addNetwork_table ORDER BY network_name ASC")
    LiveData<List<NetworkEntity>> getNetworkList();
}
