package com.example.cook.respository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cook.entity.MyCookEntity;
import com.example.cook.entity.show.ShowCarEntity;

import java.util.List;

public class MyCookViewModel extends AndroidViewModel {


    private MyCookResposition myCookResposition;


    public MyCookViewModel(@NonNull Application application) {

        super(application);
        myCookResposition = new MyCookResposition(application);
    }

    public LiveData<List<MyCookEntity>> getAllCooksLive() {
        return myCookResposition.getAllCooks();
    }

    public LiveData<List<MyCookEntity>> findAllCooksLive(String patten) {
        return myCookResposition.findCooksWithPattern(patten);
    }

    public void insertCooks(MyCookEntity... myCookEntities) {
        myCookResposition.insertCooks(myCookEntities);
    }

    public void deleteCooks(MyCookEntity... myCookEntities) {
        myCookResposition.deleteCook(myCookEntities);
    }

    public void updateCooks(MyCookEntity... myCookEntities) {
        myCookResposition.updateCook(myCookEntities);
    }

    public void deleteAllCooks() {
        myCookResposition.deleteAllCooks();
    }

}
