package com.test.learnkotlin.http.exception

import retrofit2.HttpException
import retrofit2.Response

/**
 * Http请求异常
 */
class HttpException : RuntimeException {

    var code: Int
        private set
    override var message: String?
        private set

    @Transient
    var response: Response<*>? = null
        private set

    constructor(code: Int, throwable: Throwable) : super(throwable) {
        this.code = code
        this.message = throwable.message
    }

    constructor(e: HttpException) : super(e) {
        this.code = e.code()
        this.message = e.message()
        this.response = e.response()
    }

    /**
     * 是否为任务取消
     */
    private fun isJobCancelled(): Boolean = ExceptionCode.JOB_CANCELLED == code

    /**
     * 是否为网络错误
     */
    private fun isNetworkError(): Boolean = ExceptionCode.NETWORD_ERROR == code

    /**
     * 是否为网关错误
     */
    private fun isBadGetway(): Boolean = ExceptionCode.NETWORK_BAD_GATEWAY == code

    /**
     * 是否为网络不佳
     */
    private fun isNetworkPoor(): Boolean =
            listOf(408, ExceptionCode.CONNECT_TIMEOUT, ExceptionCode.SOCKET_TIMEOUT).contains(code)

    /**
     * 根据异常信息弹出Toast
     */
    fun showToast() {
        if (isJobCancelled() || isNetworkError()) { // 任务取消或网络错误不进行处理
            return
        }
//        var message: String? = StringUtils.getString(R.string.http_exception_tips)
//        if (code != 0) {
//            message = StringUtils.format("%s[%d]", message, code)
//        }
//        ToastUtil.showToast(message)
    }

    /**
     * 根据异常类型显示不同的布局
     */
//    fun handleStatusLayout(statusEvent: SingleLiveEvent<Int>) {
//        if (isNetworkError()) {
//            statusEvent.value = StatusLayout.NETWORK_ERROR
//        } else if (isNetworkPoor() || isBadGetway()) {
//            statusEvent.value = StatusLayout.NETWORK_POOR
//        } else {
//            statusEvent.value = StatusLayout.ERROR
//        }
//    }

}