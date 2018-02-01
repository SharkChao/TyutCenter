package first.winning.com.tyutcenter;

import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentManager;

import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseActivity;
import first.winning.com.tyutcenter.databinding.ActivityHomeBinding;

@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    private FragmentManager fragmentManager;

    @Override
    public void initView() {
        fragmentManager = getSupportFragmentManager();

    }

    @Override
    public void initData(ViewDataBinding viewDataBinding) {
        ActivityHomeBinding binding = (ActivityHomeBinding) viewDataBinding;

    }

    @Override
    protected void initEvent() {

    }
}
