package com.alkemy.ong.utils;

public class SwaggerConstants {
    public static final String MODEL_CATEGORY = "{\"idCategories\" : \"1\", \"name\" : \"categoryOne\", \"description\" : \"category1\", \"image\" : \"imageCat1\",\n" + "\"creationDate\" : \"2022-04-09\",\n" + "\"updateDate\" : \"2022-04-09\"}\n";
    public static final String MODEL_LIST_NAME = "{\"name\" : \"categoryOne\"}\n";
    public static final String MODEL_MESSAGE_LIST = "{\n" + "  \"content\": [\n" + MODEL_LIST_NAME + "  ],\n" + "  \"status_code\": 200\n" + "}";
    public static final String MODEL_MESSAGE_ERROR_EMPTY = "{\n" + "  \"content\": [\n" + " \"There is a empty page.\"" +"  ],\n" + "  \"status_code\": 200\n" +"}";
    public static final String MODEL_ERROR_400 = "{\n" + "  \"message\": \"Required id field is not a number\",\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/categories\"\n" + "}";
    public static final String MODEL_ERROR_404 = "{\n" + "  \"message\": \"The ID doesn't exist.\",\n" + "  \"status_code\": 404,\n" + "  \"path\": \"/categories\"\n" + "}";
    public static final String MODEL_CATEGORY_CREATED = "{\n" + "  \"message\": \"The category was created successfully.\",\n" + "  \"status_code\": 201,\n" + "  \"path\": \"/categories\"\n" + "}";
    public static final String MODEL_DELETE = "{\n" + "  \"message\": \"The category was deleted.\",\n" + "  \"status_code\": 200,\n" + "  \"path\": \"/categories\"\n" + "}";
}
