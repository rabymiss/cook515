package com.example.cook.respository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cook.entity.RegisterEntity;

import java.util.List;

public class UserViewmodel extends AndroidViewModel {

    private  UserReposetory userReposetory;


    public UserViewmodel(@NonNull Application application) {
        super(application);
        userReposetory= new UserReposetory(application);

    }

    public LiveData<List<RegisterEntity>> list(){

     return    userReposetory.allAccout();
    }
    public  void insert(RegisterEntity...registerEntities){

        userReposetory.InsertUser(registerEntities);
    }
    public void deleteall(){
        userReposetory.DeleAll();

    }
}
