package com.raywenderlich.myapplication

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raywenderlich.myapplication.models.User
import com.raywenderlich.myapplication.ui.composables.FabAddUser
import com.raywenderlich.myapplication.ui.composables.RegistrationFormScreen
import com.raywenderlich.myapplication.ui.composables.UserList
import com.raywenderlich.myapplication.ui.theme.IStateTheme

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
            val emailState by formViewModel.email.observeAsState("")
            val usernameState by formViewModel.username.observeAsState("")
            val favoriteAvengerState by formViewModel.favoriteAvenger.observeAsState(favoriteAvengerDefault)
            val isRegisterEnabledState by formViewModel.isFormValid.observeAsState(false)
            val isStarWarsSelectedState by formViewModel.isStarWarsSelected.observeAsState(true)
            val showDropDownMenuState by formViewModel.showDropDownMenu.observeAsState(false)

            RegistrationFormScreen(
              avengers = formViewModel.avengers,
              email = emailState,
              username = usernameState,
              favoriteAvenger = favoriteAvengerState,
              isRegisterEnabled = isRegisterEnabledState,
              isStarWarsSelected = isStarWarsSelectedState,
              showDropDownMenu = showDropDownMenuState,
              onClearClicked = formViewModel::onClearClicked,
              onDropDownClicked = formViewModel::onDropDownClicked,
              onDropDownDismissed = formViewModel::onDropDownDismissed,
              onEmailChanged = formViewModel::onEmailChanged,
              onFavoriteAvengerChanged = formViewModel::onFavoriteAvengerChanged,
              onUsernameChanged = formViewModel::onUsernameChanged,
              onStarWarsSelectedChanged = formViewModel::onStarWarsSelectedChanged,
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
        FabAddUser {
          navController.navigate("form")
        }
      },
      floatingActionButtonPosition = FabPosition.End
    ) {
      UserList(users)
    }
  }
}