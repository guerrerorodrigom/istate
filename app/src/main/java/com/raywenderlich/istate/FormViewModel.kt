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

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.istate.models.RegistrationFormData
import com.raywenderlich.istate.models.avengersList

const val favoriteAvengerDefault = "Select favorite Avenger"

class FormViewModel : ViewModel() {

  private val _formData = MutableLiveData(RegistrationFormData())
  val formData: LiveData<RegistrationFormData>
    get() = _formData

  fun onClearClicked() {
    _formData.value = RegistrationFormData()
  }

  fun onDropDownClicked() {
    _formData.value = _formData.value?.copy(showDropDownMenu = true)
  }

  fun onDropDownDismissed() {
    _formData.value = _formData.value?.copy(showDropDownMenu = false)
  }

  fun onEmailChanged(email: String) {
    val isFormValid = _formData.value?.run {
      isFormValid(email, isValidEmail, username, favoriteAvenger)
    } ?: false
    _formData.value = _formData.value?.copy(
      email = email,
      isValidEmail = validateEmail(email),
      isRegisterEnabled = isFormValid
    )
  }

  fun onFavoriteAvengerChanged(value: String) {
    val isFormValid = _formData.value?.run {
      isFormValid(email, isValidEmail, username, value)
    } ?: false
    _formData.value = _formData.value?.copy(
      favoriteAvenger = value,
      showDropDownMenu = false,
      isRegisterEnabled = isFormValid
    )
  }

  fun onUsernameChanged(username: String) {
    val isFormValid = _formData.value?.run {
      isFormValid(email, isValidEmail, username, favoriteAvenger)
    } ?: false
    _formData.value = _formData.value?.copy(username = username, isRegisterEnabled = isFormValid)
  }

  fun onStarWarsSelectedChanged(isStarWarsSelected: Boolean) {
    _formData.value = _formData.value?.copy(isStarWarsSelected = isStarWarsSelected)
  }

  private fun validateEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  private fun isFormValid(
    email: String,
    isValidEmail: Boolean,
    username: String,
    favoriteAvenger: String
  ) =
    email.isNotEmpty() && isValidEmail && username.isNotEmpty() && favoriteAvenger != favoriteAvengerDefault
}
