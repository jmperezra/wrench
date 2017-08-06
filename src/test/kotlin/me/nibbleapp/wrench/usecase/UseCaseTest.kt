package me.nibbleapp.wrench.usecase

import me.nibbleapp.wrench.sample.executor.DefaultExecutor
import me.nibbleapp.wrench.type.Either
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.Executors

class UseCaseTest {

    private val mainExecutor = DefaultExecutor(Executors.newScheduledThreadPool(4))

    @Test
    fun `Given an error, use case should return left either`() {
        val result = UseCase({Either.Left(Error.UnknownError())}, mainExecutor).execute()
        Assert.assertTrue(result.isLeft())
    }

    @Test
    fun `Given a success, use case should return right either`() {
        val result = UseCase({Either.Right(true)}, mainExecutor).execute()
        Assert.assertTrue(result.isRight())
    }
}