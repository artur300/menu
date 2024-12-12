package com.example.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import com.example.menu.databinding.ItemFoodCardBinding

class FoodAdapter(
    private val foods: List<Food>,
    private val onViewDetailsClick: (Food) -> Unit,
    private val onOrderClick: (Food) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    // שימוש ב-Binding עבור ה-ViewHolder
    class FoodViewHolder(val binding: ItemFoodCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]

        // הגדרת הנתונים ב-UI באמצעות Binding
        holder.binding.foodName.text = food.name
        holder.binding.foodPrice.text = food.price
        holder.binding.foodImage.setImageResource(food.imageResId)

        // טיפול בלחיצה על View Details
        holder.binding.btnViewDetails.setOnClickListener {
            val fragment = FoodDetailsFragment.newInstance(food)
            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit()
        }

        // טיפול בלחיצה על Order
        holder.binding.btnOrder.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(holder.itemView.context, R.raw.yeehaw)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { it.release() }

            onOrderClick(food)
        }
    }

    override fun getItemCount(): Int = foods.size
}

