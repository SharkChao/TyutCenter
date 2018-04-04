package first.winning.com.tyutcenter.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.plus.DensityUtil;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.model.ResponseError;
import first.winning.com.tyutcenter.presenter.MainPresenter;
import first.winning.com.tyutcenter.views.LoadingView;

/**
 * Created by Admin on 2018/1/31.
 */

public abstract class BaseActivity<UC> extends AppCompatActivity implements BasePresenter.BaseUi<UC>{

    public static final int ivBack = R.mipmap.wn_iv_top_back;
    private volatile boolean isLoading;
    private LoadingView progressDialog;
    private TextView mTvTitle;
    private ImageView mBtnLeft;
    private ImageView mBtnRight;
    private Toolbar mToolbar;
    private LinearLayout mLLContent;
    private TextView mTvLeft;
    private TextView mTvRight;
    protected LinearLayout mLlLeft;
    private LinearLayout mLlRight;
    public  static BaseActivity currentActivity;
    private UC mCallback;
    private MainPresenter mMainController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;
        getSupportActionBar().hide();
        setToolbar();
        View contentView = findViewById(R.id.content);
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(this),getContentView(),(ViewGroup) contentView,true);
        mMainController = MyLeanCloudApp.getInstance().getMainPresenter();
        ((BasePresenter)mMainController).attachUi(this);

        initTitle();
        initView(viewDataBinding);
        initData();
        initEvent();

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
    /**
     * 1.设置沉浸式
     * 2.初始化toolbar中控件
     */
    private  void setToolbar(){
        //用来设置沉浸式状态栏
        super.setContentView(R.layout.layout_base_activity);
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvTitle = (TextView) findViewById(R.id.toolbar_title);
        mBtnRight = (ImageView) findViewById(R.id.btnRight);
        mBtnLeft = (ImageView) findViewById(R.id.btnLeft);
        mLLContent= (LinearLayout) findViewById(R.id.content);
        mLlLeft= (LinearLayout) findViewById(R.id.llLeft);
        mLlRight= (LinearLayout) findViewById(R.id.llRight);
        mTvLeft=(TextView) findViewById(R.id.tvLeft);
        mTvRight=(TextView) findViewById(R.id.tvRight);
        setLeftDefault();
        mToolbar.setTitle("");
    }


    protected void isShowToolBar(boolean isShow){
        mToolbar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
    protected void isShowLeft(boolean isShow){
        mBtnLeft.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTvLeft.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
    protected void isShowRight(boolean isShow){
        mBtnRight.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTvRight.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
    protected void setCenterTitle(String title){
        mTvTitle.setText(title == null || "".equalsIgnoreCase(title) ? "" : title);
    }
    protected  void setRightTitle(String title, View.OnClickListener listener){
        if (title != null && !"".equalsIgnoreCase(title)){
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setText(title);
            mBtnRight.setVisibility(View.GONE);
            mLlRight.setOnClickListener(listener);
        }
    }
    protected void setRightTitleAndIcon(String title, @DrawableRes int icon, View.OnClickListener listener){
        if (title != null && !"".equalsIgnoreCase(title)){
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setText(title);
        }
        mBtnRight.setVisibility(View.VISIBLE);
        mBtnRight.setImageResource(icon);
        ViewGroup.LayoutParams linearParams = mBtnRight.getLayoutParams();
        linearParams.height = DensityUtil.dip2px(this,22);
        linearParams.width = DensityUtil.dip2px(this,22);
        mBtnRight.setLayoutParams(linearParams);
        mLlRight.setOnClickListener(listener);
    }
    protected  void setLeftTitle(String title, View.OnClickListener listener){
        if (title != null && !"".equalsIgnoreCase(title)){
            mTvLeft.setVisibility(View.VISIBLE);
            mTvLeft.setText(title);
            mBtnLeft.setVisibility(View.GONE);
            mLlLeft.setOnClickListener(listener);
        }
    }
    protected  void setLeftTitleColor(int resId){
        int color=getResources().getColor(resId);
        mTvLeft.setVisibility(View.VISIBLE);
        mTvLeft.setTextColor(color);
    }
    protected  void setRightTitleColor(int resId){
        int color=getResources().getColor(resId);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setTextColor(color);
    }
    protected void setLeftTitleAndIcon(String title,@DrawableRes int icon, View.OnClickListener listener){
        if (title != null && !"".equalsIgnoreCase(title)){
            mTvLeft.setVisibility(View.VISIBLE);
            mTvLeft.setText(title);
        }
        mBtnLeft.setVisibility(View.VISIBLE);
        mBtnLeft.setBackgroundResource(icon);
        ViewGroup.LayoutParams linearParams = mBtnLeft.getLayoutParams();
        linearParams.height = DensityUtil.dip2px(this,15);
        linearParams.width = DensityUtil.dip2px(this,15);
        mBtnLeft.setLayoutParams(linearParams);
        mLlLeft.setOnClickListener(listener);
    }
    protected void  setLeftDefault(){
        mTvLeft.setVisibility(View.VISIBLE);
        mTvLeft.setText("返回");
        mBtnLeft.setVisibility(View.VISIBLE);
        mBtnLeft.setBackgroundResource(ivBack);
        ViewGroup.LayoutParams linearParams = mBtnLeft.getLayoutParams();
        linearParams.height = DensityUtil.dip2px(this,15);
        linearParams.width = DensityUtil.dip2px(this,15);
        mBtnLeft.setLayoutParams(linearParams);
        mLlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showProgressDialog() {
        if(!this.isLoading) {
            this.hideProgressDialog();
            this.isLoading = true;
            this.progressDialog = new LoadingView();
            this.progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if(this.progressDialog != null) {
            try {
                this.progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.progressDialog = null;
        }
        this.isLoading = false;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    protected ImageView getLeftButton(){
        return mBtnLeft;
    }
    protected ImageView getRightButton(){
        return mBtnRight;
    }

    public LinearLayout getLlRight() {
        return mLlRight;
    }


    public abstract  void initTitle();
    public abstract void initView(ViewDataBinding viewDataBinding);
    public abstract void initData();
    protected abstract void initEvent();

    @Override
    public void onResponseError(ResponseError error) {
        Toast.makeText(currentActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setCallbacks(UC callbacks) {
        mCallback = callbacks;
    }

    @Override
    public UC getCallbacks() {
        return mCallback;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainController.init();
        ((BasePresenter)mMainController).startUi(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainController.suspend();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((BasePresenter)mMainController).detachUi(this);
    }
}


