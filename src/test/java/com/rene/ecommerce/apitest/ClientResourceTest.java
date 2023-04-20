package com.rene.ecommerce.apitest;

import com.rene.ecommerce.Utils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientResourceTest {
    public static String baseUrl = "http://localhost:3000/";
    private static String clientEmail = "client1@gmail.com";
    private static String clientPasswd = "client1";
    private static String clientToken = "";

    @BeforeAll
    // login and get token
    public static void login() {
        String response = Utils.login(clientEmail, clientPasswd);
        assertTrue(response.startsWith("Bearer "));
        assertTrue(response.length() > 7);
        clientToken = response;

    }

    // 2. test ClientResource
    // 2.1 GET /client : return client profile
    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
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
    @Order(4)
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
        String response = Utils.login(
                "testCreate1@gmail.com",
                "testCreate1"
        );
        assertTrue(response.startsWith("Bearer "));
    }

    // 2.4.2 create a new client with an existing email
    @Test
    @Order(5)
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
    @Order(6)
    public void testUpdateClient() throws JSONException {
        String url = baseUrl + "update/client";
        String currToken = Utils.login(
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
        String response = Utils.login(
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
    @Order(7)
    public void testDeleteClient() {
        String url = baseUrl + "delete/client";
        String currToken = Utils.login(
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
        Utils.login("testCreate1@gmail.com", "testCreate1", 401);
    }
}
