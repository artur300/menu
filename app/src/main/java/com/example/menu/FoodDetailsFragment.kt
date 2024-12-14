package com.example.menu

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.menu.databinding.FragmentFoodDetailsBinding

class FoodDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFoodDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodDetailsBinding.inflate(inflater, container, false)

        // קבלת הנתונים מה-Bundle
        val foodName = arguments?.getString("food_name") ?: "No Name"
        val foodImageUri = arguments?.getString("food_image_uri")
        val foodDescription = arguments?.getString("food_description") ?: "No Description" // תיאור

        // הצגת הנתונים ב-UI
        binding.foodName.text = foodName
        binding.foodDescription.setText(foodDescription) // הצגת התיאור
        foodImageUri?.let {
            binding.foodImage.setImageURI(Uri.parse(it))
        }

        return binding.root
    }
}
