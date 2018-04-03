package com.ddnr.find11st.api

import com.ddnr.find11st.ProductSearchResponse
import com.ddnr.find11st.model.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API11st {
    //http://openapi.11st.co.kr/openapi/OpenApiGuide.tmall?commonGuideNo=1

    @GET("OpenApiService.tmall")
    fun search(
            @Query("key") key: String= API11stManager.KEY,
            @Query("apiCode") apiCode: String= API11stManager.PROD_SEARCH,
            @Query("keyword") keyword: String,
            @Query("pageSize") pageSize: String="10"
    ): Call<ProductSearchResponse>

    @GET("OpenApiService.tmall")
    fun getProduct(
            @Query("key") key: String= API11stManager.KEY,
            @Query("apiCode") apiCode: String= API11stManager.PROD_INFO,
            @Query("productCode") productCode: String
    )

    @GET("OpenApiService.tmall")
    fun searchImage(
            @Query("key") key: String= API11stManager.KEY,
            @Query("apiCode") apiCode: String= API11stManager.PROD_IMG,
            @Query("productCode") productCode: String
    )

    @GET("OpenApiService.tmall")
    fun getCateogory(
            @Query("key") key: String= API11stManager.KEY,
            @Query("apiCode") apiCode: String= API11stManager.CATEGORY,
            @Query("categoryCode") categoryCode: String,
            @Query("pageNum") pageNum: String,
            @Query("pageSize") pageSize: String,
            @Query("sortCd") sortCd: String
            , @Query("option") option: String
    ): Call<CategoryResponse>
}