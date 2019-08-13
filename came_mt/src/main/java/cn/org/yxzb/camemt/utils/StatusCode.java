package cn.org.yxzb.camemt.utils;

public class StatusCode {
    public static final int OK=20000;//成功
    public static final int ERROR =20001;//失败
    public static final int LOGINERROR =20002;//用户名或密码错误
    public static final int ACCESSERROR =20003;//权限不足
    public static final int REMOTEERROR =20004;//远程调用失败
    public static final int REPERROR =20005;//重复操作
    public static final int PARAMETERERROR =20006;//参数错误
    public static final int EXHIBITOR_LOGOUT =-2;//参展者登出
    public static final int ADMIN_USER_LOGOUT =-1;//管理员登出
}