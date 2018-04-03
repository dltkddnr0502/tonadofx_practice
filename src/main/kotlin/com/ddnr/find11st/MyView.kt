package com.ddnr.find11st

import com.ddnr.find11st.api.API11stManager
import com.ddnr.find11st.model.CategoryResponse
import com.github.thomasnield.rxkotlinfx.actionEvents
import com.github.thomasnield.rxkotlinfx.events
import com.github.thomasnield.rxkotlinfx.toObservable
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.ResourceObserver
import io.reactivex.rxjavafx.observables.JavaFxObservable
import io.reactivex.rxjavafx.observers.JavaFxObserver
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.schedulers.Schedulers
import javafx.event.EventType
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.input.TouchEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tornadofx.*
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.util.concurrent.TimeUnit
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

/**
 * https://developers.skplanetx.com/apidoc/kor/11st/product/#doc1431
 * */
class MyView : View() {
    override val root = VBox()

    val disposalble: Disposables? = null
    fun abc(observable: Observable<ProductSearchResponse>, categoryResponse: Observable<CategoryResponse>) {

    }

    fun abcd() {

    }

    val disposalbles = CompositeDisposable()

    init {
        var button: Button = Button("start")
        button.actionEvents()
                .subscribe { it -> println(it) }


        val di = JavaFxObservable.actionEventsOf(button)
                .map{ae -> 1}
                .doOnComplete { object: Runnable{
                    override fun run() {
                        println("Completed!")
                    }
                } }
                //.take(5)
                .scan(0, {x,y -> x+y})
                .subscribe(System.out::println)
        disposalbles.add(di)
        //disposalbles.dispose()

        button.setOnMouseClicked { event ->
            println("event: $event")
            val switch = 7
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
                                            var category = response?.body()?.category
                                            println(category.toString())
                                            category?.let { it ->
                                                println(it)
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
                                    }

                                    override fun onFailure(call: Call<ProductSearchResponse>?, t: Throwable?) {
                                        println("onFailure: call: $call, Throwable: $t")
                                    }
                                })
                            }, { error ->
                                println("error: $error")
                            }, { println("Succeed") })
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
                            .subscribe(su)
//                            .subscribe { next-> println{"$next"} }
//                    dispo.dispose()

                    val source = Observable.just(
                            //"A", "BB", "CCC", "D", "EE", "FFF"
                               "Alpha", "Beta", "Gamma", "Delta", "Epsilon"
                                //"123/52/6345", "23421/534", "758/2341/74932"
                    )
                    source
                            //.skip(1)
//                            .map { it.length }
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
                            .subscribe { next ->
                                println("netxt: $next, THRD: ${Thread.currentThread()}")
                            }
                }
                else -> {

                }
            }
        }

        root.children += button
        root.children += Label("Label: ")


        var listView = ListView<String>()
        (0..9).asSequence().map{ it.toString() }.forEach { listView.items.add(it) }
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
                .subscribe {next -> println("$next") }
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

    fun letPracticeSub(): Unit {
        return Unit
    }
}