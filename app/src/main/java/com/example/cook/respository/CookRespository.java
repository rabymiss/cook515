package com.example.cook.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cook.dao.CookShowCardDao;
import com.example.cook.databases.CookDatabase;
import com.example.cook.entity.show.ShowCarEntity;

import java.util.List;

public class CookRespository {
    private LiveData<List<ShowCarEntity>> allCooksLive;
    private CookShowCardDao cookShowCardDao;

    CookRespository(Context context) {

        CookDatabase cookDatabase = CookDatabase.getDatabase(context.getApplicationContext());
        cookShowCardDao = cookDatabase.getShowCardDao();
        allCooksLive = cookShowCardDao.getAllCooksLive();
    }
LiveData<List<ShowCarEntity>>findonercookpattern(String pattern){
        return cookShowCardDao.findCookusernamePattern(pattern);
}
    LiveData<List<ShowCarEntity>> findCooksWithPattern(String patten) {
        return cookShowCardDao.findCookWithPattern("%" + patten + "%");
    }
 LiveData<List<ShowCarEntity>>type(String pattern){
        return cookShowCardDao.findzaoWithPattern(pattern);
 }
    LiveData<List<ShowCarEntity>> getAllCooks() {
        return allCooksLive;
    }

    public void deleteAllCooks() {
        new DeleteAsyncTask(cookShowCardDao).execute();
    }

    public void updateCook(ShowCarEntity... showCarEntities) {
        new UpdateAsyncTask(cookShowCardDao).execute(showCarEntities);
    }

    public void deleteCook(ShowCarEntity... showCarEntities) {
        new DeleteAsyncTask(cookShowCardDao).execute(showCarEntities);
    }

    public void insertCooks(ShowCarEntity... showCarEntities) {
        new InsertAsyncTask(cookShowCardDao).execute(showCarEntities);
    }

    public static class InsertAsyncTask extends AsyncTask<ShowCarEntity, Void, Void> {

        private CookShowCardDao cookShowCardDao;

        InsertAsyncTask(CookShowCardDao cookShowCardDao) {
            this.cookShowCardDao = cookShowCardDao;
        }

        @Override
        protected Void doInBackground(ShowCarEntity... showCarEntities) {
            cookShowCardDao.insertCook(showCarEntities);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<ShowCarEntity, Void, Void> {

        private CookShowCardDao cookShowCardDao;

        DeleteAsyncTask(CookShowCardDao cookShowCardDao) {
            this.cookShowCardDao = cookShowCardDao;
        }

        @Override
        protected Void doInBackground(ShowCarEntity... showCarEntities) {
            cookShowCardDao.deleteCook(showCarEntities);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<ShowCarEntity, Void, Void> {

        private CookShowCardDao cookShowCardDao;

        UpdateAsyncTask(CookShowCardDao cookShowCardDao) {
            this.cookShowCardDao = cookShowCardDao;
        }

        @Override
        protected Void doInBackground(ShowCarEntity... showCarEntities) {
            cookShowCardDao.updateCook(showCarEntities);
            return null;
        }
    }

    public static class DeleteAllCooksAsyncTask extends AsyncTask<Void, Void, Void> {

        private CookShowCardDao cookShowCardDao;

        DeleteAllCooksAsyncTask(CookShowCardDao cookShowCardDao) {
            this.cookShowCardDao = cookShowCardDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cookShowCardDao.deleteAllCooks();
            return null;
        }
    }


}
