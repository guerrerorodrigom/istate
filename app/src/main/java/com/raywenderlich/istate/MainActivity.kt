/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.raywenderlich.istate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raywenderlich.istate.models.RegistrationFormData
import com.raywenderlich.istate.models.User
import com.raywenderlich.istate.ui.composables.FabAddUser
import com.raywenderlich.istate.ui.composables.RegistrationFormScreen
import com.raywenderlich.istate.ui.composables.UserList
import com.raywenderlich.istate.ui.theme.IStateTheme

class MainActivity : ComponentActivity() {
  private val mainViewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      IStateTheme {
        val navController = rememberNavController()
        val users by mainViewModel.users.observeAsState(emptyList())

        NavHost(navController = navController, startDestination = "list") {
          composable("list") {
            UserListScreen(navController, users)
          }
          composable("form") {
            val formViewModel: FormViewModel by viewModels()
            val registrationFormData by formViewModel.formData.observeAsState(RegistrationFormData())

            RegistrationFormScreen(
              registrationFormData = registrationFormData,
              onUsernameChanged = formViewModel::onUsernameChanged,
              onEmailChanged = formViewModel::onEmailChanged,
              onStarWarsSelectedChanged = formViewModel::onStarWarsSelectedChanged,
              onFavoriteAvengerChanged = formViewModel::onFavoriteAvengerChanged,
              onClearClicked = formViewModel::onClearClicked,
              onRegisterClicked = { user ->
                formViewModel.onClearClicked()
                mainViewModel.addUser(user)
                navController.popBackStack()
              }
            )
          }
        }
      }
    }
  }
}

@Composable
fun UserListScreen(
  navController: NavController,
  users: List<User>
) {
  Surface(color = MaterialTheme.colors.background) {
    Scaffold(
      floatingActionButton = {
        FabAddUser(navController)
      },
      floatingActionButtonPosition = FabPosition.End
    ) {
      UserList(users)
    }
  }
}