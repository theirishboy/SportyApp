package com.example.yoursportapp

import com.example.yoursportapp.data.UserDatabaseDAO
import com.example.yoursportapp.ui.screen.SignInViewModel
import dev.icerock.moko.mvvm.test.TestObserver
import dev.icerock.moko.mvvm.test.TestViewModelScope
import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SignInViewModelTest {
    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @BeforeTest
    fun setup() {
        TestViewModelScope.setupViewModelScope(CoroutineScope(Dispatchers.Unconfined))
    }

    @AfterTest
    fun reset() {
        TestViewModelScope.resetViewModelScope()
    }

    @Test
    fun `login successful`() {
        val events = mutableListOf<String>()
//        val listener = object : SignInViewModel.EventsListener {
//            override fun routeToMainScreen() {
//                events.add("routeToMainScreen")
//            }
//
//            override fun showError(error: StringDesc) {
//                events.add("showError($error)")
//            }
//        }
        val viewModel = SignInViewModel(
          //  eventsDispatcher = createTestEventsDispatcher(listener),
            userDao = UserDatabaseDAO()
        )
        val loginButtonVisibleObserver = TestObserver<Boolean>()
        //viewModel.isLoginButtonVisible.addObserver(loginButtonVisibleObserver.lambda)

        //assertEquals(expected = false, actual = viewModel.isLoginButtonVisible.value)

        viewModel._signInUiState.value.username= "am@icerock.dev"
        viewModel._signInUiState.value.password = "666666"
        var response = ""
        runBlocking {
            response = viewModel.signIn().toString()
        }
        //assertEquals(expected = true, actual = viewModel.isLoginButtonVisible.value)

      //  assertEquals(expected = 1, actual = events.size)
      //  viewModel.onLoginButtonPressed()
        assertEquals(expected =  "NetworkException",
            actual = response
        )
    }
}