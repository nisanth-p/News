package com.demo.thenews.model.db.remote

import com.demo.thenews.model.db.source.CommonDataSource
import com.demo.thenews.model.data.appscript.AppScriptResponse
import com.demo.thenews.model.db.api.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "xxxRemoteLoginDataSource"

class RemoteLoginDataSource internal
@Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CommonDataSource {




    override suspend fun postRegister(options: Map<String, String>): Response<AppScriptResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun postValidateOtp(options: Map<String, String>): Response<AppScriptResponse> {
        TODO("Not yet implemented")
    }


}