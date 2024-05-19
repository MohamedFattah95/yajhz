package com.yajhz.di.module

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yajhz.BuildConfig
import com.yajhz.R
import com.yajhz.data.AppDataManager
import com.yajhz.data.DataManager
import com.yajhz.data.local.prefs.AppPreferencesHelper
import com.yajhz.data.local.prefs.PreferencesHelper
import com.yajhz.data.remote.ApiHelper
import com.yajhz.data.remote.AppApiHelper
import com.yajhz.data.remote.NetworkService
import com.yajhz.di.DatabaseInfo
import com.yajhz.di.PreferenceInfo
import com.yajhz.utils.AppConstants
import com.yajhz.utils.rx.AppSchedulerProvider
import com.yajhz.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

    @Provides
    @Singleton
    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @SuppressLint("LogNotTimber")
    @Provides
    fun provideClient(preferencesHelper: PreferencesHelper): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        Log.i("token", "" + preferencesHelper.getAccessToken())
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor { chain: Interceptor.Chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader(
                    "Authorization",
                    "Bearer " + preferencesHelper.getAccessToken()
                )

                requestBuilder.addHeader("accept", "*/*")
                requestBuilder.addHeader("Content-Type", "application/json")
                requestBuilder.addHeader("lang", preferencesHelper.getLanguage() ?: "en")
                chain.proceed(requestBuilder.build())
            }.build()
    }

    /**
     * provide Retrofit instances
     *
     * @param baseURL base url for api calling
     * @param client  OkHttp client
     * @return Retrofit instances
     */
    @Provides
    fun provideRetrofit(baseURL: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provide Api service
     * @return ApiService instances
     */
    @Provides
    fun provideApiService(preferencesHelper: PreferencesHelper): NetworkService {
        return provideRetrofit(
            BuildConfig.BASE_URL,
            provideClient(preferencesHelper)
        ).create<NetworkService>(
            NetworkService::class.java
        )
    }
}
