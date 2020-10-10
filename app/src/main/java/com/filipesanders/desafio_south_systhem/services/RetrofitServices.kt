package com.filipesanders.desafio_south_systhem.services

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitServices {

    val BASE_URL_DEV = "http://5f5a8f24d44d640016169133.mockapi.io/api/"
    val BASE_URL_PROD = ""

    @PublishedApi
    internal var retrofit: Retrofit? = null

    fun initRetrofit(application: Application) {

        Log.d("RETROFIT", "Retrofit initialized!")

        if (retrofit != null)
            return

        //Configura o cliente Http
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            ).build()


        //Cria a instancia do Retrofit
        retrofit = Retrofit.Builder()
            .baseUrl(getBaseURL())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Cria e retorna uma instancia de um servico do tipo T
     */

    inline fun <reified T> getService(): T {
        if (retrofit == null) {
            throw NullPointerException("Retrofit not initialized! Call RetrofitServices.initialize() on the application class.")
        }
        return retrofit!!.create(T::class.java)
    }

    fun getBaseURL() = BASE_URL_DEV //if (BuildConfig.DEBUG) BASE_URL_DEV else BASE_URL_PROD

}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ServiceResponse<T> {

    return withContext(dispatcher) {
        try {
            val result = apiCall.invoke()
            ServiceResponse.Success(result)
        } catch (throwable: Throwable) {
            Log.e("SafeApiCall", throwable.localizedMessage!!)
            when (throwable) {
                is HttpException -> {
                    try {
                        val code = throwable.code()
                        throwable.response()?.errorBody()?.string()?.let {
                            Gson().fromJson(it, ServiceResponse.Error.GenericError::class.java)
                        } ?: ServiceResponse.Error.GenericError(code, throwable.message())
                    } catch (jsonException: JsonSyntaxException) {
                        ServiceResponse.Error.GenericError(null, jsonException.message)
                    }
                }
                is IOException -> ServiceResponse.Error.NetworkError
                else -> {
                    ServiceResponse.Error.GenericError(null, throwable.message)
                }
            }
        }
    }
}



