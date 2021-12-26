package com.raywenderlich.myapplication.models

import androidx.compose.runtime.saveable.listSaver

data class User(
  val username: String,
  val email: String,
  val favoriteAvenger: String,
  val likesStarWars: Boolean
)

val UserSaver = listSaver<User, Any>(
  save = { listOf(it.username, it.email, it.favoriteAvenger, it.likesStarWars) },
  restore = {
    User(
      username = it[0] as String,
      email = it[1] as String,
      favoriteAvenger = it[2] as String,
      likesStarWars = it[3] as Boolean
    )
  }
)