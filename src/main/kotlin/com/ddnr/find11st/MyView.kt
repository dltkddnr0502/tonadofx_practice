package com.ddnr.find11st

import com.ddnr.find11st.api.API11stManager
import com.ddnr.find11st.model.CategoryResponse
import com.github.thomasnield.rxkotlinfx.actionEvents
import com.github.thomasnield.rxkotlinfx.events
import com.github.thomasnield.rxkotlinfx.toObservable
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.ResourceObserver
import io.reactivex.rxjavafx.observables.JavaFxObservable
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.time.withTimeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tornadofx.View
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.experimental.RestrictsSuspension
import kotlin.system.measureTimeMillis

//import kotlinx.coroutines.experimental.javafx.JavaFx as UI

/**
 * https://developers.skplanetx.com/apidoc/kor/11st/product/#doc1431
 * */
typealias Foo<T> = (T) -> Boolean
typealias MyHandler<T1, T2, T3, R> = (T1, T2, T3) -> R
typealias MyHandler2<T1, T2, T3, R> = (T1, T2, T3) -> R

class MyView : View() {

    data class D(
            var a: String,
            var b: Int,
            var c: Boolean = true
    ) {

        init {
            println("DDDDD")
        }

        var isEmpty: Boolean = a.length == 0
            get() {
                return a.length == 0
            }

    }


    annotation class Fancy

    @RestrictsSuspension //to prevent the user from adding new ways of suspending a coroutine.
    @Fancy inner class AC(a: String) {
        init {
            println("Init $a")
        }

        constructor(a: String, b: Int) : this(a) {
            println("2nd Cons $a, $b")
        }

        fun abc() {

        }

        var a = a
                //can not be private*
            get() {
                println("cons get, field: $field")
                val r = if (field.length > 0) field else 0
                return field
            }
                //get() = field
                //can internal, private, public
            public set(value) {
                println("cons set, value: $value")
                field = value
            }
    }

    sealed class StateEnum {
        abstract fun direction(x: Int, y: Int): Pair<Int, Int>

        object IDLE : StateEnum(){  //SINGLE INSTANCE!!!
            override fun direction(x: Int, y: Int) = x to x + y
        }

        class BUSY : StateEnum(){ //CAN class!!!! Multiple INSTANCE!!
            override fun direction(x: Int, y: Int): Pair<Int, Int> {
                return x to x + y
            }
        }
    }

    //val stateEnum: StateEnum = StateEnum.IDLE()

    enum class StatusEnum {
        IDLE { //SINGLE INSTANCE!!!
            override fun direction(x: Int, y: Int) =  x to x + y
        },
        BUSY { //SINGLE INSTANCE!!!
            override fun direction(x: Int, y: Int): Pair<Int, Int> {
                return x to x + y
            }
        };
        //DO NOT FORGET ';'
        abstract fun direction(x: Int, y: Int): Pair<Int, Int>
    }
    fun abdsfdf(state: StateEnum) = when (state) {
        is StateEnum.IDLE -> ""
        is StateEnum.BUSY -> ""
    }


    /*inner */class ImplHighOrderFunction(nn: String="", var ss: String="IDLE") {
        init{
            println("$nn / $ss")
        }

        val aaaa = nn

        fun abc() {
            println("${aaaa} / ${this.ss}")
        }

        fun <T, R> lollipopAndAbove(
                a: T, b: T, s: String,
                body: (T) -> R
        ): R {
            println(" ")
            return body(a)
        }
    }

    fun <T, R> lollipopAndAbove(
            a: T, b: T, s: String,
            body: (T) -> Unit
    ): (T) -> R {
        //return body(a)
        body(a)
        return { T ->
            println("TTTT ${T}")
            "AA $T AA" as R
        }
    }

    class Box<T>(t: T) {
        private var v: T = t

        private val finalV: String by lazy {
            ""//initA()
        }



        fun abc(): T {
            return v
        }

        fun abcIn(tt: T) {

            v = tt
        }
    }


    /////
    //https://kotlinlang.org/docs/reference/generics.html
    val bt: BoxT = BoxT()

    class BoxT : Source<String> {
        override fun nextT(): String {
            return ""
        }
    }

    fun demo2(strs: String): String {
        return ""
    }

    fun demo(strs: Source<String>) {
        val objects: Source<Any> = strs // This is OK, since T is an out-parameter
    }

    interface Source<out T> {
        fun nextT(): T
    }

    interface Comparable<in T> {
        operator fun compareTo(other: T): Int
    }

    fun demo(x: Comparable<Number>) {
        x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
        // Thus, we can assign x to a variable of type Comparable<Double>
        val y: Comparable<Double> = x // OK!
    }
    /////

    override val root = VBox()
    val aS: Box<String> = Box("ABC")
    val dd: D = D("a,", 1)
    val ac: AC = AC("a")
    val ac2: AC = AC("a", 2)
    val disposalble: Disposables? = null
    //val aDIs: Box<Disposables> = Box(CompositeDisposable() as Disposables)
    val implHighOrderFunction: ImplHighOrderFunction = ImplHighOrderFunction()

    //@Suppress("NOTHING_TO_INLINE")
    /* In case you want only some of the lambdas passed to an inline function to be inlined,
    you can mark some of your function parameters with the noinline modifier: */
    inline fun <T, R> abc(noinline block: T.() -> Unit, block2: (T) -> R) {

    }

    inline fun abc(observable: Observable<ProductSearchResponse>, categoryResponse: Observable<CategoryResponse>) {

    }

    private /*suspend*/ fun AC.foo() {

    }

    fun abcd() {

    }

    val disposalbles = CompositeDisposable()


    fun bar(foo: Foo<Int>) = foo(42)
    fun bar2(foo2: MyHandler<Int, String, Int, Int>): Int = foo2(1, "My", 3)
    fun bar3(foo3: MyHandler<Int, Int, Int, Boolean>): Boolean = foo3(1, 2, 3)
    fun bar4(foo3: MyHandler2<Int, Int, Int, String>): String = foo3(1, 2, 3)
    fun bar5(name: String) {
        println(name.length)
    }

    fun bar6(name: String?): String? {
        println(name?.length)
        return null
    }

    fun bar7(name: String?): String {
        fun lo(subName: String?): String {
            return subName?.toUpperCase()?:""
        }
        println(name?.length)
        return lo(name)
    }

    val initPlug: Int.(Int) -> Int = { b ->
        println("initPlug $this + $b")
        this + b
    }

    init {
        val ret1 = lollipopAndAbove<Int, String>(
                initPlug(1, 2), 2, "HighOrderFunctionInterface3"
        ) { a ->
            println("HighOrderFunctionInterface1: $a")
            //"HighOrderFunctionInterface: $a"
        }

        println("ret1: ${ret1(123)}")

        val ar2 = { i: Int -> i + 1 }
        implHighOrderFunction.lollipopAndAbove(
                ar2(1), 2, "HighOrderFunctionInterface"
        ) { a ->
            println("HighOrderFunctionInterface2: $a")
        }

        val ar = { i: Int -> i + 1 }
        ar(1)

        2.initPlug(2)
        initPlug(2, 2)
        initPlug.invoke(2, 2)

        var f: (Int) -> Boolean = {
            it > 0
        }
        bar(f)
        var ff: (Int, String, Int) -> Int = { i, s, a ->
            i + s.length + a
        }
        println("${bar2(ff)}")

        var fff: (Int, Int, Int) -> Boolean = { i, ii, iii ->
            i + ii + iii > 1
        }
        var ffff: (Int, Int, Int) -> String = { i, ii, iii ->
            (i + ii + iii).toString()
        }
        println("${bar3(fff)}")
        println("${bar4(ffff)}")
        val nN = ""

        val a: String? = nN as? String
        val b: String? = nN as? String?

        listOf(1, 3, 4, 5, 6, 7).reversed()
        listOf(1, 3, 4, 5, 6, 7)
        mutableListOf(1, 2, 3).add(4)
        1.rangeTo(100)
        println(100.downTo(5).reversed().step(2).step(2))

        val nll: String? = null
        bar5(nN)
        //bar5(nll) //err
        //bar5(bar6(null)) //err
        bar5(bar7(null))
        bar6(null)

        """ .'''*((.. ${'$'}eq """

        println("Spliet: ${"12.345.6a".split(".")}")

        ac.a
        ac.a = "1"

        val lE = listOf("a", "b").toTypedArray()
        println("LE ${listOf("lE: ", *lE)}")

        //val (num, name) = mapOf(1 to "one")
        val (num, name) = 1 to "one"
        println("$num, $name")

        dd.component1()
        dd.component2()
        dd.component3()


        var button: Button = Button("start")
        button.actionEvents()
                .subscribe { it -> println(it) }


        val di = JavaFxObservable.actionEventsOf(button)
                .map { ae -> 1 }
                .doOnComplete {
                    object : Runnable {
                        override fun run() {
                            println("Completed!")
                        }
                    }
                }
                //.take(5)
                .scan(0, { x, y -> x + y })
                .subscribe(System.out::println)
        disposalbles.add(di)
        //disposalbles.dispose()

        button.setOnMouseClicked { event ->
            println("event: $event")
            val switch = 1
            when (switch) {
                1 -> {
                    Flowable.just(API11stManager.queryCategories())
                            .subscribeOn(Schedulers.io())
                            .subscribe({ next ->
                                println("next: $next")

                                next.enqueue(object : Callback<CategoryResponse> {
                                    override fun onResponse(call: Call<CategoryResponse>?, response: Response<CategoryResponse>?) {
                                        println("response: $response")
                                        println("response.request(): ${response?.body()?.request}")
                                        println("response.body(): ${response?.body().toString()}")

                                        if (response!!.isSuccessful) {
                                            var category = response?.body()?.children
                                            println(category.toString())
                                            category?.apply {
                                                this.forEach {
                                                    println(it)

                                                    API11stManager.getCategory(category = Integer.parseInt(it.categoryCode), option = "SubCategory").enqueue(
                                                            object: Callback<CategoryResponse>{
                                                                override fun onFailure(call: Call<CategoryResponse>?, t: Throwable?) {

                                                                }

                                                                override fun onResponse(call: Call<CategoryResponse>?, response: Response<CategoryResponse>?) {
                                                                    response?.body()?.apply {
                                                                        this?.subCategory?.apply {
                                                                            println("sub: $this")
                                                                        }
                                                                        this?.children?.apply {
                                                                            println("child: $this")
                                                                        }
                                                                        this?.products?.apply {
                                                                            println("prod: $this")
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                    )

                                                    API11stManager.getCategory(category = Integer.parseInt(it.categoryCode), option = "Products").enqueue(
                                                            object: Callback<CategoryResponse>{
                                                                override fun onFailure(call: Call<CategoryResponse>?, t: Throwable?) {

                                                                }

                                                                override fun onResponse(call: Call<CategoryResponse>?, response: Response<CategoryResponse>?) {
                                                                    response?.body()?.apply {
                                                                        this?.subCategory?.apply{
                                                                            Flowable.fromArray(this)
                                                                                    .subscribeOn(Schedulers.io())
                                                                                    .subscribe { println("sub: $it") }
                                                                        }

                                                                        this?.children?.apply {
                                                                            Flowable.fromArray(this)
                                                                                    .subscribeOn(Schedulers.io())
                                                                                    .subscribe { println("child: $it") }
                                                                        }
                                                                        this?.products?.product?.apply{
                                                                        Flowable.fromArray(this)
                                                                                .subscribeOn(Schedulers.io())
                                                                                .subscribe { println("prod: $it") }
                                                                    }
                                                                    }
                                                                }
                                                            }
                                                    )
                                                }
                                            }
                                            /*var products = response?.body()?.products
                                            println(products.toString())
                                            products?.let{ product ->
                                                println(product)
                                            }*/
                                        }
                                    }

                                    override fun onFailure(call: Call<CategoryResponse>?, t: Throwable?) {
                                        println("onFailure: call: $call, Throwable: $t")
                                    }
                                })
                            }, { error ->
                                println("error: $error")
                            }, { println("Succeed") })
                }
                2 -> {
                    Flowable.just(API11stManager.getCategory(category = 1001296, option = "Children")) //Children, SubCategory, Products
                            .subscribeOn(Schedulers.io())
                            .subscribe({ next ->
                                println("next: $next")

                                next.enqueue(object : Callback<CategoryResponse> {
                                    override fun onResponse(call: Call<CategoryResponse>?, response: Response<CategoryResponse>?) {
                                        println("response: $response")
                                        println("response.request(): ${response?.body()?.request}")
                                        println("response.body(): ${response?.body()}")

                                        if (response!!.isSuccessful) {
                                            var category = response?.body()?.category
                                            println(category.toString())
                                            category?.let { it ->
                                                println(it)
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<CategoryResponse>?, t: Throwable?) {
                                        println("onFailure: call: $call, Throwable: $t")
                                    }
                                })
                            }, { error ->
                                println("error: $error")
                            }, { println("Succeed") })
                }
                3 -> {
                    async {
                        withTimeout(
                                java.time.Duration.ofSeconds(1000, 50),
                                {
                                    repeat(1) {
                                        println("Timeout ${it}")
                                        //delay(500)
                                    }
                                }
                        )

                        withTimeout(Duration.ofMillis(25000)) {
                            repeat(3) {
                                println("$it")
                            }
                        }
                    }

                    val coV1 = async(CoroutineName("v1")) {
                        delay(500)
                        println("coroutine Name: ${Thread.currentThread().name}")
                        252
                    }

                    val coV2 = async(CoroutineName("v2")) {
                        delay(500)
                        println("coroutine Name: ${Thread.currentThread().name}")
                        "Su"
                    }

                    async {
                        println("coV1 ${coV1}")
                        println("coV2 ${coV2[coV2.key].toString()}")
                        println("coV ${coV1.await()} , ${coV2.await()}")
                    }

                    Flowable.just(API11stManager.search("asus"))
                            .subscribeOn(Schedulers.io())
                            .subscribe({ next ->
                                println("next: $next")

                                next.enqueue(object : Callback<ProductSearchResponse> {
                                    override fun onResponse(call: Call<ProductSearchResponse>?, response: Response<ProductSearchResponse>?) {
                                        println("onResponse: call: $call, response: $response")
                                        println("request: ${response?.body()?.request.toString()}")
                                        println("response.body(): ${response?.body()}")

                                        if (response!!.isSuccessful) {
                                            var list = response?.body()?.products?.product
                                            println("list size: ${if (list!!.size > 0) {
                                                list!!.size
                                            } else {
                                                0
                                            }}")
                                            list?.let {
                                                it.forEach { item ->
                                                    println(item.toString())
                                                }
                                            }
                                        }

//                                        println("flatMap")
                                        response?.body()?.products?.product?.apply {
                                            toFlowable()
//                                                    .filter { Integer.parseInt(it.buySatisfy)>90 }
//                                                    .distinct{ it.sellerNick }
//                                                    .skip(1)
//                                                    .take(3)
                                                    .subscribeOn(Schedulers.io())
                                                    .flatMap {
                                                        it.run {
                                                            //                                                            println("flatMap run ")
                                                            Observable
                                                                    .just<Triple<String?, String?, String?>>(
                                                                            Triple(prodName, prodPrice, prodCode),
                                                                            Triple(prodName + "2", prodPrice, prodCode)
                                                                    )
                                                                    .delay(Random(System.currentTimeMillis()).nextLong() / 500, TimeUnit.MILLISECONDS)
                                                                    .toFlowable(BackpressureStrategy.BUFFER)
                                                        }
                                                    }
                                                    .subscribe { prod ->
                                                        //                                                        println(prod.toString())
                                                    }
                                        }

//                                        println("concatMap")
                                        response?.body()?.products?.product?.apply {
                                            toFlowable()
//                                                    .filter { Integer.parseInt(it.buySatisfy)>90 }
//                                                    .distinct{ it.sellerNick }
//                                                    .skip(1)
//                                                    .take(3)
                                                    .subscribeOn(Schedulers.io())
                                                    .concatMap {
                                                        it.run {
                                                            //                                                            println("concatMap run")
                                                            Observable
                                                                    .just<Triple<String?, String?, String?>>(
                                                                            Triple(prodName, prodPrice, prodCode),
                                                                            Triple(prodName + "2", prodPrice, prodCode)
                                                                    )
                                                                    .delay(Random(System.currentTimeMillis()).nextLong() / 500, TimeUnit.MILLISECONDS)
                                                                    .toFlowable(BackpressureStrategy.BUFFER)
                                                        }
                                                    }
                                                    .subscribe { prod ->
                                                        //                                                        println(prod.toString())
                                                    }
                                        }

//                                        println("switchMap")


                                        println("run blocking")
                                        runBlocking {
                                            println("in run blocking")
                                            delay(1000)
                                            println("finish run blocking")
                                        }

                                        println("before 1 launch : ${Thread.currentThread()}")
                                        launch {
                                            println("in 1 launch : ${Thread.currentThread()}")
                                        }

                                        async {
                                            launch {
                                                println(" in launch")
                                                delay(1000)
                                                println("finish launch")
                                            }?.join()

                                            println("joined")
                                        }

                                        async {
                                            val time = measureTimeMillis {
                                                val a = async/*(CoroutineName("V1"))*/ {
                                                    repeat(10) {
                                                        delay(500)
                                                        println("a")
                                                    }
                                                }

                                                val b = async/*(CoroutineName("V2"))*/ {
                                                    repeat(10) {
                                                        delay(500)
                                                        println("b")
                                                    }
                                                }

                                                println("before joined")
                                                a.join()
                                                b.join()

//                                                a.await()
//                                                b.await()
                                                println("joineddddddddd")
                                            }
                                            println("time: $time")
                                        }

                                        runBlocking {
                                            val time = measureTimeMillis {
                                                val a = async(CoroutineName("V1"), CoroutineStart.ATOMIC) {
                                                    repeat(10) {
                                                        delay(500)
                                                        println("v1 a")
                                                    }
                                                }

                                                val b = async(CoroutineName("V2"), CoroutineStart.DEFAULT) {
                                                    repeat(10) {
                                                        delay(500)
                                                        println("v2 b")
                                                    }
                                                }

                                                println("before 2 joined")
                                                a.join()
                                                b.join()

//                                                a.await()
//                                                b.await()
                                                println("join2 eddddddddd")
                                            }
                                            println("time2: $time")
                                        }

                                        launch {
                                            val time = measureTimeMillis {
                                                val a = async/*(CoroutineName("V1"))*/ {
                                                    repeat(10) {
                                                        delay(500)
                                                        println("a")
                                                    }
                                                }

                                                val b = async/*(CoroutineName("V2"))*/ {
                                                    repeat(10) {
                                                        delay(500)
                                                        println("b")
                                                    }
                                                }

                                                println("before 3 joined")
                                                a.join()
                                                b.join()

//                                                a.await()
//                                                b.await()
                                                println("join3 eddddddddd")
                                            }
                                            println("time3: $time")
                                        }

                                        async {
                                            println(" in async ")
                                            delay(1000)
                                            println("finish async ")
                                        }

                                        println("before 2 launch : ${Thread.currentThread()}")
                                        launch {
                                            println("in 2 launch : ${Thread.currentThread()}")
                                        }


                                        async {
                                            delay(500)

                                            thread {
                                                println("thread block: ${Thread.currentThread()}")
                                                //doSuspending("aaa") //suspend fun, compile error!
                                                //letPractice()
                                            }

                                            println("1 ${Thread.currentThread()}")
                                            Thread.sleep(1000)
                                            println("2 ${Thread.currentThread()}")
                                            Thread.sleep(1000)
                                            "Finished"
                                            launch {
                                                println("launche: ${Thread.currentThread().name}")
                                            }
                                        }?.let {
                                            //println("runn thread:  ${Thread.currentThread()}")
                                            async {
                                                println("async ${it.await()}")
                                            }
                                        }


                                        response?.body()?.products?.product?.apply {
                                            toFlowable()
//                                                    .filter { Integer.parseInt(it.buySatisfy)>90 }
//                                                    .distinct{ it.sellerNick }
//                                                    .skip(1)
//                                                    .take(3)
                                                    .subscribeOn(Schedulers.io())
                                                    .switchMap {
                                                        it.run {
                                                            //                                                            println("switchMap run")
                                                            Observable
                                                                    .just<Triple<String?, String?, String?>>(
                                                                            Triple(prodName, prodPrice, prodCode),
                                                                            Triple(prodName + "2", prodPrice, prodCode)
                                                                    )
                                                                    .delay(Random(System.currentTimeMillis()).nextLong() / 1000, TimeUnit.MILLISECONDS)
                                                                    .toFlowable(BackpressureStrategy.BUFFER)
                                                        }
                                                    }
                                                    .subscribe { prod ->
                                                        println(prod.toString())
                                                    }
                                        }
                                    }

                                    override fun onFailure(call: Call<ProductSearchResponse>?, t: Throwable?) {
                                        println("onFailure: call: $call, Throwable: $t")
                                    }
                                })
                            }, { error ->
                                println("error: $error")
                            }, { println("Succeed") })

                    //doSuspending("Not from async") //Compile error

                    async {
                        println("async suspending: ${Thread.currentThread()}")
                        doSuspending("Suspedddd")
                    }
                }
                4 -> {
                    val p = Observable.just(API11stManager.search("asus"))
                    val c = Observable.just(API11stManager.queryCategories())
                    val obj = Observable.create<String> { it ->
                        it.onNext("a")
                        it.onNext("b")
                        it.onNext("c")
                        it.onComplete()
                        Thread.sleep(1000)
                    }


                    Completable.fromRunnable({ println("Runnable") })
                            .doOnSubscribe { disposalble -> println("subscribed comp: $disposalble") }
                            .doOnTerminate { println("terminated") }
                            .subscribeOn(Schedulers.io())
                            .doOnEvent { e -> println("event: $e") }
                            .onErrorResumeNext { t: Throwable ->
                                println(t)
                                CompletableSource { }
                            }.subscribe()


                    var disposalble = obj.subscribe(
                            { it ->
                                println("$it")
                            },
                            { it ->
                                println("$it")
                            },
                            {
                                println("succeed")
                            }
                    )
                    disposalble.dispose()

                    disposalble = Observable.just("just")
                            .delay(1, TimeUnit.SECONDS)
                            .doOnSubscribe { disposalble -> println("disposable $disposalble") }
                            .subscribeWith(object : DisposableObserver<String>() {
                                override fun onError(e: Throwable) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onNext(t: String) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onComplete() {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onStart() {
                                    super.onStart()
                                }
                            })

                    disposalble.dispose()
                }
                5 -> {
                    var call: Call<ProductSearchResponse> = API11stManager.search(keyword = "notebook", page = 100)
                    Observable.fromArray(call)
                            .doOnSubscribe { disposalble -> println("Subscribed: $disposalble") }
                            .doOnTerminate { println("Terminated") }
                            .doOnError { err -> println("err: $err") }
                            .subscribeOn(Schedulers.io())
                            .toFlowable(BackpressureStrategy.BUFFER)
                            .subscribe(
                                    { next ->
                                        println("next: $next this: ${this}")
                                        next.enqueue(object : Callback<ProductSearchResponse> {
                                            override fun onFailure(call: Call<ProductSearchResponse>?, t: Throwable?) {

                                            }

                                            override fun onResponse(call: Call<ProductSearchResponse>?, response: Response<ProductSearchResponse>?) {
                                                val r = response?.body()?.products?.product?.let {
                                                    it.forEach { prod ->
                                                        println("prod: $prod")
                                                    }
                                                }
                                                println("r: $r")

                                                val receiver = response?.body()?.products?.product?.apply {
                                                    forEach { prod ->
                                                        println("prod: $prod")
                                                    }
                                                }

                                                response?.body()?.products?.product?.run {

                                                }

                                                println("receiver: $receiver")
                                            }
                                        })
                                    }
                            )
                    val buffSize: Int = Flowable.bufferSize()
                    println("buffSize: $buffSize")
                }
                6 -> {
                    letPractice()
                }
                7 -> {
                    Observable.just(1)
                            .subscribeOn(Schedulers.io())
                            //.subscribe(::println)
                            .subscribe({ println("$it") }, { it.printStackTrace() }, { println("onComplete!") })

                    var su = object : ResourceObserver<Int>() {
                        override fun onComplete() {
//                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onError(e: Throwable) {
//                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onNext(t: Int) {
//                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                    }

                    var dispo = Observable.just(1, 2, 3, 4)
                            .observeOn(JavaFxScheduler.platform())
                            //.retryWhen((observable) -> {})
                            .subscribe(su)

//                            .subscribe { next-> println{"$next"} }
//                    dispo.dispose()

                    Single.create<String> { subscriber ->
                        subscriber.onSuccess("Succeed")
                    }.doOnSuccess {
                        println("Suceedd 12124124")
                    }.subscribe()

                    Observable.timer(1000, TimeUnit.SECONDS)
                            .repeat(1)
                            .subscribe { println("$it") }

                    val source = Observable.just(
                            //"HighOrderFunctionInterface", "BB", "CCC", "D", "EE", "FFF"
                            "Alpha", "Beta", "Gamma", "Delta", "Epsilon"
                            //"123/52/6345", "23421/534", "758/2341/74932"
                    )

                    val defer = Observable.defer {
                        println("createing defer")
                        Observable.create<String> { subscriber ->
                            println("createing observable")
                            subscriber.onNext("1")
//                            Thread.sleep(1000)
                            subscriber.onNext("2")
//                            Thread.sleep(1000)
                            subscriber.onNext("3")
                            subscriber.onComplete()
                        }
                    }
                    defer.subscribe { println("defer sub 1 $it") }
//                    Thread.sleep(1000)
                    defer.subscribe { println("defer sub 2 $it") }

                    val fj = Flowable.create<String>({ subscriber ->
                        println("createing Flowable")
                        subscriber.onNext("1")
//                        Thread.sleep(1000)
                        subscriber.onNext("2")
//                        Thread.sleep(1000)
                        subscriber.onNext("3")
                        subscriber.onComplete()
                    }, BackpressureStrategy.BUFFER)
                    fj.subscribe { println("fj sub 1 $it") }
//                    Thread.sleep(1000)
                    fj.subscribe { println("fj sub 2 $it") }

                    val af = Flowable.just("1", "2", "3", "4")
                    af.subscribe { println("afj sub 1 $it") }
                    af.subscribe { println("afj sub 2 $it") }


                    println("defer: $defer")

                    Observable.interval(1, TimeUnit.SECONDS)
                            .toFlowable(BackpressureStrategy.BUFFER)
                            .observeOn(JavaFxScheduler.platform())
                    //.subscribe(::println)

                    Observable.merge(
                            Observable.just("HighOrderFunctionInterface", "BB"),
                            Observable.just("C", "DD")
                            //Observable.just(1, 2, 3)
                    ).subscribe {
                        println("merge : $it")
                    }


                    Observable.combineLatest(
                            Observable.just("ID", "ID1", "ID2"),
                            Observable.just("PW", "PW2", "PW3", "PW4"),
                            BiFunction<String, String, String> { t1, t2 ->
                                println("$t1, $t2")
                                t1 + "/" + t2
                            }
                    ).subscribe {
                        println("combine lateset $it")
                        /**
                         * combine lateset ID2/PW
                         * combine lateset ID2/PW2
                         * combine lateset ID2/PW3
                         * combine lateset ID2/PW4
                         * */
                    }


                    Observables.combineLatest(
                            Observable.just("1", "2"),
                            Observable.just("HighOrderFunctionInterface", "BUSY", "C")
                    ) { a, b ->
                        println("ab")
                        a + " | " + b
                    }.subscribe {
                        println("combine lateset $it")
                        /*}.apply {
                            disposalbles?.let { it.add(this) }*/
                    }.addTo(disposalbles)

                    Observable.just("")
                            .subscribe {
                                println(it)
                            }

                    Observable.just("")
                            .subscribe(object : Consumer<String> {
                                override fun accept(t: String?) {
                                    println(t)
                                }
                            })

                    Observable.just("")
                            .subscribe(object : Observer<String> {
                                override fun onSubscribe(d: Disposable) {
                                }

                                override fun onError(e: Throwable) {
                                }

                                override fun onComplete() {
                                }

                                override fun onNext(t: String) {
                                }
                            })

                    Flowables.zip(
                            Flowable.just("HighOrderFunctionInterface", "BB"),
                            Flowable.just(1, 2)
                    ) { a, b ->
                        println("$a, $b")
                        a.length + b
                    }.subscribe {
                        println("Zip: $it")
                    }.addTo(disposalbles)

                    Flowable.zip(
                            Flowable.just("HighOrderFunctionInterface", "BB"),
                            Flowable.just(1, 2),
                            BiFunction<String, Int, Int> { a, b ->
                                println("$a, $b")
                                a.length + b
                            }
                    ).subscribe {
                        println("Zip: $it")
                    }

                    Observable.zip(
                            Observable.just("HighOrderFunctionInterface", "BB"),
                            Observable.just(1, 2, 3),
                            BiFunction<String, Int, Int> { a, b ->
                                println("$a, $b")
                                a.length + b
                            }
                    ).subscribe {
                        println("Zip: $it")
                    }

                    var f = Flowable.create<Int>({ sub ->
                        println("Flowable, Create")
                        sub.onNext(1)
                        sub.onNext(2)
                    }, BackpressureStrategy.BUFFER)
                    //.subscribe { it -> println("$it") }
                    println("F: $f")

                    Observable.defer {
                        var list = listOf("a", "b", "c")
                        list.toObservable()
                    }.subscribe {
                        println("Defer: $it")
                    }

                    Observable.timer(5, TimeUnit.SECONDS)
                            .subscribe {
                                println("timer observable")
                                defer.subscribe { it -> println("$it") }
                            }

                    Observable
                            .fromCallable({ println("ABC Callable") })


                    //.just("ABC")


                    source
                            //.skip(1)
                            .map { it.length }
                    //.take(1)
//                            .takeUntil { it >= 2 }
//                            .filter { it >= 2 }
//                            .distinct()
//                            .toList().subscribe { it->println(it) }
//                            .count().subscribe { it -> println(it) }
                    //.reduce(0, { c, n -> c + n }).subscribe { it -> println(it) }
//                            .scan(0, { c, n -> c + n }).subscribe { it -> println(it) }
                    //.flatMap{ Observable.fromArray(it.split('/')) }
//                            .subscribe(::println)


                    Observable.just(1)
                            .subscribeOn(Schedulers.io())
                            .doOnSubscribe { disposalble -> println("subscribed: $disposalble, THRD: ${Thread.currentThread()}") }
                            .observeOn(JavaFxScheduler.platform())
                            .doOnTerminate { println("Terminating..., THRD: ${Thread.currentThread()}") }
                            .observeOn(Schedulers.io())
                            .doOnComplete { println("Completing..., THRD: ${Thread.currentThread()}") }
                            .observeOn(JavaFxScheduler.platform())
                            .doOnEach { println("Each..., THRD: ${Thread.currentThread()}") }
                            .observeOn(Schedulers.io())
                            .doOnNext { next -> println("next: $next, THRD: ${Thread.currentThread()}") }
                            .observeOn(JavaFxScheduler.platform())
                            .toFlowable(BackpressureStrategy.BUFFER)
                            .subscribe {
                                println("netxt: $it, THRD: ${Thread.currentThread()}")
                            }


                    Observable.just("1", "2", "3")
                            .switchMap {
                                Observable.just(it + "X")
                                //.delay(1500, TimeUnit.MILLISECONDS)
                            }
                            .subscribe {
                                println("switchMap: $it")
                                //3X
                            }
                    Observable
                            .concat(
                                    Observable.just("HighOrderFunctionInterface", "BUSY", "C"),
                                    Observable.just(1, 2, 3)
                            ).subscribe {
                        println("concat: $it")
                    }
                }
                9 -> {
                    println("8")
                    Flowable.just("key")
                            .subscribeOn(Schedulers.io())
                            .observeOn(JavaFxScheduler.platform())
                            .subscribe { println("$it") }

                    var userId: BehaviorSubject<String> = BehaviorSubject.create()
                    userId.distinctUntilChanged()
                            .observeOn(Schedulers.io())
                            .subscribe {
                                println("$it")
                                //Save user id to preference
                            }


                }
                else -> {

                }
            }
        }

        root.children += button
        root.children += Label("Label: ")


        var listView = ListView<String>()
        (0..9).asSequence().map { it.toString() }.forEach { listView.items.add(it) }
        listView
                .events(javafx.scene.input.MouseEvent.MOUSE_CLICKED)
//                .events(javafx.scene.input.KeyEvent.KEY_RELEASED)
                //.map{it}
                //.filter{it.matches(Regex("[0-9]]"))}
                .subscribe { next ->
                    println("next: $next")
                    //listView.selectionModel.select(next)
                }

        root.children += listView

        val editText = TextField()
        editText.textProperty()
                .toObservable()
                .subscribe { next -> println("$next") }
        /*.addListener { _observable, oldValue, newValue ->
    run {
        println("old: $oldValue, new: $newValue")
    }
}*/
        root.children += editText
    }


    fun adfd() {
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .subscribe { next ->
                    println("${Thread.currentThread()}")
                }
    }

    /**
     * fun <T> T.apply(block: T.() -> Unit): T
     *
     * fun <T> T.also(block: T.() -> Unit): T
     *
     * fun <T, R> T.let(block: (T) -> R): R
     *
     * fun <T, R> with(receiver: T, block: T.() -> R): R
     *
     * * fun <T, R> takeIf(receiver: T, block: T.() -> R): R
     *
     * * fun <T, R> takeUnless(receiver: T, block: T.() -> R): R
     *
     * fun <T, R> T.run(block: T.() -> R): R
     * fun <R> run(block: () -> R): R
     *
     * * * fun <T, R> repeat(times: Int, action: (Int)->Unit)
     * */

    var letPractice: Int? = 42

    fun letPractice() {
        var a: String? = "ABC"
        val b: Int = a?.length ?: 10

        repeat(4/*zero base, 4time*/, { println("repeat: $it") })
        var retdd = a.takeIf { "ABC" == a }?.let {
            //a="CBD"
            a
        }
        println("$retdd")

        println("$retdd")

        a = a?.run { "ccc" }
        println("a?.run: $a")
        var rr = run {
            3
        }

        a = a?.apply {
            println("$a, ${this.length}")
            a = "apply" //return T
        }?.also {
            println("${it}") //also doesn't take this unlikely 'apply'
            a = "also" //return T
        }?.let { it ->
            println("let it ${it.length}")
            it
        }?.run {
            println("this ${this.length}")
            this
        } ?: run {
            "abc"
        } ?: with(a, {
            println("${this?.length}")
            this
        })?.takeIf {
            it == "abc" //return T
        }?.takeUnless {
            it == "abc" //return T
        }

        repeat(1, { it ->
            a = a?.apply {
                println("$a, ${this.length}")
                a = "apply" //return T
            }?.also {
                println("${it}") //also doesn't take this unlikely 'apply'
                a = "also" //return T
            }?.let { it ->
                println("let it ${it.length}")
                it
            }?.run {
                println("this ${this.length}")
                this
            } ?: run {
                "abc"
            } ?: with(a, {
                println("${this?.length}")
                this
            })?.takeIf {
                it == "abc" //return T
            }?.takeUnless {
                it == "abc" //return T
            }
            println("at ${it}: $a")
        })


        println("length: $rr")

        var rea = a?.run {
            length
        }
        println("length: $rea")

        with(ProductSearchResponse(), { println("${products}") })

        a = a?.let { "aaa" } ?: run { "bbb" }
        println("a?.let & run: $a")

        a?.let { println("$it") }.also { println("$it") }.run { println("") }
        letPractice = null
        var ret = letPractice?.let {
            println(it)
            //return letPracticeSub()
            "Let when it is null"
        } ?: run {
            println("Run when it is null")
            "Run when it is null"
        }
        println("ret: $ret")

        var ret2 = letPractice?.let { it } ?: return
        println("ret2: $ret2. Not reach if it is null by return statement")
    }

    fun letPracticeSub() {
        return Unit
    }

    fun returnInt(): Int {
        return 1
    }

    fun returnInt2(): Int = 1


    private suspend fun doSuspending(a: String): Int {
        println("doSuspending: ${Thread.currentThread()}")
        return a.length
    }
}