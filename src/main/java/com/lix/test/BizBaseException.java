package com.lix.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 业务异常的简单封装，支持对异常信息的格式化。根据业务需求对构造器、方法等补全，这里只提供几个简单示例
 * @author lix
 * @Date 2020/4/8
 */
public class BizBaseException extends RuntimeException {


    private static final String REGX_PLACEHOLDER = "\\{\\}";
    private static final String COMMON_ERROR_CODE = "9999";
    /**
     * 匹配正则占位符{}
     */
    private static final Pattern SIGN = Pattern.compile(REGX_PLACEHOLDER);
    /**
     * 错误代码
     */
    private String code;
    /**
     * 错误描述
     */
    private String desc;
    /**
     * 补位参数
     */
    private transient Object[] args;
    /**
     * 异常对象
     */
    private transient Throwable throwable;


    public static BizBaseException format(String message, Object... args){
        return new BizBaseException(COMMON_ERROR_CODE, message, args);
    }

    public static BizBaseException create(String code, String message, Object... args){
        return new BizBaseException(code, message, args);
    }


    public BizBaseException(String code, String desc, Object... args) {
        super(formatMessage(desc, args));
        this.args = args;
        this.desc = desc;
        this.code = code;
    }

    public BizBaseException(Throwable cause, String code, String desc, Object... args) {
        super(formatMessage(desc, args), cause);
        this.desc = desc;
        this.args = args;
        this.code = code;
    }

    public static String formatMessage(String message, Object[] args) {
        if (message == null || message.length() < 1) return "unknown exception";
        if (args == null || args.length < 1) {
            return message.contains("{}") ? message.replaceAll(REGX_PLACEHOLDER, "") : message;
        }
        try {
            Matcher matcher = SIGN.matcher(message);
            StringBuffer stringBuffer = new StringBuffer(64);
            int i = 0;
            while (matcher.find()) {
                matcher.appendReplacement(stringBuffer, args[i++].toString());
            }
            matcher.appendTail(stringBuffer);
            return stringBuffer.toString();
        } catch (Exception ex) {
            return message + ",errorArgs:" + args;
        }
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getDesc() {
        return desc;
    }

    public static void main(String[] args) {
        throw BizBaseException.format("出现了异常，我需要提醒1是{}，然后提醒2是{}", "参数1", "参数2");
    }
}
