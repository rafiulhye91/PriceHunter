package com.example.pricehunter.service.sample

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.SampleDomainData
import com.example.pricehunter.domain.sample.ISampleDomain
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SampleServiceTest {

    private lateinit var domain: ISampleDomain
    private lateinit var service: SampleService

    @Before
    fun setup() {
        domain = mockk(relaxed = true)
        service = SampleService(domain)
    }

    @Test
    fun `When getSampleData is called, Then domain also calls the getSampleData`() = runTest {
        service.getSampleData()
        coVerify { domain.getSampleData() }
    }

    @Test
    fun `When getAllSampleData is called, Then domain also calls the getAllSampleData`() = runTest {
        service.getAllSampleData()
        coVerify { domain.getAllSampleData() }
    }

    @Test
    fun `When getSampleData is called, Given the getSampleData call from the domain is a Success but data is null, Then the service returns the null data`() =
        runTest {
            val expected = Resource.Success<SampleDomainData?>(null)
            coEvery { domain.getSampleData() } returns expected
            val result = service.getSampleData()
            assertThat(result).isEqualTo(expected)
        }

    @Test
    fun `When getAllSampleData is called, Given the getAllSampleData call from the domain is a Success but data is null, Then the service returns the null data`() =
        runTest {
            val expected = Resource.Success<List<SampleDomainData?>>(null)
            coEvery { domain.getAllSampleData() } returns expected
            val result = service.getAllSampleData()
            assertThat(result).isEqualTo(expected)
        }

    @Test
    fun `When getAllSampleData is called, Given the getAllSampleData call from the domain is a Success but returns an empty list, Then the service returns the empty list`() =
        runTest {
            val expected = Resource.Success<List<SampleDomainData?>>(emptyList())
            coEvery { domain.getAllSampleData() } returns expected
            val result = service.getAllSampleData()
            assertThat(result).isEqualTo(expected)
        }

    @Test
    fun `When getSampleData is called, Given the getSampleData call from the domain returns an error, Then the service returns the same error`() =
        runTest {
            val expected = Resource.Error<SampleDomainData?>(error = "Something bad happened!")
            coEvery { domain.getSampleData() } returns expected
            val result = service.getSampleData()
            assertThat(result.error).isEqualTo(expected.error)
        }

    @After
    fun tearDown() {
        unmockkAll()
    }
}