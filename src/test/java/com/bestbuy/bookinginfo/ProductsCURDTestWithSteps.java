package com.bestbuy.bookinginfo;

import com.bestbuy.bestbuyinfo.ProductsSteps;
import com.bestbuy.testbase.TestBaseProducts;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

//@RunWith(SerenityRunner.class)
public class ProductsCURDTestWithSteps extends TestBaseProducts {

    static String name = "Energizer - MAX Batteries" + TestUtils.getRandomName();
    static String type = "Battery" + TestUtils.getRandomName();
    static Double price = 12.99;
    static Integer shipping = 10;
    static String upc = "012333424000";
    static String description = "Long-lasting energy Battery";
    static String manufacturer = "Energizer";
    static String model = TestUtils.getRandomName();
    static String url = "https://www.bestbuy.com/site/Energizer";
    static String image = "http://img.bystatic.com/BestBuy_US/images/products/4853/48000_sa.jpg";
    static int productId;

    @Steps
    ProductsSteps productsSteps;

    @Title("This will create a new Product")
    @Test
    public void test001() {
        ValidatableResponse response = productsSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
    }

    @Title("Verify that Product was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = productsSteps.getProductInfoByName(name);
        Assert.assertThat(productMap, hasValue(name));
        productId = (int) productMap.get("id");
        System.out.println("Created Product ID: " + productId);
    }

    @Title("Update the Product information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";
        productsSteps.updateProduct(productId, name, type, price, shipping, upc, description, manufacturer, model, url, image).log().all().statusCode(200);
        HashMap<String, Object> value = productsSteps.getProductInfoByName(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title("Update the Product partially and verify the updated information")
    @Test
    public void test004() {
        name = name + "_partial";
        type=type+"_partial";
        price= 15.99;
        productsSteps.updateProductPartially(productId, name, type, price,null,null,null,null,null,null,null,null).log().all().statusCode(200);
        HashMap<String, Object> value = productsSteps.getProductInfoByName(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title("Delete the Product and verify if the Product is deleted!")
    @Test
    public void test005() {
        productsSteps.deleteProduct(productId).statusCode(200);
        productsSteps.getProductById(productId).statusCode(404);
    }


}