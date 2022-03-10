package com.demo.thenews.model.data.appscript

import com.google.gson.annotations.SerializedName


data class AppScriptResponse(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("success")
    val success: Boolean? = false,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("user")
    val user: List<User?>? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null
)

data class User(
    val any: Any? = null
)

data class OtpSave(
    val any: Any? = null
)

data class OtpEndTime(
    val any: Any? = null
)

data class DataItem(

    @field:SerializedName("resmessage", alternate = ["user_resmessage"])
    val resmessage: String? = null,

    @field:SerializedName("otp_save", alternate = ["user_otp_save"])
    val otpSave: OtpSave? = null,

    @field:SerializedName("reqtype", alternate = ["user_reqtype"])
    val reqtype: String? = null,

    @field:SerializedName("error", alternate = ["user_error"])
    val error: String? = null,

    @field:SerializedName("otp_end_time", alternate = ["user_otp_end_time"])
    val otpEndTime: OtpEndTime? = null,
    @field:SerializedName("isLogin", alternate = ["user_is_login"])
    val isLogin: Boolean?,
    @field:SerializedName("user_adminid", alternate = ["user_admin_id"])
    val userAdminid: String? = null,
    @field:SerializedName("userPhone", alternate = ["user_phone"])
    val userPhone: String? = null,
    @field:SerializedName("userMailId", alternate = ["user_mail"])
    val userMailId: String? = null,
    @field:SerializedName("userName", alternate = ["user_name"])
    val userName: String? = null

)