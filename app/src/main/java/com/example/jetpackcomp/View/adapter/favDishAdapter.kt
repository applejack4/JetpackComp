package com.example.jetpackcomp.View.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jetpackcomp.Model.entities.JetpackComp
import com.example.jetpackcomp.databinding.DishItemBinding

class favDishAdapter(private val fragment : Fragment) : RecyclerView.Adapter<favDishAdapter.MyViewHolder>() {

    private var dishes : List<JetpackComp> = listOf()

    class MyViewHolder(view: DishItemBinding) : RecyclerView.ViewHolder(view.root){
        val ivDishImage = view.ivDishImage
        val tvTitle =view.tvTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : DishItemBinding = DishItemBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dish = dishes[position]
        Glide.with(fragment).load(dish.image).into(holder.ivDishImage)
        holder.tvTitle.text = dish.title
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    fun dishesList(list: List<JetpackComp>){
        dishes = list
        notifyDataSetChanged()
    }
}