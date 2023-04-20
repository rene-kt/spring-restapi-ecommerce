package com.rene.ecommerce.apitest;

import com.rene.ecommerce.Utils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.event.annotation.AfterTestMethod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SellerResourceTest {
    public static String baseUrl = "http://localhost:3000/";
    private static String sellerEmail = "seller1@gmail.com";
    private static String sellerPasswd = "seller1";
    private static String sellerToken = "";

    @BeforeAll
    // login and get token
    public static void login() {
        String response = Utils.login(sellerEmail, sellerPasswd);
        assertTrue(response.startsWith("Bearer "));
        assertTrue(response.length() > 7);
        sellerToken = response;
    }

    // 3. test SellerResource
    // 3.1 GET /seller: return seller profile
    @Test
    @Order(1)
    public void testSeller() {
        String url = baseUrl + "seller";
        Response response = given().
                header("Authorization", sellerToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        assertEquals(response.jsonPath().getString("email"), sellerEmail);
        assertEquals(response.jsonPath().getString("type"), "Seller");
    }

    // 3.2 GET /sellers: return all sellers
    @Test
    @Order(2)
    public void testSellers() {
        String url = baseUrl + "sellers";
        Response response = given().
                header("Authorization", sellerToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        assertTrue(response.jsonPath().getList("email").size() > 0);
        assertTrue(response.jsonPath().getList("numberOfSells").size() > 0);
    }

    // 3.3 GET /sellers/ranking: return a list of sellers who sells the most
    @Test
    @Order(3)
    public void testSellerRanking() {
        String url = baseUrl + "sellers/ranking";
        Response response = given().
                header("Authorization", sellerToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        List<Integer> sellsOrBuys = response.jsonPath().getList("sellsOrBuys");
        assertTrue(Utils.isInOrder(sellsOrBuys, false));
    }

    // 3.4 POST /create/seller: create a new seller
    @Test
    @Order(4)
    public void testCreateSeller() {
        String url = baseUrl + "create/seller";
        String body = "{" +
                "\"name\": \"testCreateSeller1\", " +
                "\"email\": \"testCreateSeller1@gmail.com\", " +
                "\"password\": \"testCreateSeller1\" " +
                "}";
        Response resp = given().
                contentType("application/json").
                body(body).
                when().
                post(url).
                then().
                extract().response();
        String response = Utils.login(
                "testCreateSeller1@gmail.com",
                "testCreateSeller1"
        );
        assertTrue(response.startsWith("Bearer "));
    }

    // 3.4.2 create a new seller with an existing email
    @Test
    @Order(5)
    public void testCreateSellerWithExistingEmail() {
        String url = baseUrl + "create/seller";
        String body = "{" +
                "\"name\": \"testCreateSeller1\", " +
                "\"email\": \"" + sellerEmail + "\", " +
                "\"password\": \"testCreateSeller1\" " +
                "}";
        Response resp = given().
                contentType("application/json").
                body(body).
                when().
                post(url).
                then().
                extract().response();
        assertEquals(resp.statusCode(), 400);
        assertTrue(resp.jsonPath().getString("message").
                contains("This email is already being used"));
    }

    // 3.5 UPDATE /update/seller: update seller profile
    @Test
    @Order(6)
    public void testUpdateSeller() throws JSONException {
        String url = baseUrl + "update/seller";
        String currToken = Utils.login(
                "testCreateSeller1@gmail.com",
                "testCreateSeller1"
        );
        String newPassword = "testUpdate";
        JSONObject json = new JSONObject();
        json.put("name", "testCreateSeller1");
        json.put("email", "testCreateSeller1@gmail.com");
        json.put("password", newPassword);
        Response resp = given().
                header("Authorization", currToken).
                contentType("application/json").
                body(json.toString()).
                when().
                put(url).
                then().
                extract().response();
        String response = Utils.login(
                "testCreateSeller1@gmail.com",
                newPassword
        );
        assertTrue(response.startsWith("Bearer "));

        // modify back to the original password
        json.put("password", "testCreateSeller1");
        given().
                header("Authorization", currToken).
                contentType("application/json").
                body(json.toString()).
                when().
                put(url).
                then().
                statusCode(200);
    }

    // 3.6 DELETE /delete/seller: delete a seller
    @Test
    @Order(7)
    public void testDeleteSeller() {
        String url = baseUrl + "delete/seller";
        String currToken = Utils.login(
                "testCreateSeller1@gmail.com",
                "testCreateSeller1"
        );
        Response resp = given().
                header("Authorization", currToken).
                when().
                delete(url).
                then().
                statusCode(204).
                extract().response();

        // try to log in with the deleted account
        Utils.login("testCreateSeller1@gmail.com", "testCreateSeller1", 401);
    }
}
