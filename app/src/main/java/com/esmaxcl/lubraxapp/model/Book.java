package com.esmaxcl.lubraxapp.model;
import com.orm.SugarRecord;

public class Book extends SugarRecord<Book> {
    String key;
    String product;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Book(){
    }

    public Book(String key, String product){
        this.key = key;
        this.product = product;
    }
}