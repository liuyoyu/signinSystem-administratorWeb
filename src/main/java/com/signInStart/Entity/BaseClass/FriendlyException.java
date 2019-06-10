/**
 * created by   liuyoyu
 * Date:2019/5/11
 **/
package com.signInStart.Entity.BaseClass;

/**
 * 自定义友好异常
 * 在service层中，对control层抛出异常，control层接收异常后显示给前台
 */
public class FriendlyException extends Exception {
    private Integer errorCode=1;

    private String methodName = "";

    public FriendlyException() {
    }

    public FriendlyException(String message) {
        super(message);
    }

    public FriendlyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FriendlyException(Throwable cause) {
        super(cause);
    }

    public FriendlyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public FriendlyException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public FriendlyException(String message, String methodName) {
        super(message);
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}