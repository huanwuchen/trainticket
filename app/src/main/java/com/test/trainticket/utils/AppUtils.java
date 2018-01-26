package com.test.trainticket.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/21.
 */

public class AppUtils {

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        String version = "0.0";
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "0.0";
            e.printStackTrace();
        }
        return version.replace("-debug","");
    }

    /**
     * 获取包名
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        String version ;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "";
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        int version = 1;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            version = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return version;
    }

    /**
     * 获取设备操作系统版本
     * @param ctx
     * @return
     */
    public static String getOSVersion(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        String osVersion = tm == null ? "" : tm.getDeviceSoftwareVersion();
        return osVersion;
    }

    /**
     * 获取设备序列号
     * @param ctx
     * @return
     */
    public static String getDeviceId(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        String deviceId = tm == null ? "" : tm.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        //虚拟一个设备id
        deviceId = UUID.randomUUID().toString();
        if (deviceId != null && deviceId.length() > 32) {
            deviceId = deviceId.substring(0, 32);
        }
        return deviceId;
    }

    /**
     * 获取设备序列号
     * @param ctx
     * @return
     */
    public static String getRandomDeviceId(Context ctx) {
        //虚拟一个设备id
       String deviceId = UUID.randomUUID().toString();
        if (deviceId != null && deviceId.length() > 32) {
            deviceId = deviceId.substring(0, 32);
        }
        return deviceId;
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei  =  telephonyMgr.getDeviceId();
        if(imei==null){
            // android pad
            imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return imei;
    }

    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * @Title: getServiceProvider
     * @Description: TODO(获取当前设备运营商类型 1：移动、2：电信、3：联通、4：其他)
     * @param ctx
     * @return
     * int    返回类型
     * @date 2014年1月16日 下午1:37:27
     */
    public static int getServiceProvider(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return 4;
        }
        String networkOperator = tm.getNetworkOperator();
        if (networkOperator == null || ("").equals(networkOperator)) {
            return 4;
        }
        String mnc = networkOperator.substring(3);
        // 中移动的mnc id为00
        int type = -1;
        if (("00").equals(mnc)) {
            type = 1;
        } else if (("01").equals(mnc)) { // 中联通的mnc id为01
            type = 3;
        } else if (("03").equals(mnc)) { // 中电信的mnc id为03
            type = 2;
        }
        return type;
    }

    public static String getAppChannel(Context context){
        String app_channel ="";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            app_channel=appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return app_channel;
    }

    public static String getAppSign(Context context){
        String str = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            str = getSHA1String(sign.toByteArray());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str ;
    }

    public static final String getSHA1String(byte[] signature) {
        String hexString = "";
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signature));
            //加密算法的类，这里的参数可以使MD4,MD5等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");  //SHA1 / MD5
            //获得公钥
            byte[] publicKey = md.digest(cert.getEncoded());
            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hexString;
    }

    //这里是将获取到得编码进行16进制转换
    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideKeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS); // (WidgetSearchActivity是当前的Activity)
    }

    public static void openBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


    /**
     * 检查系统上是否有该应用
     * @param context
     * @param pkgName
     * @return
     */
    public static boolean isExist(Context context, String pkgName) {
        PackageInfo pkgInfo = null;
        PackageManager pm = context.getPackageManager();
        try {
            // 0代表是获取版本信息
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pkgInfo != null;
    }


    private final static int kSystemRootStateUnknow = -1;
    private final static int kSystemRootStateDisable = 0;
    private final static int kSystemRootStateEnable = 1;
    private static int systemRootState = kSystemRootStateUnknow;

    /**
     * 判断是否root
     * @return
     */
    public static boolean isRootSystem() {
        if (systemRootState == kSystemRootStateEnable) {
            return true;
        } else if (systemRootState == kSystemRootStateDisable) {
            return false;
        }
        File f = null;
        final String kSuSearchPaths[] = { "/system/bin/", "/system/xbin/",
                "/system/sbin/", "/sbin/", "/vendor/bin/" };
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    systemRootState = kSystemRootStateEnable;
                    return true;
                }
            }
        } catch (Exception e) {
        }
        systemRootState = kSystemRootStateDisable;
        return false;
    }

    /**
     * 隐藏键盘
     */
    public static void hideInput(final Activity mActivity) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) mActivity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 10);

    }

    /**
     * 获取meta
     *
     * @param context
     * @param name
     * @return
     */

    public static String getApplicationMetaValue(Context context, String name) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }


}
