package com.example.pricehunter.view.sample

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.SampleDomainData
import com.example.pricehunter.service.sample.ISampleService
import com.example.pricehunter.utils.FakeLifeCycle
import com.example.pricehunter.utils.PresenterTest
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SamplePresenterTest : PresenterTest() {

    private lateinit var service: ISampleService
    private lateinit var view: ISampleView
    private lateinit var presenter: SamplePresenter

    @Before
    fun setup() {
        view = mockk(relaxed = true)
        service = mockk(relaxed = true)
        presenter = SamplePresenter(view, service)
        every { view.lifecycle } returns FakeLifeCycle()
    }


    @Test
    fun `When start is called, Then service calls the getSampleData`() = runTest {
        presenter.start()
        coVerify { service.getSampleData() }
    }

    @Test
    fun `When start is called and getSampleData is called, Then show progress is called from the view`() {
        presenter.start()
        verify { view.showProgress() }
    }

    @Test
    fun `When start is called, Given getSampleData from Service is Successful, Then hideProgress and setSampleData is called`() =
        runTest {
            val testData = Resource.Success<SampleDomainData?>(SampleDomainData("TestData"))
            coEvery { service.getSampleData() } returns testData
            presenter.start()
            coVerify {
                view.hideProgress()
                view.setSampleData(testData.data)
            }
        }

    @Test
    fun `When start is called, Given getSampleData from Service Failed, Then hideProgress and showError is called`() =
        runTest {
            val testData = Resource.Error<SampleDomainData?>(error = "Something Bad Happened!")
            coEvery { service.getSampleData() } returns testData
            presenter.start()
            coVerify {
                view.hideProgress()
                view.showError(testData.error)
            }
        }

    @Test
    fun `When start is called, Given getSampleData from Service is Successful but data is null, Then hideProgress and setSampleData is called`() =
        runTest {
            val testData = Resource.Success<SampleDomainData?>(data = null)
            coEvery { service.getSampleData() } returns testData
            presenter.start()
            coVerifyOrder {
                view.hideProgress()
                view.showError("No data found!")
            }
        }

    @Test
    fun `When getAllData is called, Then Service calls getAllSampleData`() = runTest {
        presenter.getAllData()
        coVerify { service.getAllSampleData() }
    }

    @Test
    fun `When getAllData is called, Then showProgress is called`() {
        presenter.getAllData()
        verify { view.showProgress() }
    }

    @Test
    fun `When getAllData is called, Given service getAllSampleData is Successful, Then hide progress and call setAllData`() =
        runTest {
            val expected = Resource.Success<List<SampleDomainData?>>(emptyList())
            coEvery { service.getAllSampleData() } returns expected
            presenter.getAllData()
            coVerify {
                view.hideProgress()
                view.setAllData(expected.data)
            }
        }

    @Test
    fun `When getAllData is called, Given service getAllSampleData failed, Then hide progress and call show Error`() =
        runTest {
            val expected =
                Resource.Error<List<SampleDomainData?>>(error = "Something bad happened!")
            coEvery { service.getAllSampleData() } returns expected
            presenter.getAllData()
            coVerify {
                view.hideProgress()
                view.showError(expected.error)
            }
        }

    @Test
    fun `When getAllData is called, Given service getAllSampleData is Successful but data is null, Then hide progress and call show Erro`() =
        runTest {
            val expected = Resource.Success<List<SampleDomainData?>>(null)
            coEvery { service.getAllSampleData() } returns expected
            presenter.getAllData()
            coVerify {
                view.hideProgress()
                view.showError("No data found!")
            }
        }

    @After
    fun tearDown() {
        unmockkAll()
    }
}