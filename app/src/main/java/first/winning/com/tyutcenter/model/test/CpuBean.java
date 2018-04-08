package first.winning.com.tyutcenter.model.test;

import java.util.Locale;

/**
 * cpu ratio
 * Created by yuzhijun on 2018/3/27.
 */
public class CpuBean {
    //total used ratio(user + system + io + others)
    public double totalUseRatio;
    // app's cpu ratio
    public double appCpuRatio;
    // user process's cpu ratio
    public double userCpuRatio;
    // system process's cpu ratio
    public double sysCpuRatio;
    // io waiting ratio
    public double ioWaitRatio;

    public CpuBean(double totalUseRatio, double appCpuRatio, double userCpuRatio, double sysCpuRatio, double
            ioWaitRatio) {
        this.totalUseRatio = totalUseRatio;
        this.appCpuRatio = appCpuRatio;
        this.userCpuRatio = userCpuRatio;
        this.sysCpuRatio = sysCpuRatio;
        this.ioWaitRatio = ioWaitRatio;
    }

    public CpuBean() {
    }

    @Override
    public String toString() {
        return "app:" +
                String.format(Locale.US, "%.1f", appCpuRatio * 100f) +
                "% , total:" +
                String.format(Locale.US, "%.1f", totalUseRatio * 100f) +
                "% , user:" +
                String.format(Locale.US, "%.1f", userCpuRatio * 100f) +
                "% , system:" +
                String.format(Locale.US, "%.1f", sysCpuRatio * 100f) +
                "% , iowait:" +
                String.format(Locale.US, "%.1f", ioWaitRatio * 100f) + "%";
    }

    public double getTotalUseRatio() {
        return totalUseRatio;
    }

    public void setTotalUseRatio(double totalUseRatio) {
        this.totalUseRatio = totalUseRatio;
    }

    public double getAppCpuRatio() {
        return appCpuRatio;
    }

    public void setAppCpuRatio(double appCpuRatio) {
        this.appCpuRatio = appCpuRatio;
    }

    public double getUserCpuRatio() {
        return userCpuRatio;
    }

    public void setUserCpuRatio(double userCpuRatio) {
        this.userCpuRatio = userCpuRatio;
    }

    public double getSysCpuRatio() {
        return sysCpuRatio;
    }

    public void setSysCpuRatio(double sysCpuRatio) {
        this.sysCpuRatio = sysCpuRatio;
    }

    public double getIoWaitRatio() {
        return ioWaitRatio;
    }

    public void setIoWaitRatio(double ioWaitRatio) {
        this.ioWaitRatio = ioWaitRatio;
    }
}
