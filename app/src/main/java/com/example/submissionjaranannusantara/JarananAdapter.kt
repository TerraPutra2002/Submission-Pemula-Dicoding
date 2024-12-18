package com.example.submissionjaranannusantara

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class JarananAdapter (private val listJaranan: ArrayList<Jaranan>) : RecyclerView.Adapter<JarananAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Jaranan)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_photo_item)
        val viewName: TextView = itemView.findViewById(R.id.view_item_name)
        val viewDescription: TextView = itemView.findViewById(R.id.view_item_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_jaranan, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listJaranan.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listJaranan[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.imgPhoto)
        holder.viewName.text = name
        holder.viewDescription.text = description
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailJaranan::class.java)
            intentDetail.putExtra("key_jaranan", listJaranan[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)

        }
    }

}