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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.myapplication.R
import com.raywenderlich.myapplication.models.User

@Composable
fun RegistrationFormScreen(
  email: String,
  username: String,
  isStarWarsSelected: Boolean,
  avengers: List<String>,
  showDropDownMenu: Boolean,
  favoriteAvenger: String,
  isRegisterEnabled: Boolean,
  onUsernameChanged: (String) -> Unit,
  onEmailChanged: (String) -> Unit,
  onStarWarsSelectedChanged: (Boolean) -> Unit,
  onFavoriteAvengerChanged: (Int) -> Unit,
  onDropDownClicked: () -> Unit,
  onDropDownDismissed: () -> Unit,
  onClearClicked: () -> Unit,
  onRegisterClicked: (User) -> Unit
) {
  Column(
    modifier = Modifier.padding(16.dp)
  ) {
    EditTextField(
      value = email,
      onValueChange = { onEmailChanged(it) },
      leadingIcon = Icons.Default.Email,
      placeholder = R.string.email
    )

    EditTextField(
      value = username,
      onValueChange = { onUsernameChanged(it) },
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
        onClick = { onStarWarsSelectedChanged(true) },
        text = R.string.star_wars
      )
      RadioButtonWithText(
        isSelected = !isStarWarsSelected,
        onClick = { onStarWarsSelectedChanged(false) },
        text = R.string.star_trek
      )
    }
    Row(
      modifier = Modifier
        .padding(vertical = 16.dp)
        .clickable(onClick = { onDropDownClicked() })
    ) {
      DropDown(
        selectedValue = favoriteAvenger,
        showDropDownMenu = showDropDownMenu,
        menuItems = avengers,
        onDismissRequest = { onDropDownDismissed() },
        onItemSelected = { index ->
          onFavoriteAvengerChanged(index)
        }
      )
    }
    OutlinedButton(
      onClick = {
        onRegisterClicked(
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
      onClick = { onClearClicked() },
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

@Preview
@Composable
fun PreviewTextInputField() {
  RegistrationFormScreen(
    avengers = listOf(),
    email = "",
    favoriteAvenger = "",
    username = "",
    isStarWarsSelected = true,
    isRegisterEnabled = true,
    onClearClicked = {},
    onDropDownClicked = {},
    onDropDownDismissed = {},
    onEmailChanged = {},
    onFavoriteAvengerChanged = {},
    onRegisterClicked = {},
    onStarWarsSelectedChanged = {},
    onUsernameChanged = {},
    showDropDownMenu = false
  )
}