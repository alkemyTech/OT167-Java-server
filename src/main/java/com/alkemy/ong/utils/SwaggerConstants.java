package com.alkemy.ong.utils;

public class SwaggerConstants {


    //-------------------{ Categories }------------------
    public static final String MODEL_CATEGORY = "{\"idCategories\" : \"1\", \"name\" : \"categoryOne\", \"description\" : \"category1\", \"image\" : \"imageCat1\",\n" + "\"creationDate\" : \"2022-04-09\",\n" + "\"updateDate\" : \"2022-04-09\"}\n";
    public static final String MODEL_LIST_NAME = "{\"name\" : \"categoryOne\"}\n";
    public static final String MODEL_MESSAGE_LIST = "{\n" + "  \"content\": [\n" + MODEL_LIST_NAME + "  ],\n" + "  \"status_code\": 200\n" + "}";
    public static final String MODEL_MESSAGE_ERROR_EMPTY = "{\n" + "  \"content\": [\n" + " \"There is a empty page.\"" +"  ],\n" + "  \"status_code\": 200\n" +"}";
    public static final String MODEL_ERROR_400 = "{\n" + "  \"message\": \"Required id field is not a number\",\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/categories\"\n" + "}";
    public static final String MODEL_ERROR_404 = "{\n" + "  \"message\": \"The ID doesn't exist.\",\n" + "  \"status_code\": 404,\n" + "  \"path\": \"/categories\"\n" + "}";
    public static final String MODEL_CATEGORY_CREATED = "{\n" + "  \"message\": \"The category was created successfully.\",\n" + "  \"status_code\": 201,\n" + "  \"path\": \"/categories\"\n" + "}";
    public static final String MODEL_DELETE = "{\n" + "  \"message\": \"The category was deleted.\",\n" + "  \"status_code\": 200,\n" + "  \"path\": \"/categories\"\n" + "}";



    //------------------{ News }----------------------------
    public static final String MODEL_NEW = "{\"id\" : \"1\",\n \"name\" : \"final exam\",\n \"content\" : \"the final exam will be in 30 days\",\n \"image\" : \"imageUrl\",\n  \"creationDate\": \"2022-04-10T20:48:18.617Z\",\n" + "  \"updateDate\": \"2022-04-10T20:48:18.618Z\"\n}";
    public static final String MODEL_NEW_CREATED = "{\n" + "  \"message\": \"The new was created successfully.\",\n" + "  \"status_code\": 200,\n" + "  \"path\": \"/news\"\n" + "}";
    public static final String MODEL_NEW_SAVED = "{\n" +
            "    \"id\": 4,\n" +
            "    \"name\": \"nameOfTheNew\",\n" +
            "    \"content\": \"contentOfTheNew\",\n" +
            "    \"image\": \"imageUrl\",\n" +
            "    \"categoryId\": {\n" +
            "        \"idCategories\": 1,\n" +
            "        \"name\": \"nameOfTheCategory\",\n" +
            "        \"description\": \"descOfTheCategory\",\n" +
            "        \"image\": \"imageURL\",\n" +
            "        \"deleted\": false,\n" +
            "        \"creationDate\": \"10-04-2022 00:00:00\",\n" +
            "        \"updateDate\": \"10-04-2022 00:00:00\"\n" +
            "    },\n" +
            "    \"deleted\": false,\n" +
            "    \"creationDate\": \"10-04-2022 21:36:45\",\n" +
            "    \"updateDate\": \"10-04-2022 22:16:43\"\n" +
            "}";

    public static final String MODEL_NEW_PARAM_ERROR = "{\n" + "  \"message\": {\n" + " \"image\": \"Image cannot be null\"" + "  },\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/news\"\n" + "}";
    public static final String MODEL_NEW_FOUND = MODEL_NEW;
    public static final String MODEL_NEW_NOT_FOUND = "{\n" + "  \"message\": \"The new has been not found.\", \n" + "  \"status_code\": 404,\n" + "  \"path\": \"/news/{id}\"\n" + "}";
    public static final String MODEL_NEW_DELETED = "{\n" + "  \"message\": \"News delete ok.\" \n" + "}";
    public static final String MODEL_NEW_LIST_FOUND = "{\n" + "    \"content\": [ \n" + MODEL_NEW + ",\n" + MODEL_NEW + "\n    ],\n    \"status_code\": 202,\n    \"nextPath\": \"there's not page next\",\n    \"prevPath\": \"there's not page prev\" \n}";


    //-----------------{ Testimonial }------------------------
    public static final String MODEL_TESTIMONIAL = "{\"id\" : \"1\", \"name\" : \"Agostina Suarez\", \"image\" : \"imageUrl\", \"content\" : \"testimonial content\" ,\n" + "  \"creationDate\": \"2022-04-09T15:13:18.617Z\",\n" + "  \"updateDate\": \"2022-04-09T15:13:18.618Z\"}\n";
    public static final String MODEL_MESSAGE_PAGE = "{\n" + "  \"content\": [\n" + MODEL_TESTIMONIAL + "  ],\n" + "  \"status_code\": 200,\n" + "  \"nextPath\": \"/testimonials?page=2\",\n" + "  \"prevPath\": \"/testimonials?page=0\"\n" + "}";
    public static final String MODEL_TESTIMONIAL_PARAM_ERROR = "{\n" + "  \"message\": {\n" + " \"image\": \"Image cannot be null\"" + "  },\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/testimonials\"\n" + "}";
    public static final String MODEL_TESTIMONIAL_EMPTY_ERROR = "{\n" + "  \"content\": [\n" + " \"There is a empty page.\"" + "  ],\n" + "  \"status_code\": 200,\n" + "  \"nextPath\": \"there's not page next.\",\n" + "  \"prevPath\": \"there's not page prev.\"\n" + "}";
    public static final String MODEL_TESTIMONIAL_ERROR_400 = "{\n" + "  \"message\": \"The character entered on the path is not a number\",\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/testimonials\"\n" + "}";
    public static final String MODEL_TESTIMONIAL_ERROR_404 = "{\n" + "  \"message\": \"The testimonial is not found.\",\n" + "  \"status_code\": 404,\n" + "  \"path\": \"/testimonials\"\n" + "}";
    public static final String MODEL_TESTIMONIAL_CREATED = "{\n" + "  \"message\": \"The testimonial was created successfully.\",\n" + "  \"status_code\": 201,\n" + "  \"path\": \"/testimonials\"\n" + "}";
    public static final String MODEL_TESTIMONIAL_DELETE = "{\n" + "  \"message\": \"The testimonial was deleted.\",\n" + "  \"status_code\": 200,\n" + "  \"path\": \"/testimonials\"\n" + "}";


    //-------------------{ Member }----------------------------

    public static final String MODEL_MEMBER = "{\"id\" : \"1\", \"name\" : \"Andres Rodriguez\", \"facebookUrl\" : \"FacebookAndres\", \"instagramUrl\" : \"Inta_Andres\", \"linkedinUrl\" : \"Linkedin\", \"image\" : \"imageUrl\", \"description\" : \"description of Andres\" ,\n" + "  \"creationDate\": \"2022-04-09T15:13:18.617Z\",\n" + "  \"updateDate\": \"2022-04-09T15:13:18.618Z\"}\n";
    public static final String MODEL_MEMBER_PAGE = "{\n" + "  \"content\": [\n" + MODEL_MEMBER + "  ],\n" + "  \"status_code\": 200,\n" + "  \"nextPath\": \"/members?page=2\",\n" + "  \"prevPath\": \"/members?page=0\"\n" + "}";
    public static final String MODEL_MEMBER_PARAM_ERROR = "{\n" + "  \"message\": {\n" + " \"image\": \"Image cannot be null\"" + "  },\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/members\"\n" + "}";
    public static final String MODEL_MEMBER_EMPTY_ERROR= "{\n" + "  \"content\": [\n" + " \"There is a empty page.\"" +"  ],\n" + "  \"status_code\": 200,\n" + "  \"nextPath\": \"there's not page next.\",\n" + "  \"prevPath\": \"there's not page prev.\"\n" + "}";
    public static final String MODEL_MEMBER_ERROR_400 = "{\n" + "  \"message\": \"The character entered on the path is not a number\",\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/members\"\n" + "}";
    public static final String MODEL_MEMBER_ERROR_404 = "{\n" + "  \"message\": \"The member is not found.\",\n" + "  \"status_code\": 404,\n" + "  \"path\": \"/members\"\n" + "}";
    public static final String MODEL_MEMBER_CREATED = "{\n" + "  \"message\": \"The member was created successfully.\",\n" + "  \"status_code\": 401,\n" + "  \"path\": \"/members\"\n" + "}";
    public static final String MODEL_MEMBER_DELETE = "{\n" + "  \"message\": \"The member was deleted.\",\n" + "  \"status_code\": 200,\n" + "  \"path\": \"/members\"\n" + "}";

    //-------------------{ user }----------------------------
    public static final String MODEL_ROL_ERROR_404 = "{\n" + "  \"message\": \"The user already has the ROLE_USER role!!.\",\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/auth/updateRolUser/22\"\n" + "}";
    public static final String MODEL_ROL_UPDATE = "{\n" + "  \"message\": \"User update a new role how ROLE_ADMIN.\",\n" + "  \"status_code\": 200,\n" + "  \"path\": \"/auth/updateRolUser/22\"\n" + "}";

}
