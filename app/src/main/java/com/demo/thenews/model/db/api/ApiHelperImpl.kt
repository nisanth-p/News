package com.demo.thenews.model.db.api

import com.demo.thenews.model.data.appscript.AppScriptResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {

    override suspend fun postRegister(options: Map<String, String>): Response<AppScriptResponse> =
        apiService.postRegister(options)

    override suspend fun postLogin(options: Map<String, String>): Response<AppScriptResponse> =
        apiService.postLogin(options)

    override suspend fun postForgetPassword(options: Map<String, String>): Response<AppScriptResponse> =
        apiService.postForgetPassword(options)

    override suspend fun postValidateOtp(options: Map<String, String>): Response<AppScriptResponse> =
        apiService.postForgetPassword(options)

    override suspend fun postProfileUpdate(options: Map<String, String>): Response<AppScriptResponse> =
        apiService.postProfileUpdate(options)

}