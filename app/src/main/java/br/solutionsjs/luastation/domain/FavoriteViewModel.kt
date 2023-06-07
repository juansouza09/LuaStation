package br.solutionsjs.luastation.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.solutionsjs.luastation.data.models.Freelancers
import br.solutionsjs.luastation.data.models.Services
import br.solutionsjs.luastation.data.repositories.FavoritesRepository
import br.solutionsjs.luastation.data.repositories.FavoritesRepositoryImpl

class FavoriteViewModel : ViewModel() {
    private val repository: FavoritesRepository

    init {
        repository = FavoritesRepositoryImpl()
    }

    private val _favoriteFreelancers = MutableLiveData<List<Freelancers?>>()
    val favoriteFreelancers: LiveData<List<Freelancers?>>
        get() = _favoriteFreelancers

    private val _favoriteServices = MutableLiveData<List<Services?>>()
    val favoriteServices: LiveData<List<Services?>>
        get() = _favoriteServices

    fun getFavoriteFreelancers() {
        _favoriteFreelancers.value = repository.getFavoriteFreelancers()
    }

    fun getFavoriteServices() {
        _favoriteServices.value = repository.getFavoriteServices()
    }
}
