package first.winning.com.tyutcenter.model;

/**
 * 用来统一返回结果
 * 因为服务器端返回的格式code 和errormessage是一样的
 * 所不同的是data的内容
 * Created by yuzhijun on 2016/3/29.
 */
public class HttpResult<T> {
    private int ErrorCode;
    private String ErrorMessage;
    private T Data;

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }


    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
