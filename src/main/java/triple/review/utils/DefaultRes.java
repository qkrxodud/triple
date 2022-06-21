package triple.review.utils;

import lombok.Data;

@Data
public class DefaultRes {
    private int statusCode;
    private String message;
    private Object data;

    public static DefaultRes createDefaultRes(int statusCode, String responseMessage, Object data) {
        DefaultRes defaultRes = new DefaultRes();
        defaultRes.statusCode = statusCode;
        defaultRes.message = responseMessage;
        defaultRes.data = data;
        return defaultRes;
    }
}