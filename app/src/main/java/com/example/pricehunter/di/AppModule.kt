package com.example.pricehunter.di

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.util.Base64
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.pricehunter.BuildConfig
import com.example.pricehunter.data.local.AppDatabase
import com.example.pricehunter.data.local.AppDatabase.Companion.DATABASE_NAME
import com.example.pricehunter.data.local.DaoServices
import com.example.pricehunter.data.prefs.AppPrefs
import com.example.pricehunter.data.remote.AppApiServices
import com.example.pricehunter.data.remote.AuthApiServices
import com.example.pricehunter.data.remote.AuthApiServices.Companion.BASE_URL
import com.example.pricehunter.data.remote.AuthApiServices.Companion.TIMEOUT
import com.example.pricehunter.data.remote.interceptors.AuthBasicInterceptor
import com.example.pricehunter.data.remote.interceptors.AuthBearerInterceptor
import com.example.pricehunter.domain.auth.AuthDomain
import com.example.pricehunter.domain.auth.IAuthDomain
import com.example.pricehunter.domain.finder.FinderDomain
import com.example.pricehunter.domain.finder.IFinderDomain
import com.example.pricehunter.domain.sample.ISampleDomain
import com.example.pricehunter.domain.sample.SampleDomain
import com.example.pricehunter.mock.MockAuthApiServices
import com.example.pricehunter.service.auth.AuthService
import com.example.pricehunter.service.auth.IAuthService
import com.example.pricehunter.service.finder.FinderService
import com.example.pricehunter.service.finder.IFinderService
import com.example.pricehunter.service.sample.ISampleService
import com.example.pricehunter.service.sample.SampleService
import com.example.pricehunter.view.auth.IAuthView
import com.example.pricehunter.view.launch.ILaunchView
import com.example.pricehunter.view.main.IMainView
import com.example.pricehunter.view.sample.ISampleView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 *
 * AppModule is a Dagger2 module that provides objects and dependencies
 * that are used throughout the application. These dependencies are scoped
 * to the entire application's lifecycle.
 *
 * @property authInterceptor an interceptor that adds the API key to requests
 * @property loggingInterceptor an interceptor that logs requests and responses
 * @property apiServices the API services used to make network requests
 * @property useMockApi a boolean value indicating whether to use mock API services or not
 * @property database the app's database instance
 * @property daoServices the Data Access Object services for the app's database
 */


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val clientId = "RafiulHy-PriceHun-PRD-78cce83ee-f49823da"
    private const val clientSecret = "PRD-8cce83ee510f-ad39-4248-8888-0de2"

    @Provides
    fun provideAuthCredentials(): String {
        return "Basic ${
            (Base64.encodeToString(
                "${clientId.trim()}:${clientSecret.trim()}".toByteArray(),
                Base64.NO_WRAP
            ).trim())
        }"
    }

    @Provides
    fun provideBasicAuthInterceptor(credentials: String): AuthBasicInterceptor =
        AuthBasicInterceptor(credentials)

    @Provides
    fun provideBearerAuthInterceptor(): AuthBearerInterceptor =
        AuthBearerInterceptor()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideAuthApiServices(
        authBasicInterceptor: AuthBasicInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        app: Application,
        @Named("useMockApi") useMockApi: Boolean
    ): AuthApiServices {
        return if (useMockApi) {
            MockAuthApiServices(app)
        } else {
            val client = OkHttpClient.Builder()
                .addInterceptor(authBasicInterceptor)
                .addInterceptor(loggingInterceptor)
                .callTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(AuthApiServices::class.java)
        }
    }

    @Provides
    @Singleton
    fun provideAppApiServices(
        authBearerInterceptor: AuthBearerInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        app: Application,
        @Named("useMockApi") useMockApi: Boolean
    ): AppApiServices {
        val client = OkHttpClient.Builder()
            .addInterceptor(authBearerInterceptor)
            .addInterceptor(loggingInterceptor)
            .callTimeout(AppApiServices.TIMEOUT, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(AppApiServices.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AppApiServices::class.java)
    }

    @Provides
    @Named("useMockApi")
    fun provideUseMockApi(): Boolean = BuildConfig.USE_MOCK_API

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    fun provideSharedPrefs(app: Application): SharedPreferences {
        val keyGenParamSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeys = MasterKeys.getOrCreate(keyGenParamSpec)
        return EncryptedSharedPreferences.create(
            BuildConfig.APPLICATION_ID,
            masterKeys,
            app.applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideDaoServices(database: AppDatabase): DaoServices {
        return database.getDaoServices()
    }

    @Provides
    @Singleton
    fun provideAppPrefs(sharedPrefs: SharedPreferences): AppPrefs {
        return AppPrefs(sharedPrefs)
    }

    @Provides
    @Singleton
    fun provideSampleDomain(
        authApiServices: AuthApiServices,
        daoServices: DaoServices
    ): ISampleDomain {
        return SampleDomain(authApiServices, daoServices)
    }

    @Provides
    @Singleton
    fun provideFinderDomain(appApiServices: AppApiServices): IFinderDomain {
        return FinderDomain(appApiServices)
    }

    @Provides
    @Singleton
    fun provideAuthDomain(authApiServices: AuthApiServices): IAuthDomain {
        return AuthDomain(authApiServices)
    }

    @Provides
    @Singleton
    fun provideSampleService(domain: ISampleDomain): ISampleService {
        return SampleService(domain)
    }

    @Provides
    @Singleton
    fun provideFinderService(domain: IFinderDomain): IFinderService {
        return FinderService(domain)
    }

    @Provides
    @Singleton
    fun provideAuthService(domain: IAuthDomain): IAuthService {
        return AuthService(domain)
    }
}

/**
 *
 * ViewModule is a Dagger2 module that provides objects and dependencies
 * that are scoped to the lifecycle of an Activity.
 */
@InstallIn(ActivityComponent::class)
@Module
object ViewModule {

    @Provides
    fun provideIMainView(activity: Activity): IMainView {
        return activity as IMainView
    }

    @Provides
    fun provideISampleView(activity: Activity): ISampleView {
        return activity as ISampleView
    }

    @Provides
    fun provideILaunchView(activity: Activity): ILaunchView {
        return activity as ILaunchView
    }

    @Provides
    fun provideIAuthView(activity: Activity): IAuthView {
        return activity as IAuthView
    }

}