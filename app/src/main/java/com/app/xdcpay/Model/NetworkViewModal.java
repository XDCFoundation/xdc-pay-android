package com.app.xdcpay.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.Repository.NetworkRepository;

import java.util.List;

public class NetworkViewModal extends AndroidViewModel {
    NetworkRepository repository;
    // below line is to create a variable for live data where all the courses are present.
    private List<NetworkEntity> allNetworkList;

    public NetworkViewModal(@NonNull Application application) {
        super(application);
        repository = new NetworkRepository(application);
        allNetworkList = repository.getAllNetworks();
    }

    // below method is use to insert the data to our repository.
    public void insert(NetworkEntity model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(NetworkEntity model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(NetworkEntity model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
//    public void deleteAllCourses() {
//        repository.deleteAllCourses();
//    }

    // below method is to get all the courses in our list.
    public List<NetworkEntity> getAllCourses() {
        return allNetworkList;
    }

}
