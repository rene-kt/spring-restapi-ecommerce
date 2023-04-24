package com.rene.ecommerce.apitest;

import com.rene.ecommerce.Utils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WishlistResourceTest {
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

    // 1. POST /wishlist/:id : add a product to a client's wishlist
    @Test
    public void testAddToWishlist() {
        int id = 8;
        String url = baseUrl + "wishlist/" + id;
        given().
                header("Authorization", clientToken).
                when().
                post(url).
                then().
                statusCode(200).
                extract().response();

        Response response = given().
                header("Authorization", clientToken).
                when().
                get(baseUrl + "wishlist").
                then().
                statusCode(200).
                extract().response();
        assertTrue(response.jsonPath().getList("id").contains(id));
    }

    // 1.1 POST /wishlist/:id : add a product that has been sold
    @Test
    public void testAddToWishlist2() {
        int id = 1;
        String url = baseUrl + "wishlist/" + id;
        given().
                header("Authorization", clientToken).
                when().
                post(url).
                then().
                statusCode(400).
                extract().response();
    }

    // 1.2 POST /wishlist/:id : add a product that does not exist
    @Test
    public void testAddToWishlist3() {
        int id = 100;
        String url = baseUrl + "wishlist/" + id;
        given().
                header("Authorization", clientToken).
                when().
                post(url).
                then().
                statusCode(404).
                extract().response();
    }

    // 1.3 POST /wishlist/:id : add a product that is already in the wishlist
    @Test
    public void testAddToWishlist4() {
        int id = 8;
        String url = baseUrl + "wishlist/" + id;
        given().
                header("Authorization", clientToken).
                when().
                post(url).
                then().
                statusCode(400).
                extract().response();
    }

    // 2. GET /wishlist : return a client's wishlist
    @Test
    public void testGetWishlist() {
        String url = baseUrl + "wishlist";
        Response response = given().
                header("Authorization", clientToken).
                when().
                get(url).
                then().
                statusCode(200).
                extract().response();
        assertTrue(response.jsonPath().getList("id").contains(8));
    }

    // 2.1 GET /wishlist : return a client's wishlist with seller token
    // should return 403 forbidden, but returns 500 internal server error
    @Test
    public void testGetWishlist2() {
        String url = baseUrl + "wishlist";
        Response response = given().
                header("Authorization", sellerToken).
                when().
                get(url).
                then().
                statusCode(403).
                extract().response();
    }

    // 3. DELETE /wishlist/:id : remove a product from a client's wishlist
    @Test
    public void testRemoveFromWishlist() {
        int id = 8;
        String url = baseUrl + "wishlist/" + id;
        given().
                header("Authorization", clientToken).
                when().
                delete(url).
                then().
                statusCode(204).
                extract().response();

        Response response = given().
                header("Authorization", clientToken).
                when().
                get(baseUrl + "wishlist").
                then().
                statusCode(200).
                extract().response();
        assertFalse(response.jsonPath().getList("id").contains(id));
    }

    // 3.1 DELETE /wishlist/:id : remove a product that is not in the wishlist
    // should return 404 not found, but returns 204 no content
    @Test
    public void testRemoveFromWishlist2() {
        int id = 100;
        String url = baseUrl + "wishlist/" + id;
        given().
                header("Authorization", clientToken).
                when().
                delete(url).
                then().
                statusCode(404).
                extract().response();
    }

}
