package com.example.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.widget.Toast
import android.media.MediaPlayer
import com.example.menu.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {
    private lateinit var binding: FragmentFoodListBinding
    private lateinit var adapter: FoodAdapter
    private lateinit var mediaPlayer: MediaPlayer
    private var cartCount = 0
    private val cartItems = mutableListOf<Food>()

    private fun addToCart(food: Food) {
        cartCount++
        cartItems.add(food)
        binding.cartCount.text = cartCount.toString() // עדכון המונה
        CartAnimation.animateCartCount(binding.cartCount)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // אתחול ה-Binding
        binding = FragmentFoodListBinding.inflate(inflater, container, false)
        val view = binding.root

        // הגדרת MediaPlayer
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.wild_west_music)
        mediaPlayer.isLooping = true // הפעלת לולאה
        mediaPlayer.start()

        // יצירת רשימה לדוגמה
        val foodList = listOf(
            Food("Pizza", "$10", R.drawable.pizza),
            Food("Burger", "$8", R.drawable.ham),
            Food("Sushi", "$12", R.drawable.sushi),
            Food("Pizza", "$10", R.drawable.pizza),
            Food("Burger", "$8", R.drawable.ham),
            Food("Sushi", "$12", R.drawable.sushi)
        )

        // אתחול ה-RecyclerView
        adapter = FoodAdapter(foodList, { food ->
            context?.let {
                val message = it.getString(R.string.view_details_message, food.name)
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }, { food ->
            addToCart(food) // הוספה לעגלה בעת לחיצה על "Order Now"
            context?.let {
                val message = it.getString(R.string.order_message, food.name)
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 פריטים בשורה
        binding.recyclerView.adapter = adapter

        return view
    }

    override fun onPause() {
        super.onPause()
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::mediaPlayer.isInitialized && !mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }
}
