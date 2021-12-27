package com.raywenderlich.myapplication

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
