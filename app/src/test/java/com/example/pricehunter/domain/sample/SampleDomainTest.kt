package com.example.pricehunter.domain.sample

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.local.DaoServices
import com.example.pricehunter.data.remote.ApiServices
import com.example.pricehunter.data.remote.model.SampleDTO
import com.example.pricehunter.data.util.AppExceptions.PageNotFoundException
import com.example.pricehunter.data.util.AppExceptions.UnauthorizedException
import com.example.pricehunter.data.util.ErrorType.*
import com.example.pricehunter.domain.generateMockErrorResponseBody
import com.example.pricehunter.domain.model.SampleDomainData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class SampleDomainTest {

    private lateinit var apiServices: ApiServices
    private lateinit var daoServices: DaoServices
    private lateinit var domain: SampleDomain

    @Before
    fun setup() {
        apiServices = mockk(relaxed = true)
        daoServices = mockk(relaxed = true)
        domain = SampleDomain(apiServices, daoServices)
    }

    @Test
    fun `When getSampleData is called from network, Then ApiServices calls the getSampleData`() =
        runTest {
            coEvery { apiServices.getSampleData() } returns Response.success(null)
            domain.getSampleData()
            coVerify { apiServices.getSampleData() }
        }

    @Test
    fun `When getAllSampleData is called from db, the DaoServices calls the getAllSampleData`() =
        runTest {
            domain.getAllSampleData()
            coVerify { daoServices.getAllSampleDbData() }
        }

    @Test
    fun `Given API call returns a UnauthorizedException exception, When getSampleData is called, return the unauthorized error`() =
        runTest {
            coEvery { apiServices.getSampleData() }.throws(UnauthorizedException())
            val result = domain.getSampleData()
            val expected =
                Resource.Error<List<SampleDomainData>>(error = UNAUTHORIZED_ERROR.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a NotFoundException exception, When getSampleData is called, return the page not found error`() =
        runTest {
            coEvery { apiServices.getSampleData() }.throws(PageNotFoundException())
            val result = domain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = PAGE_NOT_FOUND.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a SQLiteConstraintException, When getSampleData is called, return the db constraint error`() =
        runTest {
            coEvery { apiServices.getSampleData() }.throws(SQLiteConstraintException())
            val result = domain.getSampleData()
            val expected =
                Resource.Error<List<SampleDomainData>>(error = DB_CONSTRAINT_ERROR.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a SQLiteException, When getSampleData is called, return the db error`() =
        runTest {
            coEvery { apiServices.getSampleData() }.throws(SQLiteException())
            val result = domain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = DB_ERROR.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a HttpException, When getSampleData is called, return the no network error`() =
        runTest {
            val response = Response.error<SampleDTO>(503, generateMockErrorResponseBody())
            coEvery { apiServices.getSampleData() }.throws(HttpException(response))
            val result = domain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = NETWORK_ERROR.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a error code 400, When getSampleData is called, return the bad request error`() =
        runTest {
            val response = Response.error<SampleDTO?>(400, generateMockErrorResponseBody())
            coEvery { apiServices.getSampleData() } returns response
            val result = domain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = BAD_REQUEST_ERROR.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a error code 401, When getSampleData is called, return the unauth error`() =
        runTest {
            val response = Response.error<SampleDTO?>(401, generateMockErrorResponseBody())
            coEvery { apiServices.getSampleData() } returns response
            val result = domain.getSampleData()
            val expected =
                Resource.Error<List<SampleDomainData>>(error = UNAUTHORIZED_ERROR.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a error code 403, When getSampleData is called, return the forbidden error`() =
        runTest {
            val response = Response.error<SampleDTO?>(403, generateMockErrorResponseBody())
            coEvery { apiServices.getSampleData() } returns response
            val result = domain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = FORBIDDEN_ERROR.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a error code 404, When getSampleData is called, return the internal server error`() =
        runTest {
            val response = Response.error<SampleDTO?>(404, generateMockErrorResponseBody())
            coEvery { apiServices.getSampleData() } returns response
            val result = domain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = PAGE_NOT_FOUND.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a error code 500, When getSampleData is called, return the internal server error`() =
        runTest {
            val response = Response.error<SampleDTO?>(500, generateMockErrorResponseBody())
            coEvery { apiServices.getSampleData() } returns response
            val result = domain.getSampleData()
            val expected =
                Resource.Error<List<SampleDomainData>>(error = INTERNAL_SERVER_ERROR.message)
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call is successful but data is null, When getSampleData is called, return No Data Found error`() =
        runTest {
            coEvery { apiServices.getSampleData() } returns Response.success(null)
            val result = domain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = NO_DATA_AVAILABLE.message)
            assertThat(result.data).isEqualTo(expected.data)
        }

    @After
    fun tearDown() {
        unmockkAll()
    }
}