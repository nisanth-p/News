package com.demo.thenews.model.db.api

import com.demo.thenews.model.util.UrlConstants
import com.demo.thenews.model.data.appscript.AppScriptResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService{
    @FormUrlEncoded
    @POST(UrlConstants.QUERY_API)
    suspend fun postRegister(@FieldMap options: Map<String, String> ): Response<AppScriptResponse>

    @FormUrlEncoded
    @POST(UrlConstants.QUERY_API)
    suspend fun postLogin(@FieldMap options: Map<String, String> ): Response<AppScriptResponse>

    @FormUrlEncoded
    @POST(UrlConstants.QUERY_API)
    suspend fun postForgetPassword(@FieldMap options: Map<String, String> ): Response<AppScriptResponse>

    @FormUrlEncoded
    @POST(UrlConstants.QUERY_API)
    suspend fun postProfileUpdate(@FieldMap options: Map<String, String> ): Response<AppScriptResponse>
}