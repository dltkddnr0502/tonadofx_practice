package com.ddnr.find11st.api

import com.ddnr.find11st.model.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

//https://futurestud.io/tutorials/retrofit-add-custom-request-header
interface API11stPlanet {
    @Headers(
            "x-skpop-userId: dltkddnr0502",
            "Accept-Language: ko_KR",
            "Accept: application/xml",
            "appKey: 3251b9a2-0976-364b-b59d-551ff62b8321"
    )
    @GET("categories")
    fun queryCateogories(@Query("version") option: String = "1"): Call<CategoryResponse>
}