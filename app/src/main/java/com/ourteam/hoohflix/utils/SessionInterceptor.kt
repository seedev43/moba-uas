package com.ourteam.hoohflix.utils

import okhttp3.Interceptor
import okhttp3.Response

class SessionInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // Menambahkan cookie sesi ke header permintaan
        sessionManager.getSessionCookie()?.let { cookie ->
            requestBuilder.addHeader("Cookie", cookie)
        }

        val response = chain.proceed(requestBuilder.build())

        // Menyimpan cookie dari server jika ada
        response.headers("Set-Cookie").find { it.startsWith("sessionid") }?.let { sessionCookie ->
            sessionManager.saveSessionCookie(sessionCookie)
        }

        return response
    }
}
