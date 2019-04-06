package ite.kmitl.project.dao;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ProductItemDao {

    int amount;
    String image;
    int index;
    String name;


    public ProductItemDao(){}
    public ProductItemDao(int amount,int index,String image,String name) {
        this.amount = amount;
        this.image = image;
        this.index = index;
        this.name = name;
    }


    public int getAmount() {
        return amount;
    }

    public String getImage() {
        return image;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    @Exclude
    public Map<String,Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("amount",amount);
        result.put("image",image);
        result.put("index",index);
        result.put("name",name);
        return result;
    }

}
