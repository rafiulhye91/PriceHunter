package com.example.pricehunter.data.util

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.util.AppExceptions.*
import com.example.pricehunter.data.util.ErrorType.*
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection.*

private const val TAG = "AppExceptions"

/**
 *
 * Enumeration class that holds the different types of errors that can occur in the application.
 * Each error type is associated with a message that will be displayed to the user.
 */
enum class ErrorType(val message: String) {
    UNKNOWN_ERROR("Unknown Error!"),
    NETWORK_ERROR("Network Error!"),
    PAGE_NOT_FOUND("Page Not Found!"),
    DB_ERROR("Database Error!"),
    UNAUTHORIZED_ERROR("User is unauthorized!"),
    NO_DATA_AVAILABLE("No Data found!"),
    FORBIDDEN_ERROR("Server Refused to authorized the request!"),
    BAD_REQUEST_ERROR("Invalid Request!"),
    INTERNAL_SERVER_ERROR("The server encountered an unexpected error!"),
    DB_CONSTRAINT_ERROR("DB constraint is violated!")
}

/**
 *
 * Sealed class that represents different types of exceptions that can occur in the application.
 * Each exception type corresponds to a specific error that can happen.
 */
sealed class AppExceptions : Exception() {
    class UnauthorizedException : AppExceptions()
    class PageNotFoundException : AppExceptions()
    class UnknownException : AppExceptions()
    class NoDataException : AppExceptions()
    class ForBiddenException : AppExceptions()
    class BadRequestException : AppExceptions()
    class InternalServerErrorException : AppExceptions()
}

/**
 *
 * handleHTTPError function is used to handle HTTP errors that occur in the network layer of the app.
 * It takes a retrofit Response object as a parameter and maps it to the corresponding exception.
 * If the response code is not found in the errorMap, it throws a HttpException with the given response.
 */
fun <T> handleHTTPError(response: Response<T>) {
    val errorMap: Map<Int, Exception> = mapOf(
        HTTP_UNAUTHORIZED to UnauthorizedException(),
        HTTP_NOT_FOUND to PageNotFoundException(),
        HTTP_FORBIDDEN to ForBiddenException(),
        HTTP_BAD_REQUEST to BadRequestException(),
        HTTP_INTERNAL_ERROR to InternalServerErrorException(),
    )
    val exception = errorMap[response.code()] ?: HttpException(response)
    throw exception
}

/**
 *
 * handleException function is used to handle various types of exceptions that can occur in the app.
 * It takes an exception object as a parameter and maps it to the corresponding error type.
 * It returns a Resource.Error object with the error message of the error type.
 * It also logs the localized message of the exception.
 */
fun <T> handleException(e: Exception): Resource<T> {
    val errorMap: Map<Class<out Exception>, ErrorType> = mapOf(
        UnauthorizedException::class.java to UNAUTHORIZED_ERROR,
        PageNotFoundException::class.java to PAGE_NOT_FOUND,
        HttpException::class.java to NETWORK_ERROR,
        SQLiteException::class.java to DB_ERROR,
        SQLiteConstraintException::class.java to DB_CONSTRAINT_ERROR,
        NoDataException::class.java to NO_DATA_AVAILABLE,
        UnknownException::class.java to UNKNOWN_ERROR,
        ForBiddenException::class.java to FORBIDDEN_ERROR,
        BadRequestException::class.java to BAD_REQUEST_ERROR,
        InternalServerErrorException::class.java to INTERNAL_SERVER_ERROR,
    )
    val error = errorMap[e.javaClass] ?: UNKNOWN_ERROR
    Log.e(TAG, e.localizedMessage)
    return Resource.Error<T>(error = error.message)
}
