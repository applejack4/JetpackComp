package com.example.jetpackcomp.View.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jetpackcomp.Model.entities.JetpackComp
import com.example.jetpackcomp.R
import com.example.jetpackcomp.View.Activities.AddUpdate
import com.example.jetpackcomp.View.Fragments.FaviorateDishes
import com.example.jetpackcomp.View.Fragments.HomeFragment
import com.example.jetpackcomp.databinding.DishItemBinding
import com.example.jetpackcomp.utils.Constants

class favDishAdapter(private val fragment : Fragment) : RecyclerView.Adapter<favDishAdapter.MyViewHolder>() {

    private var dishes : List<JetpackComp> = listOf()

    class MyViewHolder(view: DishItemBinding) : RecyclerView.ViewHolder(view.root){
        val ivDishImage = view.ivDishImage
        val tvTitle =view.tvTitle
        val ibMore = view.ibMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : DishItemBinding = DishItemBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dish = dishes[position]
        Glide.with(fragment).load(dish.image).into(holder.ivDishImage)
        holder.tvTitle.text = dish.title

        holder.itemView.setOnClickListener{
            if(fragment is HomeFragment){
                fragment.dishDetails(dish)
            }

            if(fragment is FaviorateDishes){
                fragment.dishDetails(dish)
            }
        }

        holder.ibMore.setOnClickListener{
            val popup = PopupMenu(fragment.context, holder.ibMore)
            popup.menuInflater.inflate(R.menu.menu_adapter, popup.menu)

            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.action_edit_dish){
                    val intent = Intent(fragment.requireActivity(), AddUpdate::class.java)
                    intent.putExtra(Constants.EXTRA_DISH_DETAILS, dish)
                    fragment.requireActivity().startActivity(intent)
                } else if(it.itemId == R.id.action_delete_dish){
                    if(fragment is HomeFragment){
                        fragment.deleteDish(dish)
                    }
                }
                true
            }
            popup.show()
        }
        if(fragment is HomeFragment){
            holder.ibMore.visibility = View.VISIBLE
        }else if(fragment is FaviorateDishes){
            holder.ibMore.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun dishesList(list: List<JetpackComp>){
        dishes = list
        notifyDataSetChanged()
    }
}