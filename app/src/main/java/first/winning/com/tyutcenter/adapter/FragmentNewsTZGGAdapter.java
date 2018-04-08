package first.winning.com.tyutcenter.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.model.News;
import first.winning.com.tyutcenter.utils.CommonUtil;

/**
 * Created by Admin on 2018/2/2.
 */

public class FragmentNewsTZGGAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
    public FragmentNewsTZGGAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        helper.setText(R.id.tvTitle, CommonUtil.isStrEmpty(item.getTitle())?"未知":item.getTitle());
        helper.setText(R.id.tvFrom, CommonUtil.isStrEmpty(item.getFrom())?"未知":item.getFrom());
        if (!CommonUtil.isStrEmpty(item.getTime())){
            String day = item.getTime().substring(0, item.getTime().indexOf("|"));
            helper.setText(R.id.tvDay,day);

            String year = item.getTime().substring(item.getTime().indexOf("|") + 1, item.getTime().length());
            helper.setText(R.id.tvYear,year);
        }else {
            helper.setText(R.id.tvDay,"未知");
            helper.setText(R.id.tvYear,"未知");
        }

    }

}
