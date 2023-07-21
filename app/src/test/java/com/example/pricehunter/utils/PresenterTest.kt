package com.example.pricehunter.utils

import org.junit.Rule

abstract class PresenterTest {
    @get:Rule
    val coroutineRule = MainDispatcherRule()
}