package br.solutionsjs.luastation.data.repositories

import br.solutionsjs.luastation.data.models.Notification

interface NotificationsRepository {
    fun getNotifications(): List<Notification?>
}
