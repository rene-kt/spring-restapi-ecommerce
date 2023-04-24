package com.rene.ecommerce.apitest;

import com.rene.ecommerce.Utils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductResourceTest {
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

    // 1. POST /product: create a product
    @Test
    public void testCreateProduct() {
        String url = baseUrl + "product";
        String name = "Product test 5";
        String json = "{ " +
                "\"name\": \"" + name + "\", " +
                "\"description\": \"" + name + " description\", " +
                "\"price\": 10.0 " +
                "}";
        Response response = given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .body(json)
                .when()
                .post(url)
                .then()
                .statusCode(200)
                .extract()
                .response();
        assertEquals(response.jsonPath().getString("name"), name);
    }

    // 1.1 POST /product: create a product with negative price
    // should not be allowed
    @Test
    public void testCreateProductNegativePrice() {
        String url = baseUrl + "product";
        String json = "{ " +
                "\"name\": \"Product test 3\", " +
                "\"description\": \"Product test 3 description\", " +
                "\"price\": -10.0 " +
                "}";
        Response response = given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .body(json)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
        assertFalse(response.jsonPath().getInt("status") == 200);
    }

    // 1.3 POST /product: create a product without token
    @Test
    public void testCreateProductWithoutToken() {
        String url = baseUrl + "product";
        String json = "{ " +
                "\"name\": \"Product test 1\", " +
                "\"description\": \"Product test 1 description\", " +
                "\"price\": 10.0 " +
                "}";
        Response response = given()
                .contentType("application/json")
                .body(json)
                .when()
                .post(url)
                .then()
                .statusCode(403)
                .extract()
                .response();
    }

    // 1.4 POST /product: create a product with client token
    // should return 403 Forbidden, but returns 500 Internal Server Error
    @Test
    public void testCreateProductWithClientToken() {
        String url = baseUrl + "product";
        String json = "{ " +
                "\"name\": \"Product test 1\", " +
                "\"description\": \"Product test 1 description\", " +
                "\"price\": 10.0 " +
                "}";
        Response response = given()
                .header("Authorization", clientToken)
                .contentType("application/json")
                .body(json)
                .when()
                .post(url)
                .then()
                .statusCode(403)
                .extract()
                .response();
    }

    // 2. PUT /product/:id: update a product
    @Test
    public void testUpdateProduct() {
        String url = baseUrl + "product/5";
        String json = "{ " +
                "\"name\": \"Product test 2\", " +
                "\"description\": \"Product test 2 description\", " +
                "\"price\": 5.0 " +
                "}";
        Response response = given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .body(json)
                .when()
                .put(url)
                .then()
                .statusCode(200)
                .extract()
                .response();
        assertEquals(response.jsonPath().getString("name"), "Product test 2");
    }

    // 3. GET /product/:id: get a product
    @Test
    public void testGetProduct() {
        String url = baseUrl + "product/1";
        Response response = given()
                .header("Authorization", clientToken)
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();
        assertEquals(response.jsonPath().getString("name"), "test1");
    }

    // 3.1 GET /product/:id: get a product with seller token
    @Test
    public void testGetProductWithSellerToken() {
        String url = baseUrl + "product/1";
        Response response = given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();
        assertEquals(response.jsonPath().getString("name"), "test1");
    }

    // 3.2 GET /product/:id: get a product with invalid id
    @Test
    public void testGetProductWithInvalidId() {
        String url = baseUrl + "product/100";
        Response response = given()
                .header("Authorization", clientToken)
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(404)
                .extract()
                .response();
    }

    // 3.3 GET /product/:id: get a product without token
    @Test
    public void testGetProductWithoutToken() {
        String url = baseUrl + "product/1";
        Response response = given()
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(403)
                .extract()
                .response();
    }

    // 4. GET /products: get all products
    @Test
    public void testGetAllProducts() {
        String url = baseUrl + "products";
        Response response = given()
                .header("Authorization", clientToken)
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();
        assertTrue(response.jsonPath().getList("name").size() > 0);
    }

    // 4.1 GET /products: get all products with seller token
    @Test
    public void testGetAllProductsWithSellerToken() {
        String url = baseUrl + "products";
        Response response = given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();
        assertTrue(response.jsonPath().getList("name").size() > 0);
    }

    // 4.2 GET /products: get all products without token
    @Test
    public void testGetAllProductsWithoutToken() {
        String url = baseUrl + "products";
        Response response = given()
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(403)
                .extract()
                .response();
    }

    // 5. DELETE /product/:id: delete a product
    @Test
    public void testDeleteProduct() {
        String url = baseUrl + "product/6";
        given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .when()
                .delete(url)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Response response = given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(404)
                .extract()
                .response();
    }

    // 5.1 DELETE /product/:id: delete a product of another seller
    @Test
    public void testDeleteProductOfAnotherSeller() {
        String url = baseUrl + "product/8";
        given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .when()
                .delete(url)
                .then()
                .statusCode(403)
                .extract()
                .response();
    }

    // 6. GET /ownproducts: get all products of a seller
    @Test
    public void testGetAllProductsOfSeller() {
        String url = baseUrl + "ownproducts";
        Response response = given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();
        assertTrue(response.jsonPath().getList("name").size() >= 0);
    }

    // 6.1 GET /ownproducts: get all products of a client
    // should return 403 forbidden but returns 500 internal server error
    @Test
    public void testGetAllProductsOfClient() {
        String url = baseUrl + "ownproducts";
        Response response = given()
                .header("Authorization", clientToken)
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(403)
                .extract()
                .response();
    }

    // 7. PUT /buy/:id: buy a product as a client
    @Test
    public void testBuyProduct() {
        String id = "2";
        String url = baseUrl + "buy/" + id;
        given()
                .header("Authorization", clientToken)
                .contentType("application/json")
                .when()
                .put(url)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Response response = given()
                .header("Authorization", clientToken)
                .contentType("application/json")
                .when()
                .get(baseUrl + "product/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    // 7.1 PUT /buy/:id: buy a product as a seller
    // should return 403 forbidden but returns 500 internal server error
    @Test
    public void testBuyProductAsSeller() {
        String id = "3";
        String url = baseUrl + "buy/" + id;
        given()
                .header("Authorization", sellerToken)
                .contentType("application/json")
                .when()
                .put(url)
                .then()
                .statusCode(403)
                .extract()
                .response();
    }

    // 7.2 PUT /buy/:id: buy a product that has been sold
    @Test
    public void testBuyProductThatHasBeenSold() {
        String id = "1";
        String url = baseUrl + "buy/" + id;
        given()
                .header("Authorization", clientToken)
                .contentType("application/json")
                .when()
                .put(url)
                .then()
                .statusCode(400)
                .extract()
                .response();
    }

    // 7.3 PUT /buy/:id: buy a product that does not exist
    @Test
    public void testBuyProductThatDoesNotExist() {
        String id = "100";
        String url = baseUrl + "buy/" + id;
        given()
                .header("Authorization", clientToken)
                .contentType("application/json")
                .when()
                .put(url)
                .then()
                .statusCode(404)
                .extract()
                .response();
    }

    // 7.4 PUT /buy/:id: buy a product without token
    @Test
    public void testBuyProductWithoutToken() {
        String id = "3";
        String url = baseUrl + "buy/" + id;
        given()
                .contentType("application/json")
                .when()
                .put(url)
                .then()
                .statusCode(403)
                .extract()
                .response();
    }

}
