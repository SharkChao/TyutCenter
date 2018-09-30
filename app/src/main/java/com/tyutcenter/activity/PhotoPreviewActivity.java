package com.tyutcenter.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.tyutcenter.R;
import com.tyutcenter.adapter.PhotoPreviewAdapter;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.utils.CommonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 2018/2/2.
 */

@ContentView(R.layout.lx_photo_preview_activity)
public class PhotoPreviewActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainUi{

    public static final String IMAGE_PATH = "image_path";
    public static final String IMAGE_POSITION = "image_position";
    private PhotoView pvPhoto;
    private int currentPhotoPosition;
    private ArrayList<String> mImageList;
    private List<View> mViewList = new ArrayList<>();
    private ViewPager mViewPager;
    private PhotoPreviewAdapter mAdapter;



    @Override
    public void initTitle() {
        isShowToolBar(true);
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mViewPager = findViewById(R.id.viewPager);
        mImageList = getIntent().getStringArrayListExtra(IMAGE_PATH);
        currentPhotoPosition = getIntent().getIntExtra(IMAGE_POSITION,0);
        setCenterTitle((currentPhotoPosition+1)+"/"+mImageList.size());

        mAdapter = new PhotoPreviewAdapter(this,mViewList);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        for (int i = 0;i<mImageList.size();i++){
            View view = LayoutInflater.from(this).inflate(R.layout.lx_photo_preview_item,null,false);
            pvPhoto = (PhotoView) view.findViewById(R.id.photoView);
            if (!CommonUtil.isStrEmpty(mImageList.get(i))){
                if (mImageList.get(i).contains("http:")){
                    Glide.with(this).load(mImageList.get(i)).into(pvPhoto);
                }else{
                    Glide.with(this).load(new File(mImageList.get(i))).into(pvPhoto);
                }
                mViewList.add(view);
            }
        }
        mAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(currentPhotoPosition);
    }

    @Override
    public void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCenterTitle((position+1)+"/"+mImageList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public static void startPhotoPreview(Context context, ArrayList<String> mImageList, int position){
        Intent intent = new Intent(context,PhotoPreviewActivity.class);
        intent.putStringArrayListExtra(IMAGE_PATH,mImageList);
        intent.putExtra(IMAGE_POSITION,position);
        context.startActivity(intent);
    }

}
