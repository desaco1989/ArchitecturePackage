package com.desaco.architecturepackage.jar_utils.gson;

/**
 * Created by desaco on 2018/11/5.
 * 描述书的属性
 */

public class BookBean {
    private String bookName;//书的名字
    private String bookPrice;//书的价格
    private String bookCode;//书的ISBN编号

    //带参构造函数
    public BookBean(String bookName, String bookPrice, String bookCode) {
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    @Override
    public String toString() {
        return "这是一个描述书的类，一共有3个属性（color,longs,name,price）" + "\n" +
                "BookBean[bookName:'" + bookName + "',bookPrice:'" + bookPrice + "',bookCode:'" + bookCode + "']";
    }

}
