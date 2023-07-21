package com.example.pricehunter.data

/**
 *
 * A sealed class that represents the resource that is returned from a data source.
 * It can either be a success with data, or an error with a message.
 * @param T The type of the data that is returned in the resource.
 * @property data The data returned in the resource, if the resource is a success.
 * @property error The error message returned in the resource, if the resource is an error.
 *
 * @author [Rafiul Hye]
 * @since [Current Date or Version]
 */

sealed class Resource<T>(val data: T? = null, val error: String? = null) {

    /**
     *
     * A subclass of [Resource] that represents a successful resource with data.
     * @param T The type of the data that is returned in the resource.
     * @property data The data returned in the resource.
     */
    class Success<T>(data: T?) : Resource<T>(data)

    /**
     *
     * A subclass of [Resource] that represents an error resource with an error message.
     * @param T The type of the data that is returned in the resource.
     * @property data The data returned in the resource.
     * @property error The error message returned in the resource.
     */
    class Error<T>(data: T? = null, error: String?) : Resource<T>(data, error)
}
