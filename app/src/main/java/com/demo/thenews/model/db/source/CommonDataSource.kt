package com.demo.thenews.model.db.source

import com.demo.thenews.model.data.appscript.AppScriptResponse

import retrofit2.Response

interface CommonDataSource {

    suspend fun postRegister(options: Map<String, String>): Response<AppScriptResponse>
    suspend fun postValidateOtp(options: Map<String, String> ): Response<AppScriptResponse>
}