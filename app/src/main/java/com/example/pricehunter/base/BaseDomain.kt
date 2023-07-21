package com.example.pricehunter.base

import com.example.pricehunter.data.ItemWrapper
import com.example.pricehunter.data.ListItemWrapper
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.util.AppExceptions.NoDataException
import com.example.pricehunter.data.util.ErrorType.UNKNOWN_ERROR
import com.example.pricehunter.data.util.handleException
import com.example.pricehunter.data.util.handleHTTPError

/**
 *
 * Abstract class that provides a set of functions to handle different types of responses (API, DB, etc.).
 *
 * @author [Rafiul Hye]
 * @since [Current date or version]
 */

abstract class BaseDomain {
    /**
     *
     * Handles the response of an API call that returns a single item.
     * @param func A function that returns an [ItemWrapper] object.
     * @return A [Resource] object that can be either a success or an error.
     */
    inline fun <T, DTO> handleApiResponse(func: () -> ItemWrapper<T, DTO>): Resource<T> {
        try {
            val item = func()
            if (item.response.isSuccessful) {
                if (item.data == null) {
                    throw NoDataException()
                }
                return Resource.Success(item.data)
            }
            handleHTTPError(item.response)
        } catch (e: Exception) {
            return handleException(e)
        }
        return Resource.Error(error = UNKNOWN_ERROR.message)
    }

    /**
     *
     * Handles the response of an API call that returns a list of items.
     * @param func A function that returns a [ListItemWrapper] object.
     * @return A [Resource] object that can be either a success or an error.
     */
    inline fun <T, DTO> handleApiListResponse(func: () -> ListItemWrapper<T, DTO>): Resource<List<T>> {
        try {
            val item = func()
            if (item.response.isSuccessful) {
                if (item.data == null || item.data.isEmpty()) {
                    throw NoDataException()
                }
                return Resource.Success(item.data as List<T>)
            }
            handleHTTPError(item.response)
        } catch (e: Exception) {
            return handleException(e)
        }
        return Resource.Error(error = UNKNOWN_ERROR.message)
    }

    /**
     *
     * Handles the response of a DB query.
     * @param func A function that returns a single item from the DB.
     * @return A [Resource] object that can be either a success or an error.
     */
    inline fun <T> handleDbResponse(func: () -> T?): Resource<T> {
        return try {
            val data = func() ?: throw NoDataException()
            Resource.Success(data)
        } catch (e: Exception) {
            handleException(e)
        }
    }
}