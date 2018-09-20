package com.tyutcenter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tyutcenter.R;
import com.tyutcenter.base.MyLeanCloudApp;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017-06-29.
 */

public class CommonUtil {
    /**
     *
     * @param view  只要是界面中的view即可
     * @param text  snackbar中的提示内容
     * @param bgColor 背景颜色，设置成0为默认颜色，即程序基色。
     */
    public static void showSnackBar(final View view, String text, int bgColor){
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View snackView = snackbar.getView();
        if(snackView!=null){
                snackView.setBackgroundColor(bgColor);//修改view的背景色
                ((TextView) snackView.findViewById(R.id.snackbar_text)).setTextColor(MyLeanCloudApp.getInstance().getResources().getColor(R.color.white));//获取Snackbar的message控件，修改字体颜色
        }
        snackbar.show();
    }
    public static void showSnackBar(final View view, String text){
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View snackView = snackbar.getView();
        if(snackView!=null){
                snackView.setBackgroundColor(MyLeanCloudApp.getInstance().getResources().getColor(R.color.colorPrimary));//修改view的背景色
                ((TextView) snackView.findViewById(R.id.snackbar_text)).setTextColor(MyLeanCloudApp.getInstance().getResources().getColor(R.color.white));//获取Snackbar的message控件，修改字体颜色
        }
        snackbar.show();
    }



    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * @Params str 要判断是否为空的字符串
     * @Return boolean 返回是否为空
     * */
    public static boolean isStrEmpty(String str) {
        return str != null && !"".equals(str.trim())?(str = str.replaceAll(" ", "").trim()) == null || "".equals(str.trim()):true;
    }

    /**
     *
     * @param var0  手机号
     * @return  p   判断此手机号是否正确
     * */
    public static boolean isMobileNO(String var0) {
        return Pattern.compile("[1][34578]\\d{9}").matcher(var0).matches();
    }

    /**
     * 判断对象是否为空
     * @param var0
     * @return
     */

    public static boolean isNotEmpty(Object var0) {
        return var0 != null;
    }
    public static Date AddTimeforNow2() {
        Date var0 = new Date();
        return new Date(var0.getTime() + 59000L);
    }

    public static String getShardPStringByKey(String key) {
        return MyLeanCloudApp.getInstance().getSharedPreferences("user_info",MyLeanCloudApp.getInstance().MODE_PRIVATE).getString(key,"");
    }

    public static void setShardPString(String key, String value) {
        SharedPreferences.Editor var2;
        var2 = MyLeanCloudApp.getInstance().getSharedPreferences("user_info",MyLeanCloudApp.getInstance().MODE_PRIVATE).edit();
        var2.putString(key,value);
        var2.commit();
    }
    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param begin
     *            开始位置
     * @param end
     *            结束位置
     * @return
     */
    public static String getStarString(String content, int begin, int end) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end >= content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());

    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param frontNum
     *            保留前面字符的位数
     * @param endNum
     *            保留后面字符的位数
     * @return 带星号的字符串
     */

    public static String getStarString2(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }

    public static int getResColor(int id) {
        return MyLeanCloudApp.getInstance().getResources().getColor(id);
    }


    public static Date fullConverToDate(String strDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.parse(strDate);
    }
    public static Date fullStringToDate(String strDate)  {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMoneyType(String string) {
        // 把string类型的货币转换为double类型。
        Double numDouble = Double.parseDouble(string);
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // 把转换后的货币String类型返回
        String numString = format.format(numDouble);
        return numString;
    }
    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }


    /**
     * 把字符串转为日期  
     * @return
     */
    public static Date ConverToDate(String strDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }

    public static String getDateTime() {
        Date date = new Date();
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }
    public static String getDateYear() {
        Date date = new Date();
        return (new SimpleDateFormat("yyyy")).format(date);
    }


    /**
     * 判断是否是同一天
     * */
    public static boolean isSameDay(String dateOne, String dateTwo){
        if (dateOne.length() >= 10 && dateTwo.length() >= 10)
        {
            if (dateOne.substring(0,10).equalsIgnoreCase(dateTwo.substring(0,10))){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * 判断时间是上午还是下午
     * */
    public static boolean isMorning(String dateStr) throws Exception {
        Date date = fullConverToDate(dateStr);
        Calendar startCal =  Calendar.getInstance();
        startCal.setTime(date);
        int apm = startCal.get(Calendar.AM_PM);
        //0上午1下午
        return apm == 0? true:false;
    }

    /**
     * 把两个日期间的所有日期返回(间隔小于一周则返回一周的所有时间)
     * */
    public static List<String> getDatePeriod(String startDateStr, String endDateStr) throws ParseException {
        //将字符串时间转化成date格式
        Date startDate = ConverToDate(startDateStr);
        Date endDate = ConverToDate(endDateStr);
        //获取两个时间间的间隔天数
        int gapDay = getGapCount(startDate,endDate) < 7 ? 6 : getGapCount(startDate,endDate);

        ArrayList timeList = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startCal =  Calendar.getInstance();
        startCal.setTime(startDate);
        timeList.add(dateFormat.format(startCal.getTime()));

        for(int var5 = 0; var5 < gapDay; ++var5) {
            startCal.add(Calendar.DATE,1);
            timeList.add(dateFormat.format(startCal.getTime()));
        }
        return timeList;
    }

    /**
     * 判断当前时间是星期几
     * */
    public static String getWeekDay(String dateStr) throws ParseException {
        String Week = "周";
        Date date = ConverToDate(dateStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startCal =  Calendar.getInstance();
        startCal.setTime(date);
        switch (startCal.get(Calendar.DAY_OF_WEEK)){
            case 1:
                Week += "天";
                break;
            case 2:
                Week += "一";
                break;
            case 3:
                Week += "二";
                break;
            case 4:
                Week += "三";
                break;
            case 5:
                Week += "四";
                break;
            case 6:
                Week += "五";
                break;
            case 7:
                Week += "六";
                break;
            default:
                break;
        }
        return Week;
    }
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static  int getVersionCode(Context context)
    {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int version = packInfo.versionCode;
        return version;
    }
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap bytes2Bitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /**
     * Drawable → Bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /*
         * Bitmap → Drawable
		 */
    @SuppressWarnings("deprecation")
    public static Drawable bitmap2Drawable(Bitmap bm) {
        if (bm == null) {
            return null;
        }
        BitmapDrawable bd = new BitmapDrawable(bm);
        bd.setTargetDensity(bm.getDensity());
        return new BitmapDrawable(bm);
    }



    /**
     *判断是否是wifi环境
     * */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
