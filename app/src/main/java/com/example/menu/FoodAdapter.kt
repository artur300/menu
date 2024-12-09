package com.example.menu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.media.MediaPlayer


class FoodAdapter(
    private val foods: List<Food>,
    private val onViewDetailsClick: (Food) -> Unit,
    private val onOrderClick: (Food) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.food_image)
        val name: TextView = itemView.findViewById(R.id.food_name)
        val price: TextView = itemView.findViewById(R.id.food_price)
        val viewDetails: Button = itemView.findViewById(R.id.btn_view_details)
        val order: Button = itemView.findViewById(R.id.btn_order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_card, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]
        holder.name.text = food.name
        holder.price.text = food.price
        holder.image.setImageResource(food.imageResId) // תשתמש בנתוני התמונה
        holder.viewDetails.setOnClickListener { onViewDetailsClick(food) }
        holder.order.setOnClickListener {


            val mediaPlayer = MediaPlayer.create(holder.itemView.context, R.raw.yeehaw)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {it.release()}


            onOrderClick(food)
        }
    }

    override fun getItemCount(): Int = foods.size
}
