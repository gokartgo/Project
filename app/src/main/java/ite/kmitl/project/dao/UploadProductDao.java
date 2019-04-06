package ite.kmitl.project.dao;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

public class UploadProductDao {

    private String name;
    private String image;

    public UploadProductDao(){
    }

    public UploadProductDao(String name, String image) {
        if(name.trim().equals("")) {
            name = "No Name";
        }
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

}
