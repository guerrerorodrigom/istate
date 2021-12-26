package com.raywenderlich.myapplication.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.myapplication.R
import com.raywenderlich.myapplication.models.User

const val favoriteAvengerDefault = "Select favorite Avenger"

@Composable
fun RegistrationFormScreen(navigateBack: (User) -> Unit) {
  Column(
    modifier = Modifier.padding(16.dp)
  ) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var isStarWarsSelected by remember { mutableStateOf(true) }
    val avengers = listOf("Iron Man", "Capitan America", "Hulk", "Spiderman", "Black Widow")
    var showDropDownMenu by remember { mutableStateOf(false) }
    var favoriteAvenger by remember { mutableStateOf(favoriteAvengerDefault) }
    var isRegisterEnabled by remember { mutableStateOf(false) }

    EditTextField(
      value = email,
      onValueChange = {
        email = it
        isRegisterEnabled = isFormValid(email, username, favoriteAvenger)
      },
      leadingIcon = Icons.Default.Email,
      placeholder = R.string.email
    )

    EditTextField(
      value = username,
      onValueChange = {
        username = it
        isRegisterEnabled = isFormValid(email, username, favoriteAvenger)
      },
      leadingIcon = Icons.Default.AccountBox,
      placeholder = R.string.username,
      modifier = Modifier.padding(top = 16.dp)
    )

    Row(
      Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
    ) {
      RadioButtonWithText(
        isSelected = isStarWarsSelected,
        onClick = { isStarWarsSelected = true },
        text = R.string.star_wars
      )
      RadioButtonWithText(
        isSelected = !isStarWarsSelected,
        onClick = { isStarWarsSelected = false },
        text = R.string.star_trek
      )
    }
    Row(
      modifier = Modifier
        .padding(vertical = 16.dp)
        .clickable(onClick = { showDropDownMenu = true })
    ) {
      DropDown(
        selectedValue = favoriteAvenger,
        showDropDownMenu = showDropDownMenu,
        menuItems = avengers,
        onDismissRequest = { showDropDownMenu = false },
        onItemSelected = { index ->
          favoriteAvenger = avengers[index]
          showDropDownMenu = !showDropDownMenu
          isRegisterEnabled = isFormValid(email, username, favoriteAvenger)
        }
      )
    }
    OutlinedButton(
      onClick = {
        navigateBack(
          User(
            username = username,
            email = email,
            favoriteAvenger = favoriteAvenger,
            likesStarWars = isStarWarsSelected
          )
        )
      },
      modifier = Modifier.fillMaxWidth(),
      enabled = isRegisterEnabled
    ) {
      Text(stringResource(R.string.register))
    }
    OutlinedButton(
      onClick = {
        username = ""
        email = ""
        isStarWarsSelected = true
        isRegisterEnabled = false
        favoriteAvenger = favoriteAvengerDefault
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
    ) {
      Text(stringResource(R.string.clear))
    }
  }
}

@Composable
fun DropDown(
  selectedValue: String,
  showDropDownMenu: Boolean,
  menuItems: List<String>,
  onDismissRequest: () -> Unit,
  onItemSelected: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Text(selectedValue)
  Icon(Icons.Filled.ArrowDropDown, contentDescription = "")
  DropdownMenu(
    expanded = showDropDownMenu,
    onDismissRequest = { onDismissRequest() },
    modifier = modifier
      .fillMaxWidth()
      .background(Color.White)
  ) {
    menuItems.forEachIndexed { index, name ->
      DropdownMenuItem(onClick = {
        onItemSelected(index)
      }) {
        Text(text = name)
      }
    }
  }
}

@Composable
fun EditTextField(
  value: String,
  onValueChange: (String) -> Unit,
  leadingIcon: ImageVector,
  @StringRes placeholder: Int,
  modifier: Modifier = Modifier
) {
  OutlinedTextField(
    value = value,
    onValueChange = { onValueChange(it) },
    leadingIcon = { Icon(leadingIcon, contentDescription = "") },
    modifier = modifier.fillMaxWidth(),
    placeholder = { Text(stringResource(placeholder)) }
  )
}

@Composable
fun RadioButtonWithText(
  isSelected: Boolean,
  onClick: () -> Unit,
  @StringRes text: Int,
  modifier: Modifier = Modifier
) {
  RadioButton(
    selected = isSelected,
    onClick = { onClick() }
  )
  Text(
    text = stringResource(text),
    style = MaterialTheme.typography.body1.merge(),
    modifier = modifier.padding(start = 8.dp, end = 8.dp)
  )
}

private fun isFormValid(email: String, username: String, avenger: String) =
  email.isNotEmpty() && username.isNotEmpty() && avenger != favoriteAvengerDefault

@Preview
@Composable
fun PreviewTextInputField() {
  RegistrationFormScreen {}
}