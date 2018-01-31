package first.winning.com.tyutcenter.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import first.winning.com.tyutcenter.annotation.ContentView;

/**
 * Created by Admin on 2018/1/31.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, getContentView());
        initData(viewDataBinding);
    }
    //获取注解的值
    public int getContentView(){
        Class<? extends BaseActivity> aClass = this.getClass();
        if (aClass.isAnnotationPresent(ContentView.class)){
            ContentView annotation = aClass.getAnnotation(ContentView.class);
            int layoutId = annotation.value();
            return layoutId;
        }
        return 0;
    }
    public abstract void initData(ViewDataBinding viewDataBinding);
}


