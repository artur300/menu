package com.example.menu

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menu.databinding.ItemFoodCardBinding

class FoodAdapter(
    private val foods: List<Food>,
    private val onViewDetailsClick: (Food) -> Unit,
    private val onOrderClick: (Food) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(val binding: ItemFoodCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]

        // עדכון שם ומחיר המנה
        holder.binding.foodName.text = food.name
        holder.binding.foodPrice.text = food.price

        // עדכון התמונה: תמונה מ-URI אם קיימת, אחרת תמונה דיפולטיבית
        if (!food.imageUri.isNullOrEmpty()) {
            holder.binding.foodImage.setImageURI(Uri.parse(food.imageUri))
        } else {
            holder.binding.foodImage.setImageResource(R.drawable.ic_launcher_foreground) // תמונה דיפולטיבית
        }

        // מאזין ללחיצה על כפתור פרטים נוספים
        holder.binding.btnViewDetails.setOnClickListener {
            onViewDetailsClick(food)
        }

        // מאזין ללחיצה על כפתור הזמנה
        holder.binding.btnOrder.setOnClickListener {
            onOrderClick(food)
        }
    }

    override fun getItemCount(): Int = foods.size
}
