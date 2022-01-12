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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.istate.R

@Composable
fun RegistrationFormScreen() {
  Column(
    modifier = Modifier.padding(16.dp)
  ) {
    OutlinedTextField(
      value = "",
      onValueChange = { },
      leadingIcon = { Icon(Icons.Default.Email, contentDescription = "") },
      modifier = Modifier
        .padding(top = 16.dp)
        .fillMaxWidth(),
      placeholder = { Text(stringResource(R.string.email)) }
    )
    OutlinedTextField(
      value = "",
      onValueChange = { },
      leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = "") },
      modifier = Modifier
        .padding(top = 16.dp)
        .fillMaxWidth(),
      placeholder = { Text(stringResource(R.string.username)) }
    )

    Row(
      Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
    ) {
      RadioButton(
        selected = true,
        onClick = { }
      )
      Text(
        text = stringResource(R.string.star_wars),
        style = MaterialTheme.typography.body1.merge(),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
      )
      RadioButton(
        selected = false,
        onClick = { }
      )
      Text(
        text = stringResource(R.string.star_trek),
        style = MaterialTheme.typography.body1.merge(),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
      )
    }
    Row(
      modifier = Modifier
        .padding(vertical = 16.dp)
        .clickable(onClick = { })
    ) {
      Text("")
      Icon(Icons.Filled.ArrowDropDown, contentDescription = "")
      DropdownMenu(
        expanded = false,
        onDismissRequest = { },
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
      ) {
        listOf<String>().forEachIndexed { index, name ->
          DropdownMenuItem(onClick = { }) {
            Text(text = name)
          }
        }
      }
    }
    OutlinedButton(
      onClick = { },
      modifier = Modifier.fillMaxWidth(),
      enabled = false
    ) {
      Text(stringResource(R.string.register))
    }
    OutlinedButton(
      onClick = { },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
    ) {
      Text(stringResource(R.string.clear))
    }
  }
}

@Preview
@Composable
fun PreviewTextInputField() {
  RegistrationFormScreen()
}