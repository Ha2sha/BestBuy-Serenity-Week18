package com.bestbuy.bestbuyinfo;


import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductsSteps {
    @Step("Creating Product with name : {0}, type: {1}, price: {2}, shipping: {3}, upc: {4},description: {5}, manufacturer: {6},model: {7},url: {8},image: {9}")
    public ValidatableResponse createProduct(String name, String type, Double price, Integer shipping, String upc, String description, String manufacturer, String model, String url, String image) {
        ProductsPojo productsPojo = ProductsPojo.getProductsPojo(name,type,price,shipping,upc,description,manufacturer,model,url,image);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post()
                .then();
    }

    @Step("Getting single Product information with name: {0}")
    public HashMap<String, Object> getProductInfoByName(String name) {
        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);

    }

    @Step("Updating Product information with ProductID: {0}, name : {1}, type: {2}, price: {3}, shipping: {4}, upc: {5},description: {6}, manufacturer: {7},model: {8},url: {9},image: {10}")
    public ValidatableResponse updateProduct(int productId, String name, String type, Double price, Integer shipping, String upc, String description, String manufacturer, String model, String url, String image) {
        ProductsPojo productsPojo = ProductsPojo.getProductsPojo(name,type,price,shipping,upc,description,manufacturer,model,url,image);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("productID", productId)
                .body(productsPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Updating Product partially with ProductID: {0}, name : {1}, type: {2}, price: {3}")
    public ValidatableResponse updateProductPartially(int productId, String name, String type, Double price, Integer shipping, String upc, String description, String manufacturer, String model, String url, String image, Object o) {
        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("productID", productId)
                .body(productsPojo)
                .when()
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Deleting Product information with ProductID: {0}")
    public ValidatableResponse deleteProduct(int productId) {
        return SerenityRest
                .given()
                .pathParam("productID", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Getting Product information with ProductID: {0}")
    public ValidatableResponse getProductById(int productId) {
        return SerenityRest
                .given()
                .pathParam("productID", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }

}
