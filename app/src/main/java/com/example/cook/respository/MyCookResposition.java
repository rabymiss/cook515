package com.example.cook.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cook.dao.CookShowCardDao;
import com.example.cook.dao.MyCookDao;
import com.example.cook.databases.CookDatabase;
import com.example.cook.entity.MyCookEntity;
import com.example.cook.entity.show.ShowCarEntity;

import java.util.List;

public class MyCookResposition {


    private LiveData<List<MyCookEntity>> allCooksLive;
    private MyCookDao myCookDao;

    MyCookResposition(Context context) {

        CookDatabase cookDatabase = CookDatabase.getDatabase(context.getApplicationContext());
        myCookDao = cookDatabase.getMyCookDao();
        allCooksLive = myCookDao.findAllCollect();
    }

    LiveData<List<MyCookEntity>> findCooksWithPattern(String patten) {
        return myCookDao.findCookWithPattern("%" + patten + "%");
    }

    LiveData<List<MyCookEntity>> getAllCooks() {
        return allCooksLive;
    }

    public void deleteAllCooks() {
        new MyCookResposition.DeleteAsyncTask(myCookDao).execute();
    }

    public void updateCook(MyCookEntity... myCookEntities) {
        new MyCookResposition.UpdateAsyncTask(myCookDao).execute(myCookEntities);
    }

    public void deleteCook(MyCookEntity... showCarEntities) {
        new MyCookResposition.DeleteAsyncTask(myCookDao).execute(showCarEntities);
    }

    public void insertCooks(MyCookEntity... showCarEntities) {
        new MyCookResposition.InsertAsyncTask(myCookDao).execute(showCarEntities);
    }

    public static class InsertAsyncTask extends AsyncTask<MyCookEntity, Void, Void> {

        private MyCookDao myCookDao;

        InsertAsyncTask(MyCookDao myCookDao) {
            this.myCookDao = myCookDao;
        }

        @Override
        protected Void doInBackground(MyCookEntity... myCookEntities) {
            myCookDao.insert(myCookEntities);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<MyCookEntity, Void, Void> {

        private MyCookDao myCookDao;

        DeleteAsyncTask(MyCookDao myCookDao) {
            this.myCookDao = myCookDao;
        }

        @Override
        protected Void doInBackground(MyCookEntity... myCookEntities) {
            myCookDao.delete(myCookEntities);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<MyCookEntity, Void, Void> {

        private MyCookDao myCookDao;

        UpdateAsyncTask(MyCookDao myCookDao) {
            this.myCookDao = myCookDao;
        }

        @Override
        protected Void doInBackground(MyCookEntity... myCookEntities) {
            myCookDao.update(myCookEntities);
            return null;
        }
    }

    public static class DeleteAllCooksAsyncTask extends AsyncTask<Void, Void, Void> {

        private MyCookDao myCookDao;

        DeleteAllCooksAsyncTask(MyCookDao myCookDao) {
            this.myCookDao = myCookDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myCookDao.deleteAllCollect();
            return null;
        }
    }


}
