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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // אתחול ה-Binding
        _binding = FragmentFoodDetailsBinding.inflate(inflater, container, false)

        // קבלת הנתונים שנשלחו
        val foodName = arguments?.getString("FOOD_NAME")
        val foodPrice = arguments?.getString("FOOD_PRICE")
        val foodImage = arguments?.getInt("FOOD_IMAGE")
        val foodDescription = arguments?.getString("FOOD_DESCRIPTION")

        // עדכון UI עם נתוני המנה
        binding.foodName.text = foodName
        foodImage?.let { binding.foodImage.setImageResource(it) }
        binding.foodDescription.text = foodDescription

        // כפתור חזרה
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(food: Food, description: String): FoodDetailsFragment {
            val fragment = FoodDetailsFragment()
            val bundle = Bundle()
            bundle.putString("FOOD_NAME", food.name)
            bundle.putString("FOOD_PRICE", food.price)
            bundle.putInt("FOOD_IMAGE", food.imageResId)
            bundle.putString("FOOD_DESCRIPTION", description)
            fragment.arguments = bundle
            return fragment
        }
    }
}
