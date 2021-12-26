package com.raywenderlich.myapplication.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.myapplication.models.User

@Composable
fun FabAddUser(onClick: () -> Unit = { }) {
  FloatingActionButton(
    onClick = onClick,
    backgroundColor = Color.Red
  ) {
    Icon(
      Icons.Default.Add,
      contentDescription = "",
      tint = Color.White
    )
  }
}

@Composable
fun UserList(users: List<User>) {
  val listState = rememberLazyListState()

  LazyColumn(state = listState) {
    items(
      items = users,
      key = { user -> user.email }
    ) { user ->
      ItemUser(user)
      Divider()
    }
  }
}

@Composable
fun ItemUser(user: User) {
  Column(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth()
  ) {
    Text(text = user.username, style = MaterialTheme.typography.h4)
    Text(text = user.email, style = MaterialTheme.typography.h5)
    val likes = if (user.likesStarWars) "Star Wars" else "Star Trek"
    Text(text = "Likes $likes", style = MaterialTheme.typography.body1)
    Text(text = "Avenger: ${user.favoriteAvenger}", style = MaterialTheme.typography.body2)
  }
}

@Composable
@Preview
fun PreviewItemUser() {
  val user = User("username", "email@email.com", "Iron Man", true)
  ItemUser(user = user)
}

@Composable
@Preview
fun PreviewUserList() {
  val user = User("username", "email@email.com", "Iron Man", true)
  UserList(users = listOf(user, user, user, user, user))
}