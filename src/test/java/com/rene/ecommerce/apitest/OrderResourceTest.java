package com.rene.ecommerce.apitest;

import com.rene.ecommerce.Utils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderResourceTest {
    public static String baseUrl = "http://localhost:3000/";
    private static String clientEmail = "client1@gmail.com";
    private static String clientPasswd = "client1";
    private static String clientToken = "";
    private static String sellerEmail = "seller1@gmail.com";
    private static String sellerPasswd = "seller1";
    private static String sellerToken = "";

    @BeforeAll
    // login and get token
    public static void login() {
        String response = Utils.login(clientEmail, clientPasswd);
        assertTrue(response.startsWith("Bearer "));
        assertTrue(response.length() > 7);
        clientToken = response;

        response = Utils.login(sellerEmail, sellerPasswd);
        assertTrue(response.startsWith("Bearer "));
        assertTrue(response.length() > 7);
        sellerToken = response;
    }

    // 1. GET /client/order/:id : return a client order by id
    @Test
    public void testGetClientOrder() {
        String url = baseUrl + "client/order/1";
        Response response = given().
                header("Authorization", clientToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        Map order = response.jsonPath().getJsonObject("productOrder");
        assertEquals(order.get("id"), 1);
        assertEquals(order.get("hasBeenSold"), "Sold");
    }

    // 1.1 GET /client/order/:id : return a client order by id with seller token
    @Test
    public void testGetClientOrderWithSellerToken() {
        String url = baseUrl + "client/order/1";
        Response response = given().
                header("Authorization", sellerToken).
                when().
                get(url).
                then().
                statusCode(403).
                extract().response();
    }

    // 1.2 GET /client/order/:id : return a client order by id without token
    @Test
    public void testGetClientOrderWithoutToken() {
        String url = baseUrl + "client/order/1";
        Response response = given().
                when().
                get(url).
                then().
                statusCode(403).
                extract().response();
    }

    // 1.3 GET /client/order/:id : return a client order with invalid id
    // should return 404 not found, but returns 500 server error
    @Test
    public void testGetClientOrderWithInvalidId() {
        String url = baseUrl + "client/order/100";
        Response response = given().
                header("Authorization", clientToken).
                when().
                get(url).
                then().
                statusCode(404).
                extract().response();
    }

    // 2. GET /client/orders : return all client orders
    @Test
    public void testGetClientOrders() {
        String url = baseUrl + "client/orders";
        Response response = given().
                header("Authorization", clientToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        assertTrue(response.jsonPath().getList("productOrder").size() > 0);
    }

    // 3. GET /seller/order/:id : return a seller order by id
    @Test
    public void testGetSellerOrder() {
        String url = baseUrl + "seller/order/1";
        Response response = given().
                header("Authorization", sellerToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        Map order = response.jsonPath().getJsonObject("productOrder");
        assertEquals(order.get("id"), 1);
        assertEquals(order.get("hasBeenSold"), "Sold");
    }

    // 3.1 GET /seller/order/:id : return a seller order by id with client token
    // should return 403 forbidden, but returns 500 server error
    @Test
    public void testGetSellerOrderWithClientToken() {
        String url = baseUrl + "seller/order/1";
        Response response = given().
                header("Authorization", clientToken).
                when().
                get(url).
                then().
                statusCode(403).
                extract().response();
    }

    // 3.2 GET /seller/order/:id : return a seller order by id without token
    @Test
    public void testGetSellerOrderWithoutToken() {
        String url = baseUrl + "seller/order/1";
        Response response = given().
                when().
                get(url).
                then().
                statusCode(403).
                extract().response();
    }

    // 3.3 GET /seller/order/:id : return a seller order with invalid id
    // should return 404 not found, but returns 500 server error
    @Test
    public void testGetSellerOrderWithInvalidId() {
        String url = baseUrl + "seller/order/100";
        Response response = given().
                header("Authorization", sellerToken).
                when().
                get(url).
                then().
                statusCode(404).
                extract().response();
    }

    // 4. GET /seller/orders : return all seller orders
    @Test
    public void testGetSellerOrders() {
        String url = baseUrl + "seller/orders";
        Response response = given().
                header("Authorization", sellerToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        assertTrue(response.jsonPath().getList("productOrder").size() > 0);
    }

}
