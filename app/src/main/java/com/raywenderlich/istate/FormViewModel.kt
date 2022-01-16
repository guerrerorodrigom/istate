package com.raywenderlich.istate

import android.util.Patterns
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

  private val _isEmailValid = MutableLiveData<Boolean>()
  val isEmailValid: LiveData<Boolean>
    get() = _isEmailValid

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
    addSource(isEmailValid) {
      value = isFormValid()
    }
  }

  fun onClearClicked() {

  }

  fun onDropDownClicked() {

  }

  fun onDropDownDismissed() {

  }

  fun onEmailChanged(value: String) {

  }

  fun onFavoriteAvengerChanged(index: Int) {

  }

  fun onUsernameChanged(value: String) {

  }

  fun onStarWarsSelectedChanged(value: Boolean) {

  }

  private fun validateEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  private fun isFormValid() =
    _email.value?.isNotEmpty() == true &&
      _isEmailValid.value == true &&
      _username.value?.isNotEmpty() == true &&
      _favoriteAvenger.value?.equals(favoriteAvengerDefault) == false
}
