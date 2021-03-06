/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package ex_5_4

sealed class List<A>{
    abstract fun isEmpty(): Boolean
    fun cons(a:A) : List<A> = Cons(a,this)
    fun setHead(a: A): List<A> = when (this) {
        Nil -> throw IllegalStateException("setHead called on an empty list")
        is Cons -> tail.cons(a)
    }

    fun drop (n:Int) : List<A>{
        tailrec fun drop (n: Int, list: List<A>) : List<A> =
            if (n<= 0) list else when (list) {
                is Cons -> drop(n - 1, list.tail)
                is Nil -> list
            }
        return drop(n,this)
    }

    fun dropWhile(p: (A)-> Boolean): List<A> =
        dropWhile(this,p)


    private object Nil:List<Nothing>(){
        override fun isEmpty()=true
        override fun toString():String="[NIL]"
    }
    private class Cons<A>(internal val head: A, internal val tail: List<A>):List<A>() {
        override fun isEmpty() = false
        override fun toString(): String = "[${toString("", this)}NIL]"
        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head},", list.tail)
            }
    }
        companion object{
             operator  fun <A> invoke(vararg az: A): List<A> =
             az.foldRight(Nil as List<A>) { a:A, list:List<A>->Cons(a,list) }

            private tailrec  fun <A> dropWhile (list: List<A>, p:(A)-> Boolean) : List <A> =
                when (list) {
                    Nil -> list
                    is Cons -> if (p(list.head)) dropWhile (list.tail, p) else list
                }
        }
    }
class App {
    val greeting: String  get() { return "ex_5_4" }    
}

fun main() {
    println(App().greeting)

    val invoke_test = List(20,30,40,10,1,2,3)
    println(invoke_test)
    println(invoke_test.dropWhile { x -> x>10 })
}