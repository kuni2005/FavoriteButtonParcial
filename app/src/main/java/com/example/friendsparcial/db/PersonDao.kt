package com.example.friendsparcial.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.friendsparcial.models.Person

@Dao
interface PersonDao {

    @Insert
    fun insertOne(person: Person)
    @Query("SELECT * FROM Person")
    fun getAll(): List<Person>
    @Delete
    fun delete(person: Person)

}