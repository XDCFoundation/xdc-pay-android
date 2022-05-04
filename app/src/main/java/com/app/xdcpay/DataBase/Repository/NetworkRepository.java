package com.app.xdcpay.DataBase.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.app.xdcpay.DataBase.DAO.AddNetworkDAO;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;

import java.util.List;

public class NetworkRepository {
    private AddNetworkDAO networkDao;
    private List<NetworkEntity> allNetworkList;

    public NetworkRepository(Application application) {
        NetworkDataBase database = NetworkDataBase.getInstance(application);
        networkDao = database.getNetworkDao();
        allNetworkList = networkDao.getNetworkList();
    }

    // creating a method to insert the data to our database.
    public void insert(NetworkEntity model) {
        new InsertNetworkAsyncTask(networkDao).execute(model);
    }

    // creating a method to update data in database.
    public void update(NetworkEntity model) {
        new UpdateNetworkAsyncTask(networkDao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(NetworkEntity model) {
        new DeleteNetworkAsyncTask(networkDao).execute(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllNetworks() {
        new DeleteAllNetworkAsyncTask(networkDao).execute();
    }

    // below method is to read all the Network.
    public List<NetworkEntity> getAllNetworks() {
        return allNetworkList;
    }

    private class InsertNetworkAsyncTask extends AsyncTask<NetworkEntity, Void, Void> {
        private AddNetworkDAO dao;
        public InsertNetworkAsyncTask(AddNetworkDAO networkDao) {
            this.dao = networkDao;
        }

        @Override
        protected Void doInBackground(NetworkEntity... networkEntities) {
            networkDao.insertNetwork(networkEntities[0]);

            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateNetworkAsyncTask extends AsyncTask<NetworkEntity, Void, Void> {
        private AddNetworkDAO dao;

        private UpdateNetworkAsyncTask(AddNetworkDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(NetworkEntity... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateNetwork(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteNetworkAsyncTask extends AsyncTask<NetworkEntity, Void, Void> {
        private AddNetworkDAO dao;

        private DeleteNetworkAsyncTask(AddNetworkDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(NetworkEntity... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.deleteNetwork(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class DeleteAllNetworkAsyncTask extends AsyncTask<Void, Void, Void> {
        private AddNetworkDAO dao;
        private DeleteAllNetworkAsyncTask(AddNetworkDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
//            dao.deleteAllCourses();
            return null;
        }
    }
}