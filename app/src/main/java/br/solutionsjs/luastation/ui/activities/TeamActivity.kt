package br.solutionsjs.luastation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.solutionsjs.luastation.ui.adapters.TeamAdapter
import br.solutionsjs.luastation.databinding.ActivityTeamBinding
import br.solutionsjs.luastation.data.models.Team

class TeamActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTeamBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
        setUpRv()
    }

    private fun setUpRv() {
        val equipeList: MutableList<Team> = mutableListOf()

        val juan = Team(
            "Juan Souza",
            "Desenvolvedor Android",
            "https://firebasestorage.googleapis.com/v0/b/lua-station.appspot.com/o/juan.png?alt=media&token=cb54cd80-6938-42d0-8103-ba51971820fe"
        )

        val filipe = Team(
            "Filipe Sottili",
            "Desenvolvedor Front-end",
            "https://firebasestorage.googleapis.com/v0/b/lua-station.appspot.com/o/fe.png?alt=media&token=e04ed9f4-9260-4d48-85c9-3cd23b4c34b8"
        )

        val maxsuel = Team(
            "Maxsuel Souza",
            "Designer",
            "https://firebasestorage.googleapis.com/v0/b/lua-station.appspot.com/o/max.png?alt=media&token=a9d20774-1e9c-4d1a-bab7-c186d9b92906"
        )

        val jonas = Team(
            "Jonas Henrique",
            "Designer",
            "https://firebasestorage.googleapis.com/v0/b/lua-station.appspot.com/o/jonas.png?alt=media&token=d6e99358-6806-4240-a4ec-9ce594f54c32"
        )

        val guilherme = Team(
            "Guilherme Serafim",
            "Administrador",
            "https://firebasestorage.googleapis.com/v0/b/lua-station.appspot.com/o/gui.png?alt=media&token=cd78fe5e-98e1-453b-987d-74d01fe36a1f"
        )

        val newana = Team(
            "Newana Victória",
            "Administradora",
            "https://firebasestorage.googleapis.com/v0/b/lua-station.appspot.com/o/new.png?alt=media&token=b036b7cf-4b62-499e-b6c9-f03e42ff72f1"
        )
        equipeList.add(juan)
        equipeList.add(filipe)
        equipeList.add(maxsuel)
        equipeList.add(jonas)
        equipeList.add(guilherme)
        equipeList.add(newana)
        binding.recyclerEquipe.layoutManager
        binding.recyclerEquipe.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerEquipe.setHasFixedSize(true)
        adapter = TeamAdapter()
        adapter.submitList(equipeList)
        binding.recyclerEquipe.adapter = adapter
    }

    private fun setupListeners() {
        binding.icBackEquipeActivity.let {
            it.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
