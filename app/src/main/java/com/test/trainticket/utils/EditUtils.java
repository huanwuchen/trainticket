package com.test.trainticket.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/12.
 */
public class EditUtils {

    /**
     * @param edit
     * @return boolean 返回类型
     * @Title: hasInput
     * @Description: TODO(检查单一EditText是否有输入)
     * @date 2013年12月24日 上午10:26:56
     */
    public static boolean hasInput(EditText edit) {
        if (null != edit.getText() && !"".equals(edit.getText().toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * @param edits
     * @return boolean 返回类型
     * @Title: allHasInput
     * @Description: TODO(检查所给的EditText是否均有内容)
     * @date 2013年12月24日 上午10:28:25
     */
    public static boolean allHasInput(EditText... edits) {
        for (EditText edit : edits) {
            if (!hasInput(edit)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param edit1
     * @param edit2
     * @return boolean 返回类型
     * @Title: isEqual
     * @Description: TODO(判断所给EditText内容是否相等)
     * @date 2013年12月24日 上午10:32:21
     */
    public static boolean isEqual(EditText edit1, EditText edit2) {
        if (edit1.getText().toString().equals(edit2.getText().toString())) {
            return true;
        }
        return false;
    }

    public static void fillBankNumSpeace(final EditText mAddCardNumEdt) {// 银行卡补齐
        // 空格
        mAddCardNumEdt.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    if (s.length() == 4) {
                        mAddCardNumEdt.setText(s + " ");
                        mAddCardNumEdt.setSelection(5);
                    }
                    if (s.length() == 9) {
                        mAddCardNumEdt.setText(s + " ");
                        mAddCardNumEdt.setSelection(10);
                    }
                    if (s.length() == 14) {
                        mAddCardNumEdt.setText(s + " ");
                        mAddCardNumEdt.setSelection(15);
                    }
                    if (s.length() == 19) {
                        mAddCardNumEdt.setText(s + " ");
                        mAddCardNumEdt.setSelection(20);
                    }
                } else if (count == 0) {
                    if (s.length() == 4) {
                        mAddCardNumEdt.setText(s.subSequence(0, s.length() - 1));
                        mAddCardNumEdt.setSelection(3);
                    }
                    if (s.length() == 9) {
                        mAddCardNumEdt.setText(s.subSequence(0, s.length() - 1));
                        mAddCardNumEdt.setSelection(8);
                    }
                    if (s.length() == 14) {
                        mAddCardNumEdt.setText(s.subSequence(0, s.length() - 1));
                        mAddCardNumEdt.setSelection(13);
                    }
                    if (s.length() == 19) {
                        mAddCardNumEdt.setText(s.subSequence(0, s.length() - 1));
                        mAddCardNumEdt.setSelection(18);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static boolean isPassWordLength(String pwd) {
        boolean result = Pattern.matches("^.{6,16}$", pwd);
        return result;
    }

    public static boolean isPassWord(String pwd) {
        boolean result = Pattern.matches("^(?![\\d]+$)(?![a-z]+$)(?![A-Z]+$)(?![^\\da-zA-Z]+$).{6,16}", pwd);
        return result;
    }

    /**
     * 身份证正则验证
     *
     * @param pwd
     * @return
     */
    public static boolean isIdCard(String pwd) {
        return Pattern.matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", pwd);
    }




}
