package com.ddnr.find11st.api

import com.ddnr.find11st.ProductSearchResponse
import com.ddnr.find11st.model.CategoryResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


object API11stManager {
    const val KEY: String = "3b33784aac5e554d447887c957bd8b26"
    const val PROD_SEARCH: String = "ProductSearch"
    const val PROD_INFO: String = "ProductInfo"
    const val PROD_IMG: String = "ProductImage"
    const val CATEGORY: String = "CategoryInfo"

    private val api11st: API11st by lazy {
        println("API11st Created!")
        create()
    }

    private val api11stPlanet: API11stPlanet by lazy {
        println("API11stPlanet Created!")
        createPlanet()
    }

    private fun create(): API11st {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                //GSON
                //.addConverterFactory(GsonConverterFactory.create(gson))

                //XML
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
                        Persister (AnnotationStrategy ())// important part!
                ))

                //Adapter
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                .baseUrl("http://openapi.11st.co.kr/openapi/")
                .client(OkHttpClient())
                .build()
        return retrofit.create(API11st::class.java)
    }

    private fun createPlanet(): API11stPlanet {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                //GSON
                //.addConverterFactory(GsonConverterFactory.create(gson))

                //XML
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
                        Persister (AnnotationStrategy ())// important part!
                ))

                //Adapter
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                .baseUrl("http://apis.skplanetx.com/11st/v2/common/")
                .client(OkHttpClient())
                .build()
        return retrofit.create(API11stPlanet::class.java)
    }

    fun search(keyword: String="notebook", page: Int = 10): Call<ProductSearchResponse> {
        return api11st.search(keyword = keyword, pageSize = page.toString())
    }

    fun getCategory(category: Int=1,
                    pageSize: Int = 10,
                    pageNum: Int = 10,
                    sortCd: String="CP",
                    option: String="Products" //Children, SubCategory, Products
                    ): Call<CategoryResponse> {
        return api11st.getCateogory(
                categoryCode = category.toString(),
                pageSize=pageSize.toString(),
                pageNum=pageNum.toString(),
                sortCd=sortCd,
                option = option
        )
    }

    fun queryCategories(): Call<CategoryResponse> {
        return api11stPlanet.queryCateogories()
    }
}