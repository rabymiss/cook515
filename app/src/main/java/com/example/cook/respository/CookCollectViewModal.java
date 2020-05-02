package com.example.cook.respository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cook.entity.CollectCenterEntity;

import java.util.List;

public class CookCollectViewModal extends AndroidViewModel {

    private CookCollectRepository cookCollectRepository;

    public CookCollectViewModal(@NonNull Application application) {
        super(application);
        cookCollectRepository = new CookCollectRepository(application);
    }


    public LiveData<List<CollectCenterEntity>> findAllCollect() {

        return cookCollectRepository.findAllCollect();
    }

    public LiveData<List<CollectCenterEntity>> findWithPattern(String pattern) {
        return cookCollectRepository.findCollectPattern(pattern);
    }

    public void insertCollect(CollectCenterEntity... collectCenterEntities) {
        cookCollectRepository.insertCollect(collectCenterEntities);
    }

    public void deleteCollect(CollectCenterEntity... collectCenterEntities) {
        cookCollectRepository.deleteCollect(collectCenterEntities);
    }
}
