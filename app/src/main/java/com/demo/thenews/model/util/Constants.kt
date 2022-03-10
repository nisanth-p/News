package com.demo.thenews.model.util

import android.content.Context
import android.content.pm.ApplicationInfo

/**SharedPref */
object SharedPrefKey{
    const val APP_LANGUAGE:String = "APP_LANGUAGE"
    const val APP_WEBLINK:String = "APP_WEBLINK"
    const val APP_USERID:String = "APP_USERID"
    const val APP_USERMAIL:String = "APP_USERMAIL"
    const val APP_USERLOGIN:String = "APP_USERLOGIN"
    const val APP_USERNAME:String = "APP_USERNAME"
    const val APP_USERPHONE:String = "APP_USERPHONE"
    const val APP_SELECTED_ROOM:String = "APP_SELECTED_ROOM"
    const val APP_SELECTED_DEVICES:String = "APP_SELECTED_DEVICES"
    const val APP_AVAILABLE_ROOMS:String = "APP_AVAILABLE_ROOMS"
    const val APP_AVAILABLE_DEVICES:String = "APP_AVAILABLE_DEVICES"
    const val APP_RION_WORKFLOW:String = "APP_RION_WORKFLOW"
    const val APP_CONFIGURE_DEVICES:String = "APP_CONFIGURE_DEVICES"
    const val APP_SWITCH_STATUS:String = "APP_SWITCH_STATUS"
    const val APP_AVAILABLE_ROOMS_DOWNLOAD:String = "APP_AVAILABLE_ROOMS_DOWNLOAD"



}

object AppConstants{
    /* events used by view models */
    const val EVENT_SEND_OTP = 1
    const val EVENT_VALIDATE_OTP = 2
    const val EVENT_RESEND_OTP = 3
    const val EVENT_CHANGE_NUMBER = 4

    /* events used by otp manager */
    const val EVENT_SEND_SUCCESS = 11
    const val EVENT_SEND_FAILED = 12
    const val EVENT_VALIDATE_SUCCESS = 13
    const val EVENT_VALIDATE_FAILED = 14
}
object ReqMapKey{
    const val name = "name"
    const val mail = "mail"
    const val userid = "userid"
    const val phone = "phone"
    const val password = "password"
    const val otp = "otp"
}

object DataFlow{


}

object SelectedRooms{
    //todo(create selected rooms json)
    const val selectRoomData ="selectRoomData"
    const val fragName = "fragName"
    const val favorite = "favorite"
    const val isAdded = "isAdded"
    const val isConfigured = "isConfigured"
    const val isDeviceIdEdit = "isDeviceIdEdit"
    const val isDeviceNameEdit = "isDeviceNameEdit"
    const val hardwareId = "hardwareId"
    const val deviceId = "deviceId"
    const val wayOfSwitch = "wayOfSwitch"
    const val roomName ="roomName"
    const val count ="count"
    const val deviceCount ="deviceCount"
    const val roomCount ="roomCount"
    const val data ="data"
    const val rooms ="rooms"
    const val roomSelected ="roomSelected"
    const val deviceSelected ="deviceSelected"

    // TODO: 21-12-2021 rooms adapter
    const val deviceName ="deviceName"
    const val deviceItem = "DeviceItem"
    const val roomsItem = "RoomsItem"
    const val selected = "Selected"
    const val selectedItem = "SelectedItem"

    // TODO: 21-12-2021 switch status
    const val deviceSwitchOneIsOn ="deviceSwitchOneIsOn"
    const val deviceSwitchTwoIsOn = "deviceSwitchTwoIsOn"
    const val deviceSwitchThreeIsOn = "deviceSwitchThreeIsOn"
    const val deviceSwitchFourIsOn = "deviceSwitchFourIsOn"
}

object UrlConstants{
    const val BASE_APPSCRIPT_URL = "https://script.google.com/"
    const val BASE_NEWS_URL = "https://newsapi.org/"
    private const val NEWS_API_KEY = "apiKey=4d7f32fcdaa440c8957002be5a281290"
    const val QUERY_API = "v2/everything?q=bitcoin&$NEWS_API_KEY"
}
 object PostmanQueryParams{

    /**
     * user account param key
     */

    const val QUERY_KEY_NAME="name"
    const val QUERY_KEY_PHONE="phone"
    const val QUERY_KEY_MAIL="mail"
    const val QUERY_KEY_DEVICE_NAME="device_name"
    const val QUERY_KEY_DEVICE_ID="device_id"
    const val QUERY_KEY_PASSWORD="password"
    const val QUERY_KEY_OTP="otp"
    const val QUERY_KEY_NEW_PASSWORD="new_password"
    const val QUERY_KEY_RE_PASSWORD="re_password"
    const val QUERY_KEY_ACTION_RUN="run"
    const val QUERY_VALUE_POST="post"
    const val QUERY_VALUE_GET="get"

    /**
     * action for which operation
     */

    const val QUERY_KEY_ACTION="action"
    const val QUERY_VALUE_ACTION_INSERT="insert"
    const val QUERY_VALUE_ACTION_UPDATE="update"
    const val QUERY_VALUE_ACTION_DELETE="delete"
    const val QUERY_VALUE_ACTION_SEARCH="search"
    const val QUERY_VALUE_ACTION_FORGET="forget"
    const val QUERY_VALUE_ACTION_LOGIN="login"
    const val QUERY_VALUE_ACTION_LOGOUT="logout"
    const val QUERY_VALUE_ACTION_OTP_VALIDATION="otpValidation"
    const val QUERY_VALUE_ACTION_READ="read"
    const val QUERY_VALUE_ACTION_LANGUAGE="language"
    const val QUERY_VALUE_ACTION_NEW_PASSWORD="new_password"
    const val QUERY_VALUE_ACTION_LOGIN_TIME="login_time"
    const val QUERY_VALUE_ACTION_CONFIGURE="configure"

    /**
     * search operation
     */
    const val QUERY_KEY_SEARCH_OPTION="search_option"
    const val QUERY_VALUE_SEARCH_NAME="search_name"
    const val QUERY_VALUE_SEARCH_PHONE="search_phone"
    const val QUERY_VALUE_SEARCH_MAIL="search_mail"
    const val QUERY_VALUE_SEARCH_DEVICE_NAME="search_device_name"
    const val QUERY_VALUE_SEARCH_DEVICE_ID="search_device_id"
    const val QUERY_VALUE_SEARCH_USER_PROFILE="search_user_profile"

}

object DebugMode{
    fun isDebug(context: Context) = (0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)
}