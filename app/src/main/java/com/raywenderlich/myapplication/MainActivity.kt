package com.raywenderlich.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
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
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      IStateTheme {
        val navController = rememberNavController()
        val users = remember { mutableStateListOf<User>() }

        NavHost(navController = navController, startDestination = "list") {
          composable("list") {
            UserListScreen(navController, users)
          }
          composable("form") {
            RegistrationFormScreen {
              users.add(it)
              navController.popBackStack()
            }
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