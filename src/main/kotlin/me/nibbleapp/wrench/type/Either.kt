package me.nibbleapp.wrench.type

sealed class Either<out L, out R> {

    data class Left<out T>(val value: T) : Either<T, Nothing>()
    data class Right<out T>(val value: T) : Either<Nothing, T>()

    /**
     * Example:
     * ```
     * val folded: Either<Error, Value> = someMethod()
     * result.fold(
     *      { log("Error with $it") },
     *      { log("Success with $it") }
     * )
     * ```
     */
    inline fun <C> fold(left: (L) -> C, right: (R) -> C): C = when (this) {
        is Left -> left(value)
        is Right -> right(value)
    }

    /**
     * Example:
     * ```
     * Left(error).swap()   // Result: Right("Maradona")
     * Right("Maradona").swap() // Result: Left(error)
     * ```
     */
    fun swap() = fold({ Right(it) }, { Left(it) })

    /**
     * Example:
     * ```
     * Right("Maradona").map { "Diego" } // Result: Right("Diego")
     * Left(error).map { "Diego" }  // Result: Left(error)
     * ```
     */
    inline fun <C> map(f: (R) -> C): Either<L, C> =
            fold({ Left(it) }, { Right(f(it)) })

    /**
     * Returns the value from this [Either.Right] or the given argument if this is a [Either.Left].
     *
     * Example:
     * ```
     * Right("Maradona").getOrElse("undefined") // Result: "Maradona"
     * Left(error).getOrElse("undefined")  // Result: "undefined"
     * ```
     */
    inline fun <B> Either<*, B>.getOrElse(default: () -> B): B =
            fold({ default() }, { it })
}

