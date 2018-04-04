package first.winning.com.tyutcenter.inject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by yuzhijun on 2017/2/4.
 */
public class StringConverter implements Converter<ResponseBody,String> {
    public static final StringConverter INSTANCE = new StringConverter();
    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
