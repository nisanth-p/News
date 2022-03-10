package com.demo.thenews.model.util

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

private const val TAG = "BasicFunction"

class BasicFunction @Inject constructor(context: Context?) {
    var bufferReader: BufferedReader? = null
    var builderContents: StringBuilder = StringBuilder()

    fun getFileFromAsset(fileName: String, context: Context?): String {
        try {
            if (context != null) {
                bufferReader = BufferedReader(InputStreamReader(context.assets.open(fileName)))
                // do reading, usually loop until end of file reading
                var mLine: String?
                while (bufferReader!!.readLine().also { mLine = it } != null) {
                    //process line
                    builderContents.append(mLine)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bufferReader?.close()
            } catch (e: IOException) {
            }
        }
        return builderContents.toString()
    }


    fun removeWhiteSpace(strings: String, function: (String) -> Unit): String {
        val pattern = "\\s+".toRegex()
        val res = strings.replace(pattern, "")
        function(res)
        return res
    }

    object Fun {
        fun spiltContainsComma(
            delimeter: Char,
            strings: String,
            function: (List<String>) -> Unit
        ): List<String> {
            val res = strings.split(delimeter).map { it.trim() }
            function(res)
            return res
        }
    }

    companion object Screens {
        private var scr = hashMapOf<String, Any>()
        fun getScreens(): HashMap<String, Any> {
/*            scr["splash_to_splash"] = R.id.action_splashFragment_self
            scr["splash_to_select_screen"] = R.id.action_splashFragment_to_selectScreenFragment
            scr["splash_to_add_rooms"] = R.id.action_splashFragment_to_addRoomFragment

            scr["select_screen_to_signin"] = R.id.action_selectScreenFragment_to_signInFragment
            scr["select_screen_to_signup"] = R.id.action_selectScreenFragment_to_signUpFragment


            scr["signin_to_add_rooms"] = R.id.action_signInFragment_to_addRoomFragment
            scr["signin_to_select_screen"] = R.id.action_signInFragment_to_selectScreenFragment
            scr["signin_to_otp_screen"] = R.id.action_signInFragment_to_otpFragment

            scr["otp_screen_to_add_rooms"] = R.id.action_otpFragment_to_addRoomFragment
            scr["otp_screen_to_signin"] = R.id.action_otpFragment_to_signInFragment

            scr["signup_to_add_rooms"] = R.id.action_signUpFragment_to_addRoomFragment
            scr["signup_to_select_screen"] = R.id.action_signUpFragment_to_selectScreenFragment  */

            scr["nill"] = 0
            scr["finish"] = -1
            return scr
        }
    }
}