package com.lawrence.oss.validation.resource;

/**
 * Created by z_wu on 2014/12/5.
 */
public class Messages {
    public static String email_error = "'{propertyName}'表示的邮件地址无效.";
    public static String equal_error = "'{propertyName}' 应该等于 '{propertyValue}'.";
    public static String exact_length_error = "'{propertyName}' 的长度必须是 {maxLength}, 你已输入 {totalLength} 个字符.";
    public static String exclusivebetween_error = "'{propertyName}' 应该介于 {from} 到 {to} 之间(exclusive). 你输入了：{value}.";
    public static String greaterthan_error = "'{propertyName}' 必须大于 '{comparisonValue}'.";
    public static String greaterthanorequal_error = "'{propertyName}' 必须大于或等于 '{comparisonValue}'.";
    public static String inclusivebetween_error ="'{propertyName}' 必须在 {from} 到 {to} 之间. 你输入了：{value}.";
    public static String length_error = "'{propertyName}' 的长度必须在 {minLength} 到 {maxLength} 之间.您输入了 {totalLength} 个字符.";
    public static String lessthan_error = "'{propertyName}' 必须小于 '{comparisonValue}'.";
    public static String lessthanorequal_error = "'{propertyName}' 必须小于或等于 '{comparisonValue}'.";
    public static String notempty_error = "'{propertyName}' 不能为空字符串.";
    public static String notequal_error = "'{propertyName}' 应不等于 '{propertyValue}'.";
    public static String notnull_error = "'{propertyName}' 必须不为空.";
    public static String predicate_error = "'{propertyName}' 不符合指定条件.";
    public static String regex_error = "'{propertyName}' 格式不正确.";
}
