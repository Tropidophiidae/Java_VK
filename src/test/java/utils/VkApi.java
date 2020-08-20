package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasKey;

public class VkApi {
    private final String version;

    public VkApi() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root =mapper.readTree(new File("src\\test\\resources\\apiConfig.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        version = root.path("apiVersion").asText();
        RestAssured.baseURI = root.path("baseURI").asText();
    }

    public String getPhotoAttachment(String token, String userId, String imagePath) {
        String uploadServer = photosGetWallUploadServer(token, userId).path("response.upload_url");
        Response uploadPhotoOnServer = uploadOnServer(uploadServer, imagePath);
        Response saveWallPhoto = photosSaveWallPhoto(token, userId, uploadPhotoOnServer);
        return "photo" + saveWallPhoto.path("response[0].owner_id").toString() + "_" + saveWallPhoto.path("response[0].id").toString();
    }

    public String getDocAttachment(String token, String userId, String docPath) {
        String uploadServer = docsGetWallUploadServer(token, userId);
        Response uploadPhotoOnServer = uploadOnServer(uploadServer, docPath);
        Response saveDocPhoto = docsSave(token, userId, uploadPhotoOnServer);
        return "doc" + saveDocPhoto.path("response.doc.owner_id").toString() + "_" + saveDocPhoto.path("response.doc.id").toString();
    }

    public Response wallPost(String userId, String message, String token, String... attachment) {
        Response response = given().
                param("owner_id", userId).
                param("message", message).
                param("access_token", token).
                param("attachment", attachment).
                param("v", version).
                get("wall.post");
        response.then().
                body("$", not(hasKey("error")));
        return response.
                then().
                extract().response();
    }

    public void wallEdit(String userId, String postId, String message, String token, String... attachments) {
        Response response = given().
                param("owner_id", userId).
                param("post_id", postId).
                param("message", message).
                param("attachments", attachments).
                param("access_token", token).
                param("v", version).
                get("wall.edit");
        response.then().
                body("$", not(hasKey("error")));
    }

    public Response createComment(String ownerId, String postId, String message, String token) {
        Response response = given().
                param("owner_id", ownerId).
                param("post_id", postId).
                param("message", message).
                param("access_token", token).
                param("v", version).
                get("wall.createComment");
        response.then().
                body("$", not(hasKey("error")));
        return response.
                then().
                extract().response();
    }

    public int isLiked(String userId, String type, String itemId, String ownerId, String token) {
        Response response = given().
                param("user_id", userId).
                param("type", type).
                param("owner_id", ownerId).
                param("item_id", itemId).
                param("access_token", token).
                param("v", version).
                get("likes.isLiked");
        response.then().
                body("$", not(hasKey("error")));
        return response.
                then().
                extract().response().path("response.liked");
    }

    public void wallDelete(String ownerId, String postId, String token) {
        Response response = given().
                param("owner_id", ownerId).
                param("post_id", postId).
                param("access_token", token).
                param("v", version).
                get("wall.delete");
        response.then().
                body("$", not(hasKey("error")));
    }

    public String getCurrentUserId(String token) {
        Response response = given().
                param("access_token", token).
                param("v", version).
                get("users.get");
        response.then().
                body("$", not(hasKey("error")));
        return response.
                then().
                extract().response().path("response[0].id").toString();
    }

    public String likesGetList(String type, String ownerId, String itemId, String token) {
        Response response = given().
                param("type", type).
                param("owner_id", ownerId).
                param("item_id", itemId).
                param("access_token", token).
                param("v", version).
                get("likes.getList");
        response.then().
                body("$", not(hasKey("error")));
        return response.
                then().
                extract().response().path("response.items").toString();
    }

    public void photosDelete(String ownerId, String photoId, String token){
        Response response = given().
                param("owner_id", ownerId).
                param("photo_id", Integer.parseInt(photoId.substring(photoId.indexOf("_")+1))).
                param("access_token", token).
                param("v", version).
                get("photos.delete");
        response.then().
                body("$", not(hasKey("error")));
    }

    public void docsDelete(String ownerId, String docId, String token){
        Response response = given().
                param("owner_id", ownerId).
                param("doc_id", Integer.parseInt(docId.substring(docId.indexOf("_")+1))).
                param("access_token", token).
                param("v", version).
                get("docs.delete");
        response.then().
                body("$", not(hasKey("error")));
    }

    private Response photosGetWallUploadServer(String token, String userId) {
        Response response = given().
                param("owner_id", userId).
                param("access_token", token).
                param("v", version).
                get("photos.getWallUploadServer");
        response.then().
                body("$", not(hasKey("error")));
        return response.
                then().
                extract().response();
    }

    private Response uploadOnServer(String url, String imagePath) {
        Response response = given().
                multiPart("file", new File(imagePath)).
                when().
                post(url);
        response.then().
                body("$", not(hasKey("error")));
        return response.
                then().
                extract().response();
    }

    private Response photosSaveWallPhoto(String token, String userId, Response jsonResp) {
        Response response = given().
                param("user_id", userId).
                param("photo", jsonResp.jsonPath().getString("photo")).
                param("server", jsonResp.jsonPath().getString("server")).
                param("hash", jsonResp.jsonPath().getString("hash")).
                param("access_token", token).
                param("v", version).
                get("photos.saveWallPhoto");
        response.then().
                body("$", not(hasKey("error")));
        return response.
                then().
                extract().
                response();
    }

    private String docsGetWallUploadServer(String token, String userId) {
        Response response = given().
                param("owner_id", userId).
                param("access_token", token).
                param("v", version).
                get("docs.getWallUploadServer");
        response.then().
                body("$", not(hasKey("error")));
        return response.
                then().
                extract().path("response.upload_url");
    }

    private Response docsSave(String token, String userId, Response jsonResp) {
        Response response = given().
                param("user_id", userId).
                param("file", jsonResp.jsonPath().getString("file")).
                param("access_token", token).
                param("v", version).
                get("docs.save");
        response.then().
                body("$", not(hasKey("error")));
        return response.then().
                extract().
                response();
    }
}
