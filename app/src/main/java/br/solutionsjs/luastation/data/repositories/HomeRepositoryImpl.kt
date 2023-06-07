package br.solutionsjs.luastation.data.repositories

import br.solutionsjs.luastation.data.models.Freelancers
import br.solutionsjs.luastation.data.models.Services
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeRepositoryImpl : HomeRepository {
    private val database by lazy {
        FirebaseDatabase.getInstance()
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun getFreelancers(): List<Freelancers?> {
        val freelancersList: MutableList<Freelancers?> = mutableListOf()

        database.getReference("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val freelancersArrayList = mutableListOf<Freelancers?>()
                if (snapshot.exists()) {
                    for (freelancerSnapshot in snapshot.children) {
                        val freelancer = freelancerSnapshot.getValue(Freelancers::class.java)
                        freelancersArrayList.add(freelancer)
                    }
                    val filteredList =
                        freelancersArrayList.filterNotNull().filter { it.cpf_cnpj?.length == 14 }
                    freelancersList.addAll(filteredList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return freelancersList
    }

    override fun getMyProjects(): List<Services?> {
        val projectsList: MutableList<Services?> = mutableListOf()

        firebaseAuth.currentUser?.uid?.let {
            database.getReference("Users").child(it)
                .child("Meus Projetos").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val servicesArrayList = mutableListOf<Services?>()
                        if (snapshot.exists()) {
                            for (serviceSnapshot in snapshot.children) {
                                val project = serviceSnapshot.getValue(Services::class.java)
                                servicesArrayList.add(project)
                            }
                            val filteredProjectsList = servicesArrayList.filterNotNull()
                            projectsList.addAll(filteredProjectsList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
        return projectsList
    }

    override fun getServices(): List<Services?> {
        val servicesList: MutableList<Services?> = mutableListOf()

        database.getReference("Services").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val servicesArrayList = mutableListOf<Services?>()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val service = serviceSnapshot.getValue(Services::class.java)
                        servicesArrayList.add(service)
                    }
                    val filteredServicesList = servicesArrayList.filterNotNull()
                    servicesList.addAll(filteredServicesList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return servicesList
    }
}
