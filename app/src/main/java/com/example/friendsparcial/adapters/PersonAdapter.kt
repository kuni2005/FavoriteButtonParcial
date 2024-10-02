package com.example.friendsparcial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.friendsparcial.R
import com.example.friendsparcial.models.Person
import com.squareup.picasso.Picasso

class PersonAdapter(private val people: List<Person>, private val clickListener: OnItemClickListener):
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {
    inner class  PersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvCell: TextView = itemView.findViewById(R.id.tvCel)
        private val like: ImageButton = itemView.findViewById(R.id.btLike)
        private val ivPhoto: ImageView = itemView.findViewById(R.id.ivPerson)

        fun bind(person: Person, clickListener: OnItemClickListener){
            tvName.text = person.firstName + " " + person.lastName
            tvCell.text = person.cell

            Picasso.get()
                .load(person.picture)
                .into(ivPhoto)

            like.setOnClickListener{
                clickListener.onItemClick(person)
            }
    }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_user, parent, false)
        return  PersonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(people[position], clickListener)
    }

    interface OnItemClickListener{
        fun onItemClick(person: Person)
    }
}