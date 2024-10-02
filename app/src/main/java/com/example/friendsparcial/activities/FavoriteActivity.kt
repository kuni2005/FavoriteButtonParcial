package com.example.friendsparcial.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friendsparcial.R
import com.example.friendsparcial.adapters.PersonAdapter
import com.example.friendsparcial.db.AppDatabase
import com.example.friendsparcial.models.Person

class FavoriteActivity : AppCompatActivity(), PersonAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        recyclerView = findViewById(R.id.rvFavorite)
    }

    override fun onResume(){
        super.onResume()
        loadPeople{ people ->
            recyclerView.adapter = PersonAdapter(people, this)
            recyclerView.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }
    }

    private fun loadPeople(onComplete: (List<Person>) -> Unit){
        val dao = AppDatabase.getInstance(this).getDao()
        onComplete(dao.getAll())
    }


    override fun onItemClick(person: Person) {
        val dao = AppDatabase.getInstance(this).getDao()
        dao.delete(person)
        Toast.makeText(this,"Person " + person.firstName + " deleted from favorites", Toast.LENGTH_SHORT).show()
        loadPeople{ people ->
            recyclerView.adapter = PersonAdapter(people, this)
            recyclerView.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }
    }

}