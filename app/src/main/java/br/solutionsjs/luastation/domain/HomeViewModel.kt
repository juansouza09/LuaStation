package br.solutionsjs.luastation.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.solutionsjs.luastation.data.models.Freelancers
import br.solutionsjs.luastation.data.models.Services
import br.solutionsjs.luastation.data.repositories.HomeRepository
import br.solutionsjs.luastation.data.repositories.HomeRepositoryImpl

class HomeViewModel() : ViewModel() {
    private val repository: HomeRepository

    init {
        repository = HomeRepositoryImpl()
    }

    private val _freelancers = MutableLiveData<List<Freelancers?>>()
    val freelancers: LiveData<List<Freelancers?>>
        get() = _freelancers

    private val _services = MutableLiveData<List<Services?>>()
    val services: LiveData<List<Services?>>
        get() = _services

    private val _myProjects = MutableLiveData<List<Services?>>()
    val myProjects: LiveData<List<Services?>>
        get() = _myProjects

    fun getFreelancers() {
        _freelancers.value = repository.getFreelancers()
    }

    fun getMyProjects() {
        _myProjects.value = repository.getMyProjects()
    }

    fun getServices() {
        _services.value = repository.getServices()
    }
}
