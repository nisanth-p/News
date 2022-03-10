package com.demo.thenews.model.db.api

import com.demo.thenews.model.data.appscript.AppScriptResponse
import retrofit2.Response

interface ApiHelper {
//    suspend fun getWorkflow(): Response<Workflow>
    suspend fun postRegister(options: Map<String, String>): Response<AppScriptResponse>
    suspend fun postLogin(options: Map<String, String> ): Response<AppScriptResponse>
    suspend fun postForgetPassword(options: Map<String, String> ): Response<AppScriptResponse>
    suspend fun postValidateOtp(options: Map<String, String> ): Response<AppScriptResponse>
    suspend fun postProfileUpdate(options: Map<String, String>): Response<AppScriptResponse>
}