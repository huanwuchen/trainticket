package com.test.trainticket.common;

/**
 * Created by Administrator on 2018/1/24.
 */

public class Constants {

    /**
     * 坐标数据
     */
    public static final String[] YZM_COORDINATES = {"35,35", "105,35", "175,35", "245,35", "35,105", "105,105", "175,105",
            "245,105"};

    /**
     * 获取12306变态验证码接口
     * 返回的是图片
     */
    public static final String CAPTCHAIMAGE_URL = "https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login" +
            "&rand=sjrand&t=";

    /**
     * 验证选择的验证码接口
     */
    public static final String CAPTCHACHECK_URL = "https://kyfw.12306.cn/passport/captcha/captcha-check";

    /**
     * 登录
     */
    public static final String LOGIN_URL = "https://kyfw.12306.cn/passport/web/login";

    /**
     * 初始化页面 先访问这个
     */
    public static final String INITPAGE_URL = "https://kyfw.12306.cn/otn/index/init";


}
