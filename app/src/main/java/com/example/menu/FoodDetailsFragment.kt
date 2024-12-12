package com.example.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.menu.databinding.FragmentFoodDetailsBinding

class FoodDetailsFragment : Fragment() {
    private var _binding: FragmentFoodDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // אתחול View Binding
        _binding = FragmentFoodDetailsBinding.inflate(inflater, container, false)

        // קבלת הנתונים שנשלחו
        val foodName = arguments?.getString("FOOD_NAME")
        val foodPrice = arguments?.getString("FOOD_PRICE")
        val foodImage = arguments?.getInt("FOOD_IMAGE")

        // קישור רכיבי ה-UI והצגת הנתונים
        binding.foodName.text = foodName ?: getString(R.string.unknown_food) // לוקליזציה
        binding.foodPrice.text = getString(R.string.price_format, foodPrice ?: "N/A") // לוקליזציה עם תבנית
        foodImage?.let { binding.foodImage.setImageResource(it) }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // ניקוי ה-Binding
    }

    companion object {
        fun newInstance(food: Food): FoodDetailsFragment {
            val fragment = FoodDetailsFragment()
            val bundle = Bundle()
            bundle.putString("FOOD_NAME", food.name)
            bundle.putString("FOOD_PRICE", food.price)
            bundle.putInt("FOOD_IMAGE", food.imageResId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
