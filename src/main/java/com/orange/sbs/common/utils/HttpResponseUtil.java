package com.orange.sbs.common.utils;

import com.orange.sbs.common.core.Result;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/6 说明：
 */
public class HttpResponseUtil {

    public static void writeJson(HttpServletResponse response, Object o) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        JsonGenerator jsonGenerator = objectMapper.getFactory()
                .createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
        objectMapper.writeValue(jsonGenerator, o);
    }

    public static void writeJson(HttpServletResponse response, Integer code,
                                 String message) throws IOException {
        writeJson(response, new HttpResult(code, message));
    }

    public static HttpResult success() {
        return new HttpResult(HttpServletResponse.SC_OK);
    }

    public static HttpResult success(String token) {
        return new HttpResult(HttpServletResponse.SC_OK, token);
    }

    public static Result data(String massage) {
        return new Result(HttpServletResponse.SC_OK, massage, Collections.EMPTY_LIST);
    }

    public static <T> HttpResult success(T data) {
        return new HttpResult(HttpServletResponse.SC_OK, data);
    }

    public static Result error() {
        return new Result(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    public static Result error(String message) {
        return new Result(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }

    public static Result error(int code, String message) {
        return new Result(code, message);
    }

    public static Result error(BindingResult result) {
        String msg = "";
        if (result.hasFieldErrors()) {
            msg = result.getFieldErrors().get(0).getField() + ":" + result.getFieldError()
                    .getDefaultMessage();
        }
        return new Result(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static void error(HttpServletResponse response, String message) {
        try {
            writeJson(response, error(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void error(HttpServletResponse response, int code, String message) {
        try {
            writeJson(response, error(code, message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
