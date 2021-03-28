package com.example.ff.fragments.fridge
import android.text.Layout
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import com.example.ff.R
import com.example.ff.fragments.update.UpdateFragment
import com.example.ff.model.FridgeItem

class FridgeAdapter: RecyclerView.Adapter<FridgeAdapter.MyViewHolder>() {
    private var itemList = emptyList<FridgeItem>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemView.findViewById<TextView>(R.id.id_text).text = currentItem.id.toString()

        holder.itemView.findViewById<TextView>(R.id.foodName_row).text = currentItem.name
        holder.itemView.findViewById<TextView>(R.id.expDate_row).text = currentItem.expDate
        holder.itemView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.rowLayout).setOnClickListener{
            val action = FridgeFragmentDirections.actionFridgeFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(item: List<FridgeItem>){
        this.itemList = item
        notifyDataSetChanged()
    }
}