package com.rene.ecommerce.apitest;

import com.rene.ecommerce.Utils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthResourceTest {
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
}
