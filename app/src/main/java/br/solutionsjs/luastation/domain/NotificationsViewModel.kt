package br.solutionsjs.luastation.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.solutionsjs.luastation.data.models.Notification
import br.solutionsjs.luastation.data.repositories.NotificationsRepository
import br.solutionsjs.luastation.data.repositories.NotificationsRepositoryImpl

class NotificationsViewModel : ViewModel() {
    private val repository: NotificationsRepository

    init {
        repository = NotificationsRepositoryImpl()
    }

    private val _notifications = MutableLiveData<List<Notification?>>()
    val notifications: LiveData<List<Notification?>>
        get() = _notifications

    fun getNotifications() {
        _notifications.value = repository.getNotifications()
    }
}
