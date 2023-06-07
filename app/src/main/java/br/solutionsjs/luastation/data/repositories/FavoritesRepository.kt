package br.solutionsjs.luastation.data.repositories

import br.solutionsjs.luastation.data.models.Freelancers
import br.solutionsjs.luastation.data.models.Services

interface FavoritesRepository {
    fun getFavoriteFreelancers(): List<Freelancers?>
    fun getFavoriteServices(): List<Services?>
}
