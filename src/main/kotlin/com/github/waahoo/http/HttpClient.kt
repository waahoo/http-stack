package com.github.waahoo.http

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieStore

const val UserAgent =
  "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36"

fun makeClient(
  cookieStore: CookieStore? = null, logLevel: HttpLoggingInterceptor.Level = NONE,
  userAgent: String = UserAgent
): OkHttpClient {
  return OkHttpClient.Builder().let {
    if (cookieStore != null) {
      val cookieMgr = CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL)
      CookieHandler.setDefault(cookieMgr)
      it.cookieJar(JavaNetCookieJar(cookieMgr))
    }
    it.addNetworkInterceptor { chain ->
      val req = chain.request()
      chain.proceed(
        req.newBuilder()
          .header("User-Agent", userAgent)
          .build()
      )
    }
    it.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
    it.build()
  }

}

lateinit var client: OkHttpClient

fun initClient(
  cookieStore: CookieStore? = null, logLevel: HttpLoggingInterceptor.Level = NONE,
  userAgent: String = UserAgent
) {
  client = makeClient(cookieStore, logLevel, userAgent)
}

fun closeClient() {
  if (::client.isInitialized)
    client.close()
}