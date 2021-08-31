package com.example.jetpackcomp.View.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomp.View.Activities.AddUpdate
import com.example.jetpackcomp.View.Fragments.HomeFragment
import com.example.jetpackcomp.databinding.ItemCustomListBinding

class CustomItemListAdapter(
    private val activity : Activity,
    private val fragment : Fragment?,
    private val listItems : List<String>,
    private val selectedItem : String): RecyclerView.Adapter<CustomItemListAdapter.MyViewHolder>() {

        class MyViewHolder(view: ItemCustomListBinding): RecyclerView.ViewHolder(view.root){
            val tvText = view.tvTextItem

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : ItemCustomListBinding = ItemCustomListBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = listItems[position]
        holder.tvText.text = items

        holder.itemView.setOnClickListener{
            if(activity is AddUpdate){
                activity.selectedListItem(items, selectedItem)
            }
            if(fragment is HomeFragment){
                fragment.filterSelection(items)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}