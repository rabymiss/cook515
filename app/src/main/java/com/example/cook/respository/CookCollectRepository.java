package com.example.cook.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cook.dao.CollectCenterDao;
import com.example.cook.databases.CookDatabase;
import com.example.cook.entity.CollectCenterEntity;

import java.util.List;

public class CookCollectRepository {

    private LiveData<List<CollectCenterEntity>> allCollect;
    private CollectCenterDao collectCenterDao;

    public CookCollectRepository(Context context) {
        CookDatabase cookDatabase = CookDatabase.getDatabase(context.getApplicationContext());
        collectCenterDao = cookDatabase.getCollectCenterDao();
        allCollect = collectCenterDao.findAllCollect();


    }


    LiveData<List<CollectCenterEntity>> findCollectPattern(String pattern) {
        return collectCenterDao.findCookWithPattern("%" + pattern + "%");
    }

    LiveData<List<CollectCenterEntity>> findAllCollect() {
        return collectCenterDao.findAllCollect();
    }

    public void deleteCollect(CollectCenterEntity... collectCenterEntities) {
        new DeleteAsyncTask(collectCenterDao).execute(collectCenterEntities);

    }

    public void insertCollect(CollectCenterEntity... collectCenterEntities) {
        new InsertAsyncTask(collectCenterDao).execute(collectCenterEntities);

    }

    public static class InsertAsyncTask extends AsyncTask<CollectCenterEntity, Void, Void> {
        private CollectCenterDao collectCenterDao;

        InsertAsyncTask(CollectCenterDao collectCenterDao) {
            this.collectCenterDao = collectCenterDao;
        }

        @Override
        protected Void doInBackground(CollectCenterEntity... collectCenterEntities) {
            collectCenterDao.insert(collectCenterEntities);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<CollectCenterEntity, Void, Void> {
        private CollectCenterDao collectCenterDao;

        DeleteAsyncTask(CollectCenterDao collectCenterDao) {
            this.collectCenterDao = collectCenterDao;
        }

        @Override
        protected Void doInBackground(CollectCenterEntity... collectCenterEntities) {
            collectCenterDao.delete(collectCenterEntities);
            return null;
        }
    }


}
