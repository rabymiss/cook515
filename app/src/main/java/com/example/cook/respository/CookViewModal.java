package com.example.cook.respository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cook.entity.show.ShowCarEntity;

import java.util.List;

public class CookViewModal extends AndroidViewModel {

    private CookRespository cookRespository;

    public CookViewModal(@NonNull Application application) {
        super(application);
        cookRespository = new CookRespository(application);
    }


public LiveData<List<ShowCarEntity>> getUsercook(String pattern){
        return cookRespository.findonercookpattern(pattern);
}
public LiveData<List<ShowCarEntity>> type(String pattern){
        return cookRespository.type(pattern);
}
    public LiveData<List<ShowCarEntity>> getAllCooksLive() {
        return cookRespository.getAllCooks();
    }

    public LiveData<List<ShowCarEntity>> findAllCooksLive(String patten) {
        return cookRespository.findCooksWithPattern(patten);
    }

    public void insertCooks(ShowCarEntity... showCarEntities) {
        cookRespository.insertCooks(showCarEntities);
    }

    public void deleteCooks(ShowCarEntity... showCarEntities) {
        cookRespository.deleteCook(showCarEntities);
    }

    public void updateCooks(ShowCarEntity... showCarEntities) {
        cookRespository.updateCook(showCarEntities);
    }

    public void deleteAllCooks() {
        cookRespository.deleteAllCooks();
    }

}

