package ite.kmitl.project.dao;

import android.util.Log;

public class StaffDao {
    private String keyRef;
    private String key;
    private String name;
    private String surname;
    private String username;

    StaffDao(){

    }

    StaffDao(String key,String name,String surname,String username){
        this.key = key;
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public void print() {
        Log.d("test_print",this.key+" "+this.name+" "+this.surname+" "+this.username);
    }

    public String getKeyRef() {return keyRef;}

    public void setKeyRef(String keyRef){this.keyRef = keyRef;}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
