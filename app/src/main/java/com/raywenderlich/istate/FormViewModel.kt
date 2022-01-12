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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val favoriteAvengerDefault = "Select favorite Avenger"

class FormViewModel : ViewModel() {
  val avengers = listOf(
    "Iron Man",
    "Capitan America",
    "Hulk",
    "Spiderman",
    "Black Widow",
    "Hawkeye",
    "Thor",
    "Scarlet Witch",
    "Black Panther"
  )

  private val _email = MutableLiveData<String>()
  val email: LiveData<String>
    get() = _email

  private val _username = MutableLiveData<String>()
  val username: LiveData<String>
    get() = _username

  private val _isStarWarsSelected = MutableLiveData<Boolean>()
  val isStarWarsSelected: LiveData<Boolean>
    get() = _isStarWarsSelected

  private val _favoriteAvenger = MutableLiveData<String>()
  val favoriteAvenger: LiveData<String>
    get() = _favoriteAvenger

  private val _showDropDownMenu = MutableLiveData<Boolean>()
  val showDropDownMenu: LiveData<Boolean>
    get() = _showDropDownMenu

  val isFormValid = MediatorLiveData<Boolean>().apply {
    addSource(email) {
      value = isFormValid()
    }
    addSource(username) {
      value = isFormValid()
    }
    addSource(favoriteAvenger) {
      value = isFormValid()
    }
  }

  fun onClearClicked() {
    _email.value = ""
    _username.value = ""
    _isStarWarsSelected.value = true
    _favoriteAvenger.value = favoriteAvengerDefault
  }

  fun onDropDownClicked() {
    _showDropDownMenu.value = true
  }

  fun onDropDownDismissed() {
    _showDropDownMenu.value = false
  }

  fun onEmailChanged(value: String) {
    _email.value = value
  }

  fun onFavoriteAvengerChanged(index: Int) {
    _favoriteAvenger.value = avengers[index]
    _showDropDownMenu.value = false
  }

  fun onUsernameChanged(value: String) {
    _username.value = value
  }

  fun onStarWarsSelectedChanged(value: Boolean) {
    _isStarWarsSelected.value = value
  }

  private fun isFormValid() =
    _email.value?.isNotEmpty() == true && _username.value?.isNotEmpty() == true && _favoriteAvenger.value?.equals(
      favoriteAvengerDefault
    ) == false
}
