package first.winning.com.tyutcenter.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import first.winning.com.tyutcenter.annotation.ContentView;


/**
 *
 * Created by yuzhijun on 2017/6/27.
 */
public abstract class BaseFragment extends Fragment{

    private ViewDataBinding bind;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(getContentView(), container, false);
        bind = DataBindingUtil.bind(inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData(bind);
        initEvent();
    }

    protected abstract void initEvent();

    protected abstract void initData(ViewDataBinding bind);

    protected abstract void initView();

    //获取注解的值
    public int getContentView(){
        Class<? extends BaseFragment> aClass = this.getClass();
        if (aClass.isAnnotationPresent(ContentView.class)){
            ContentView annotation = aClass.getAnnotation(ContentView.class);
            int layoutId = annotation.value();
            return layoutId;
        }
        return 0;
    }
}
