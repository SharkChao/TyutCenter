package first.winning.com.tyutcenter;

import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseActivity;
import first.winning.com.tyutcenter.databinding.ActivityMainBinding;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    public void initData(ViewDataBinding viewDataBinding) {
        ActivityMainBinding binding = (ActivityMainBinding) viewDataBinding;
        binding.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 测试 SDK 是否正常工作的代码
                AVObject testObject = new AVObject("TestObject");
                testObject.put("words","Hello World!");
                testObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e == null){
                            Log.d("saved","success!");
                        }
                    }
                });
            }
        });

    }
}
