package br.solutionsjs.luastation.data.repositories

import br.solutionsjs.luastation.data.models.Freelancers
import br.solutionsjs.luastation.data.models.Services
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavoritesRepositoryImpl : FavoritesRepository {
    private val database by lazy {
        FirebaseDatabase.getInstance()
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val firebaseUser = firebaseAuth.currentUser

    override fun getFavoriteFreelancers(): List<Freelancers?> {
        val favoriteFreelancersList: MutableList<Freelancers?> = mutableListOf()

        if (firebaseUser != null) {
            database.getReference("Users").child((firebaseUser.uid))
                .child("FreelancerFav").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val favoriteFreelancersArrayList = mutableListOf<Freelancers?>()
                        if (snapshot.exists()) {
                            for (freelancerSnapshot in snapshot.children) {
                                val favoriteFreelancer =
                                    freelancerSnapshot.getValue(Freelancers::class.java)
                                favoriteFreelancersArrayList.add(favoriteFreelancer)
                            }
                            val filteredFavoriteFreelancersList =
                                favoriteFreelancersArrayList.filterNotNull()
                                    .filter { it.cpf_cnpj?.length == 14 }
                            favoriteFreelancersList.addAll(filteredFavoriteFreelancersList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
        return favoriteFreelancersList
    }

    override fun getFavoriteServices(): List<Services?> {
        val favoriteServicesList: MutableList<Services?> = mutableListOf()

        if (firebaseUser != null) {
            database.getReference("Users").child((firebaseUser.uid))
                .child("ServicosFav").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val favoriteServicesArrayList = mutableListOf<Services?>()
                        if (snapshot.exists()) {
                            for (serviceSnapshot in snapshot.children) {
                                val favoriteService = serviceSnapshot.getValue(Services::class.java)
                                favoriteServicesArrayList.add(favoriteService)
                            }
                            val filteredFavoriteServicesList =
                                favoriteServicesArrayList.filterNotNull()
                            favoriteServicesList.addAll(filteredFavoriteServicesList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
        return favoriteServicesList
    }
}
