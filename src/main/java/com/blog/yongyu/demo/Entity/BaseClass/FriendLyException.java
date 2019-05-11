/**
 * created by   liuyoyu
 * Date:2019/5/11
 **/
package com.blog.yongyu.demo.Entity.BaseClass;

/**
 * 自定义友好异常
 * 在service层中，对control层抛出异常，control层接收异常后显示给前台
 */
public class FriendLyException extends Exception {
    private Integer errorCode;

    public FriendLyException() {
    }

    public FriendLyException(String message) {
        super(message);
    }

    public FriendLyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FriendLyException(Throwable cause) {
        super(cause);
    }

    public FriendLyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public FriendLyException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}