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
package com.raywenderlich.istate.ui.composables

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.istate.models.User
import istate.R

@Composable
fun RegistrationFormScreen(
  email: String,
  username: String,
  isStarWarsSelected: Boolean,
  avengers: List<String>,
  showDropDownMenu: Boolean,
  favoriteAvenger: String,
  isRegisterEnabled: Boolean,
  isValidEmail: Boolean,
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
      placeholder = R.string.email,
      isError = !isValidEmail
    )

    EditTextField(
      value = username,
      onValueChange = { onUsernameChanged(it) },
      leadingIcon = Icons.Default.AccountBox,
      placeholder = R.string.username,
      modifier = Modifier.padding(top = 16.dp),
      isError = false
    )

    Row(
      Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
    ) {
      RadioButtonWithText(
        isSelected = isStarWarsSelected,
        onClick = { onStarWarsSelectedChanged(true) },
        text = R.string.star_wars,
        modifier = Modifier.align(Alignment.CenterVertically)
      )
      RadioButtonWithText(
        isSelected = !isStarWarsSelected,
        onClick = { onStarWarsSelectedChanged(false) },
        text = R.string.star_trek,
        modifier = Modifier.align(Alignment.CenterVertically)
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
  isError: Boolean,
  modifier: Modifier = Modifier
) {
  OutlinedTextField(
    value = value,
    onValueChange = { onValueChange(it) },
    leadingIcon = { Icon(leadingIcon, contentDescription = "") },
    modifier = modifier.fillMaxWidth(),
    placeholder = { Text(stringResource(placeholder)) },
    isError = isError
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
    showDropDownMenu = false,
    isValidEmail = true
  )
}