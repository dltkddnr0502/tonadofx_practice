package com.ddnr.find11st.model

import com.ddnr.find11st.Products
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "CategoryResponse", strict = false)
data class CategoryResponse(
        @set: Element(name = "Request", required = true)
        @get: Element(name = "Request", required = true)
        var request: Request? = null,

        @set:ElementList(inline = true, name = "RootCategory", required = false)
        @get:ElementList(inline = true, name = "RootCategory", required = false)
        var rootCategory: Category? = null,

        @set:ElementList(inline = true, name = "Category", required = false)
        @get:ElementList(inline = true, name = "Category", required = false)
        var category: List<Category>? = null,

        @set:Element(name = "Products", required = false)
        @get:Element(name = "Products", required = false)
        var products: Products? = null,

        @set:ElementList(name = "Children", required = false)
        @get:ElementList(name = "Children", required = false)
        var children: List<Category>? = null,

        @set:ElementList(name = "SubCategory", required = false)
        @get:ElementList(name = "SubCategory", required = false)
        var subCategory: List<Category>? = null
)

@Root(name = "RootCategory", strict = false)
data class RootCategory(
        @set:Element(name = "CategoryName", required = false, data = true)
        @get:Element(name = "CategoryName", required = false, data = true)
        var categoryName: String? = null,

        @set:Element(name = "CategoryCode", required = false)
        @get:Element(name = "CategoryCode", required = false)
        var categoryCode: String? = null
)

@Root(name = "Category", strict = false)
data class Category(
        @set:Element(name = "CategoryName", required = false, data = true)
        @get:Element(name = "CategoryName", required = false, data = true)
        var categoryName: String? = null,

        @set:Element(name = "CategoryCode", required = false)
        @get:Element(name = "CategoryCode", required = false)
        var categoryCode: String? = null,

        @set:Element(name = "TotalCount", required = false)
        @get:Element(name = "TotalCount", required = false)
        var totalCount: String? = null,

        @set:Element(name = "CategoryImage", required = false, data = true)
        @get:Element(name = "CategoryImage", required = false, data = true)
        var categoryImage: String? = null,

        @set:Element(name = "Children", required = false)
        @get:Element(name = "Children", required = false)
        private var children: List<Category>? = null,

        @set:Element(name = "SubCategory", required = false)
        @get:Element(name = "SubCategory", required = false)
        private var subCategory: List<Category>? = null
)