package com.rene.ecommerce;

import io.restassured.http.ContentType;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Utils {
    public static String baseUrl = "http://localhost:3000/";

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
    public static boolean isInOrder(List<Integer> list, boolean ascending) {
        if (ascending) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) > list.get(i + 1)) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) < list.get(i + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

}
