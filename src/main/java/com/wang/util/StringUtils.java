package com.wang.util;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isBlank(String string) {
        return string == null || string.trim().equals("");
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    /**
     * The empty String <code>""</code>.
     *
     * @since 2.0
     */
    public static final String EMPTY = "";

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 替换指定位置的字符
     *
     * @param index
     * @param res
     * @param str
     * @return
     */
    public static String replaceIndex(int index, String res, String str) {
        return res.substring(0, index) + str + res.substring(index + 1);
    }

    @SuppressWarnings("unchecked")
    public static List<Integer> string2integerList(String value) {
        String[] split = value.split(",");
        @SuppressWarnings("rawtypes")
        List<Integer> list = new ArrayList();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }

    public static List<String> string2stringList(String value) {
        String[] split = value.split(",");
        return Arrays.asList(split);
    }

    @SuppressWarnings("rawtypes")
    public static String list2string(List<?> valueList) {
        StringBuffer buf = new StringBuffer();
        for (Iterator ite = valueList.iterator(); ite.hasNext(); ) {
            buf.append(ite.next());
            if (ite.hasNext()) buf.append(",");
        }
        return buf.toString();
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Long getLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer getInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String cutIfExceed(String original, int length) {
        if (Strings.isNullOrEmpty(original) || original.length() <= length) {
            return original;
        } else {
            return original.substring(0, length);
        }
    }

    public static String getCurrentDate() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(new Date());
    }

    /**
     * 验证时间是否在30分钟之内 大于30为true 小于30为false
     *
     * @param sendDate
     * @param now
     * @return
     */
    public static boolean compareTime(Date sendDate, Date now) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String systemTime = sdf.format(sendDate).toString();
            String compareTime = sdf.format(now).toString();
            Date begin;
            Date end;
            begin = sdf.parse(systemTime);
            end = sdf.parse(compareTime);

            long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

            long day = between / (24 * 3600);
            long hour = between % (24 * 3600) / 3600;
            long minute = between % 3600 / 60;
            return !((hour == 0) && (day == 0) && (minute < 30));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 手机号码验证
     *
     * @param mobile 手机号码
     * @return 验证结果
     */
    public static boolean isMobile(String mobile) {
        String regular = "1[3,4,5,8,7,6,9]{1}\\d{9}";
        Pattern pattern = Pattern.compile(regular);
        boolean flag = false;
        if (isNotBlank(mobile)) {
            Matcher matcher = pattern.matcher(mobile);
            flag = matcher.matches();
        }
        return flag;
    }

    public static void main(String[] args) {
        String mobile = "15000808994";
        System.out.println(isMobile(mobile));
    }

    /**
     * 生成固定长度的随机数
     *
     * @param strLength 随机数长度
     * @return 随机数
     */
    public static String getFixLenthString(int strLength) {

        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        String random = fixLenthString.substring(2, strLength + 2);

        if (random.length() > 0 && "0".equals(random.substring(0, 1))) {
            return getFixLenthString(strLength);
        } else {
            // 返回固定的长度的随机数
            return random;
        }
    }

    /**
     * 密码复杂度验证
     *
     * @param password
     * @return
     */
    public static boolean complexity(String password) {
        //String str = "^(?![~!@#$%^&*()-@\\[-_+=|{}':;/D]+$)(?![^a-zA-Z?!~!@#$%^&*()-@\\[-_+=|{}':;/D]+$)(?![^0-9?!~!@#$%^&*()-@\\[-_+=|{}':;/D]+$).{6,20}$";
        String str = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![_]+$)[0-9A-Za-z_]{6,20}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }
    /**
     * 密码复杂度验证
     *
     * @param password
     * @return
     */
    public static boolean complexityV2(String password) {
        String str ="[0-9]{6}";
       // String str = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![_]+$)[0-9A-Za-z_]{6,20}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 验证
     *
     * @param rule
     * @param parameter
     * @return
     */
    public static boolean verify(String rule ,String parameter) {
      //  String str ="[0-9]{6}";
        // String str = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![_]+$)[0-9A-Za-z_]{6,20}$";
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(parameter);
        return m.matches();
    }


    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     * <p>
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     * <p>
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array     the array of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, <code>null</code> if null array input
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     * <p>
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     * <p>
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array      the array of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @param startIndex the first index to start joining from.  It is
     *                   an error to pass in an end index past the end of the array
     * @param endIndex   the index to stop joining from (exclusive). It is
     *                   an error to pass in an end index past the end of the array
     * @return the joined String, <code>null</code> if null array input
     */
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return EMPTY;
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length())
                + separator.length());

        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 生成随机流水号
     *
     * @param business 业务
     * @param userId   用户ID
     * @return
     */
    public static String getSerialNumber(String business, String userId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo = business + formatter.format(new Date()).substring(2) + Math.round(Math.random() * 900 + 100) + userId;
        return orderNo;
    }

    /**
     * 密码规则
     * 6-20位数字、字母或下划线组合
     *
     * @param pwd
     * @return
     */
    public static boolean pwdRule(String pwd) {
        String str = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![_]+$)[0-9A-Za-z_]{6,20}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 4-20位字符( 汉字是话是10个汉字 ）
     *
     * @param nickName
     * @return
     */
    public static boolean nickNameRule(String nickName) {
        if (null == nickName) {
            return false;
        }
        int minlength = 4;
        int maxLength = 20;
        int length = nickName.length() + fileChin(nickName).length();
        if (length < minlength || length > maxLength) {
            return false;
        } else {
            return true;
        }

    }

    public static String fileChin(String chin) {
        if (null == chin) {
            return "";
        }
        String s = "[^(\\\\u4e00-\\\\u9fa5)]";
        chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        return chin;
    }
}
