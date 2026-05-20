package ec.edu.puce.githubclient.services

import ec.edu.puce.githubclient.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
// IMPORTANTE: Estos son los imports que te hacían falta
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // CORREGIDO: Faltaban los ":" en el https://
    private const val BASE_URL = "https://api.github.com/"

    private val loggin = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor = loggin)
        .addInterceptor { chain ->
            val token = BuildConfig.GITHUB_TOKEN
            println("Token: $token")

            val request = chain.request().newBuilder()
                // CORREGIDO: Se escribe "Authorization" (con h)
                .addHeader(name = "Authorization", value = "Bearer $token")
                .build()

            chain.proceed(request)
        }
        .build()

    val apiService: ApiService by lazy {
        // Ahora Retrofit y GsonConverterFactory ya no saldrán en rojo
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}