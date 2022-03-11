package com.demo.thenews.di

import android.content.Context
import com.demo.thenews.model.db.api.ApiHelper
import com.demo.thenews.model.db.api.ApiHelperImpl
import com.demo.thenews.model.util.BasicFunction
import com.demo.thenews.view.base.FireRealTimeDB
import com.demo.thenews.view.base.FireStoreDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.demo.thenews.model.db.api.ApiService
import com.demo.thenews.model.db.remote.RemoteLoginDataSource
import com.demo.thenews.model.db.repository.CommonRepository
import com.demo.thenews.model.db.source.CommonDataSource
import com.demo.thenews.model.util.UrlConstants
import com.demo.thenews.model.db.remote.firebase.firestore.FireStoreImpl
import com.demo.thenews.model.db.remote.firebase.realtimedatabase.FireRealTimeDBImpl
import com.demo.thenews.model.util.impl.IPref
import com.demo.thenews.model.util.impl.IRes
import com.demo.thenews.model.util.impl.PrefImpl
import com.demo.thenews.model.util.impl.ResImpl
import com.demo.thenews.model.db.repository.DefaultRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

// TODO: 10/3/2022 developed by Nisath
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideBasicFunction(@ApplicationContext context: Context): BasicFunction {
        return BasicFunction(context)
    }
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideJsonObj(): JSONObject = JSONObject()

    @Singleton
    @Provides
    fun provideJsonArray(): JSONArray = JSONArray()


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource


    @Singleton
    @RemoteDataSource
    @Provides
    fun provideRemoteLoginDataSource(
        apiService: ApiService,
        ioDispatcher: CoroutineDispatcher
    ): CommonDataSource = RemoteLoginDataSource(apiService, ioDispatcher)

/*    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java, "news.db"
        ).build()
    }*/


    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(UrlConstants.BASE_NEWS_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

/*    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()*/


    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Singleton // Provide always the same instance
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        // Run this code when providing an instance of CoroutineScope
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }


    @Singleton
    @Provides
    fun provideDefaultRepository(

        @AppModule.RemoteDataSource remote: CommonDataSource,
        apiHelper: ApiHelper,
        ioDispatcher: CoroutineDispatcher
    ): CommonRepository {
        return DefaultRepository( remote, apiHelper, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    fun provideEmptyList(): List<String> = emptyList()


    @Singleton
    @Provides
    fun provideFireStoreDatabase(): FirebaseFirestore = FirebaseFirestore.getInstance()
    @Singleton
    @Provides
    fun provideFireStoreImpl(db: FirebaseFirestore): /*FireStoreDatabase is Base ABS class class*/ FireStoreDatabase =
        FireStoreImpl(db)

    @Singleton
    @Provides
    fun provideFireDatabaseInstance(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Singleton
    @Provides
    fun provideFireDatabaseRef(): DatabaseReference = Firebase.database.reference
    @Singleton
    @Provides
    fun provideFireRealTimeDBImpl(db: DatabaseReference):
            /*FireRealTimeDB is Base ABS class class*/FireRealTimeDB = FireRealTimeDBImpl(db)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourceWrapperModule {
    @Singleton
    @Binds
    abstract fun bindResImpl(resImpl: ResImpl): IRes

    @Singleton
    @Binds
    abstract fun bindPrefImpl(prefImpl: PrefImpl): IPref
}