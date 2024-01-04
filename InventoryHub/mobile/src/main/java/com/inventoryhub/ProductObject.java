package com.inventoryhub;

public class ProductObject {
    public String productName, productPrice, productDescription,  productQuanitty;
    public String key=null;

    public ProductObject(String productName, String productPrice, String productDescription, String productQuanitty) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productQuanitty = productQuanitty;
    }
}
