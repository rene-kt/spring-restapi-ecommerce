package com.rene.ecommerce;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class APITest {

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
        String response = login(clientEmail, clientPasswd);
        assertTrue(response.startsWith("Bearer "));
        assertTrue(response.length() > 7);
        clientToken = response;

        response = login(sellerEmail, sellerPasswd);
        assertTrue(response.startsWith("Bearer "));
        assertTrue(response.length() > 7);
        sellerToken = response;
    }

    public static String login(String email, String password) {
        return login(email, password, 200);
    }

    public static String login(String email, String password, int statusCode) {
        String url = baseUrl + "login";
        String body = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
        String response = given().
                contentType(ContentType.TEXT).
                body(body).
                when().
                post(url).
                then().
                statusCode(statusCode).
                extract().header("Authorization");
        return response;
    }

    // 1. test AuthResource
    // 1.1 POST /forgot : send new password to email
    @Test
    public void testForgot() {
        String url = baseUrl + "forgot";
        String body = "{}";

    }

    // 1.2 GET /user : return if the user is a client or a seller
    @Test
    public void testUserClient() {
        String url = baseUrl + "user";
        Response response = given().
                header("Authorization", clientToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        String role = response.jsonPath().getString("type");
        assertEquals(role, "Client");
    }

    @Test
    public void testUserSeller() {
        String url = baseUrl + "user";
        Response response = given().
                header("Authorization", sellerToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        String role = response.jsonPath().getString("type");
        assertEquals(role, "Seller");
    }

    // 2. test ClientResource
    // 2.1 GET /client : return client profile
    @Test
    public void testClient() {
        String url = baseUrl + "client";
        Response response = given().
                header("Authorization", clientToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        assertEquals(response.jsonPath().getString("email"), clientEmail);
        assertEquals(response.jsonPath().getString("type"), "Client");
    }

    // 2.2 GET /clients: return all clients
    @Test
    public void testClients() {
        String url = baseUrl + "clients";
        Response response = given().
                header("Authorization", clientToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        assertTrue(response.jsonPath().getList("email").size() > 0);
        assertTrue(response.jsonPath().getList("boughtProducts").size() > 0);
    }

    // 2.3 GET /clients/ranking: return a list of clients who buys the most
    @Test
    public void testClientRanking() {
        String url = baseUrl + "clients/ranking";
        Response response = given().
                header("Authorization", clientToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        List<Integer> sellsOrBuys = response.jsonPath().getList("sellsOrBuys");
        assertTrue(Utils.isInOrder(sellsOrBuys, false));
    }

    // 2.4.1 POST /create/client: create a new client
    @Test
    public void testCreateClient() {
        String url = baseUrl + "create/client";
        String body = "{" +
                "\"name\": \"testCreate1\", " +
                "\"email\": \"testCreate1@gmail.com\", " +
                "\"password\": \"testCreate1\" " +
                "}";
        Response resp = given().
                contentType("application/json").
                body(body).
                when().
                post(url).
                then().
                extract().response();
        String response = login(
                "testCreate1@gmail.com",
                "testCreate1"
        );
        assertTrue(response.startsWith("Bearer "));
    }

    // 2.4.2 create a new client with an existing email
    @Test
    public void testCreateClientWithExistingEmail() {
        String url = baseUrl + "create/client";
        String body = "{" +
                "\"name\": \"testCreate1\", " +
                "\"email\": \"testCreate1@gmail.com\", " +
                "\"password\": \"testCreate1\" " +
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

    // 2.5 PUT /update/client: update client profile
    @Test
    public void testUpdateClient() throws JSONException {
        String url = baseUrl + "update/client";
        String currToken = login(
                "testCreate1@gmail.com",
                "testCreate1"
        );
        String newPassword = "testUpdate";
        JSONObject json = new JSONObject();
        json.put("name", "testCreate1");
        json.put("email", "testCreate1@gmail.com");
        json.put("password", newPassword);
        Response resp = given().
                header("Authorization", currToken).
                contentType("application/json").
                body(json.toString()).
                when().
                put(url).
                then().
                extract().response();
        String response = login(
                "testCreate1@gmail.com",
                newPassword
        );
        assertTrue(response.startsWith("Bearer "));

        // modify back to the original password
        json.put("password", "testCreate1");
        given().
                header("Authorization", currToken).
                contentType("application/json").
                body(json.toString()).
                when().
                put(url).
                then().
                statusCode(200);
    }

    // 2.6 DELETE /delete/client: delete a client
    @Test
    public void testDeleteClient() {
        String url = baseUrl + "delete/client";
        String currToken = login(
                "testCreate1@gmail.com",
                "testCreate1"
        );
        Response resp = given().
                header("Authorization", currToken).
                when().
                delete(url).
                then().
                statusCode(204).
                extract().response();

        // try to log in with the deleted account
        login("testCreate1@gmail.com", "testCreate1", 401);
    }

    // 3. test SellerResource
    // 3.1 GET /seller: return seller profile
    @Test
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
        String response = login(
                "testCreateSeller1@gmail.com",
                "testCreateSeller1"
        );
        assertTrue(response.startsWith("Bearer "));
    }

    // 3.4.2 create a new seller with an existing email
    @Test
    public void testCreateSellerWithExistingEmail() {
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
        assertEquals(resp.statusCode(), 400);
        assertTrue(resp.jsonPath().getString("message").
                contains("This email is already being used"));
    }

    // 3.5 UPDATE /update/seller: update seller profile
    @Test
    public void testUpdateSeller() throws JSONException {
        String url = baseUrl + "update/seller";
        String currToken = login(
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
        String response = login(
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
    public void testDeleteSeller() {
        String url = baseUrl + "delete/seller";
        String currToken = login(
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
        login("testCreateSeller1@gmail.com", "testCreateSeller1", 401);
    }

}
