package br.solutionsjs.luastation.data.repositories

import br.solutionsjs.luastation.data.models.Freelancers
import br.solutionsjs.luastation.data.models.Services

interface HomeRepository {

    fun getFreelancers(): List<Freelancers?>
    fun getMyProjects(): List<Services?>
    fun getServices(): List<Services?>
}
