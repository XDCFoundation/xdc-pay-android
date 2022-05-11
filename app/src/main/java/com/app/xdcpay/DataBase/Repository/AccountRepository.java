package com.app.xdcpay.DataBase.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.app.xdcpay.DataBase.DAO.AddNetworkDAO;
import com.app.xdcpay.DataBase.DAO.Add_ImportAccountDao;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;

import java.util.List;

public class AccountRepository {
    private Add_ImportAccountDao add_importAccountDao;
    private List<AccountEntity> accountList;

    public AccountRepository(Application application) {
        NetworkDataBase database = NetworkDataBase.getInstance(application);
        add_importAccountDao = database.getAccountDao();
        accountList = add_importAccountDao.getAccountList();
    }

    // creating a method to insert the data to our table.
    public void insertAccount(AccountEntity model) {
        new InsertNetworkAsyncTask(add_importAccountDao).execute(model);
    }

    // creating a method to update data in table.
    public void update(AccountEntity model) {
        new UpdateNetworkAsyncTask(add_importAccountDao).execute(model);
    }

    // creating a method to delete the data in our table.
    public void delete(AccountEntity model) {
        new DeleteNetworkAsyncTask(add_importAccountDao).execute(model);
    }

    // below is the method to delete all the contact.
    public void deleteAllAccount() {
        new DeleteAllNetworkAsyncTask(add_importAccountDao).execute();
    }

    // below method is to read all the contact.
    public List<AccountEntity> getAllAccount() {
        return accountList;
    }

    private class InsertNetworkAsyncTask extends AsyncTask<AccountEntity, Void, Void> {
        private Add_ImportAccountDao dao;
        public InsertNetworkAsyncTask(Add_ImportAccountDao networkDao) {
            this.dao = networkDao;
        }

        @Override
        protected Void doInBackground(AccountEntity... networkEntities) {
            add_importAccountDao.insertAccount(networkEntities[0]);

            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateNetworkAsyncTask extends AsyncTask<AccountEntity, Void, Void> {
        private Add_ImportAccountDao dao;

        private UpdateNetworkAsyncTask(Add_ImportAccountDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(AccountEntity... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateAccount(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteNetworkAsyncTask extends AsyncTask<AccountEntity, Void, Void> {
        private Add_ImportAccountDao dao;

        private DeleteNetworkAsyncTask(Add_ImportAccountDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(AccountEntity... models) {
            // below line is use to delete our course modal in dao.
            dao.deleteAccount(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class DeleteAllNetworkAsyncTask extends AsyncTask<Void, Void, Void> {
        private Add_ImportAccountDao dao;
        private DeleteAllNetworkAsyncTask(Add_ImportAccountDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method to delete all courses.
//            dao.deleteAllCourses();
            return null;
        }
    }
}
