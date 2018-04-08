package first.winning.com.tyutcenter.model;

/**
 * 用来统一返回结果
 * 因为服务器端返回的格式code 和errormessage是一样的
 * 所不同的是data的内容
 * Created by yuzhijun on 2016/3/29.
 */
public class HttpResult<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
