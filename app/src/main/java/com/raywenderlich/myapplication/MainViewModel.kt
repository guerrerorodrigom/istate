package com.raywenderlich.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.myapplication.models.User

class MainViewModel : ViewModel() {

  val users: LiveData<List<User>>
    get() = _users
  private val _users = MutableLiveData<List<User>>()
}