package com.example.friendsparcial.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friendsparcial.R
import com.example.friendsparcial.adapters.PersonAdapter
import com.example.friendsparcial.communication.ApiResponse
import com.example.friendsparcial.db.AppDatabase
import com.example.friendsparcial.models.Person
import com.example.friendsparcial.network.RandomUserApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PeopleActivity : AppCompatActivity(), PersonAdapter.OnItemClickListener  {

    private lateinit var btnLoad: Button
    private lateinit var etResults: EditText
    private lateinit var rvPeople: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people)

        setSupportActionBar(findViewById(R.id.toolbar))
        btnLoad = findViewById(R.id.btLoadPeople)
        etResults = findViewById(R.id.etPeopleCount)
        rvPeople = findViewById(R.id.rvPeople)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
    override fun onResume(){
        super.onResume()
        btnLoad.setOnClickListener {
            val results = etResults.text.toString().toInt()
            loadPeople(results) { people ->
                rvPeople.adapter = PersonAdapter(people, this)//NO SE OLVIDEEEEN
                rvPeople.layoutManager = LinearLayoutManager(this@PeopleActivity)
            }
        }
    }

    private fun loadPeople(results: Int, onComplete: (List<Person>)->Unit) {
        var count = results
        val retrofit= Retrofit.Builder()
            .baseUrl("https://randomuser.me/") //cambia
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val randomUserService : RandomUserApiService = retrofit.create(RandomUserApiService::class.java)

        val request = randomUserService.getUsers(count)
        request.enqueue(object: Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val userApiUsers : ApiResponse = response.body()!!
                    val personList = mutableListOf<Person>()

                    userApiUsers.results?.forEach {
                        personList.add(it.toPerson())
                    }

                    onComplete(personList)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })




    }
    override fun onItemClick(person : Person) {
        val dao = AppDatabase.getInstance(this).getDao()
        dao.insertOne(person)

        Toast.makeText(this, "Person "+person.firstName+" added to favorites", Toast.LENGTH_SHORT).show()
    }


}