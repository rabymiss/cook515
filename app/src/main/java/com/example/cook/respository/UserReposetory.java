package com.example.cook.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cook.dao.UserDao;
import com.example.cook.databases.CookDatabase;
import com.example.cook.entity.RegisterEntity;

import java.util.List;

public class UserReposetory {

private LiveData<List<RegisterEntity> >list;
private UserDao userDao;
    UserReposetory(Context context) {

        CookDatabase cookDatabase = CookDatabase.getDatabase(context.getApplicationContext());
        userDao = cookDatabase.getUserDao();
        list = userDao.findAccount();
    }

    public void DeleAll(){

      new  UserReposetory.DeleteAllAsncTask(userDao).execute();
    }
    public void InsertUser( RegisterEntity...registerEntities){

        new UserReposetory.InsertAsyncTask(userDao).execute(registerEntities);
    }
    LiveData<List<RegisterEntity>>allAccout(){return  list;}
    public static class InsertAsyncTask extends AsyncTask<RegisterEntity,Void,Void>{
private UserDao userDao;
        InsertAsyncTask( UserDao userDao){
            this.userDao=userDao;

        }

        @Override
        protected Void doInBackground(RegisterEntity... registerEntities) {
            userDao.insert(registerEntities);
            return null;
        }
    }


public static class DeleteAllAsncTask extends AsyncTask<Void,Void,Void>{
private UserDao userDao;
    DeleteAllAsncTask(UserDao userDao){this.userDao=userDao;}
    @Override
    protected Void doInBackground(Void... voids) {
        userDao.deleteAll();
        return null;
    }
}


}
