/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syntech.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

/**
 *
 * @author rasmi
 */
public class RestResponse {

    private String success;
    private String code;
    private String message;
    private String result;

    public RestResponse() {
    }

    public RestResponse(String success, String code, String message, String result) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static Response responseBuilder(String success, String code, String message, Object result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        String str = mapper.writeValueAsString(result);
        JsonObject json = Json.createObjectBuilder()
                .add("success", success)
                .add("code", code)
                .add("message", message)
                .add("result", str == null ? "" : str).build();
        return Response.status(Response.Status.OK).entity(json).build();
    }
}
