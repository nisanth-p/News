package com.demo.thenews.model.db.repository
import com.demo.thenews.model.db.api.ApiHelper
import com.demo.thenews.model.data.appscript.AppScriptResponse
import com.demo.thenews.model.db.source.CommonDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private const val TAG = "xxxDefaultRepository"

class DefaultRepository (
    private val local: CommonDataSource,
    private val remote: CommonDataSource,
    private val apiHelper: ApiHelper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CommonRepository {

    override suspend fun postRegister(options: Map<String, String>): Response<AppScriptResponse> {
        return withContext(ioDispatcher) { apiHelper.postRegister(options) }

    }

    override suspend fun postLogin(options: Map<String, String>): Response<AppScriptResponse> {
        return withContext(ioDispatcher) {
            apiHelper.postLogin(options) }
    }

    override suspend fun postForgetPassword(options: Map<String, String>): Response<AppScriptResponse> {
        return withContext(ioDispatcher) { apiHelper.postForgetPassword(options) }
    }

    override suspend fun postValidateOtp(options: Map<String, String>): Response<AppScriptResponse> {
        return withContext(ioDispatcher) { apiHelper.postValidateOtp(options) }
    }

    override suspend fun postProfileUpdate(options: Map<String, String>): Response<AppScriptResponse> {
      return  withContext(ioDispatcher) { apiHelper.postProfileUpdate(options) }
    }


}