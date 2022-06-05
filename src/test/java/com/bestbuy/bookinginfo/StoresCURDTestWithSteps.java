package com.bestbuy.bookinginfo;

import com.bestbuy.bestbuyinfo.StoresSteps;
import com.bestbuy.testbase.TestBaseStores;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

//@RunWith(SerenityRunner.class)
public class StoresCURDTestWithSteps extends TestBaseStores {

    static String name = "Debenhams" + TestUtils.getRandomValue();
    static String type = "SuperStore" + TestUtils.getRandomValue();
    static String address = TestUtils.getRandomValue() + ", Harrow Road";
    static String address2 = "Harrow";
    static String city = "London";
    static String state = "London";
    static String zip = "123456";
    static Double lat = 65.122179;
    static Double lng = -95.263429;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int storeId;

    @Steps
    StoresSteps storesSteps;

    @Title("This will create new store")
    @Test
    public void test001() {
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours);
        response.log().all().statusCode(201);
    }

    @Title("Verify if the store was created in application")
    @Test
    public void test002() {
        HashMap<String, Object> storeMap = storesSteps.getStoreInfoByname(name);
        Assert.assertThat(storeMap, hasValue(name));
        storeId = (int) storeMap.get("id");
        System.out.println("Created Store ID: " + storeId);
    }

    @Title("Update store information and verify the updated information")
    @Test
    public void test003() {
        name = name + " (Updated)";
        address = address + " (Updated)";
        storesSteps.updateStore(storeId, name, type, address, address2, city, state, zip, lat, lng, hours).log().all().statusCode(200);
        HashMap<String, Object> value = storesSteps.getStoreInfoByname(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title("Update the Store partially and verify the updated information")
    @Test
    public void test004() {
        name = name + "_partially";
        type = type + "_partially";
        storesSteps.updateStorePartially(storeId, name, type, null, null, null, null, null, 0.00, 0.00, null).log().all().statusCode(200);
        HashMap<String, Object> value = storesSteps.getStoreInfoByname(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title("Delete store and verify if the product is deleted")
    @Test
    public void test005() {
        storesSteps.deleteStore(storeId).statusCode(200);
        storesSteps.getStoreById(storeId).statusCode(404);
    }


}