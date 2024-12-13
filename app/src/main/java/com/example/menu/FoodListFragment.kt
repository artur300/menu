package com.example.menu

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.activity.result.contract.ActivityResultContracts
import com.example.menu.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding
    private lateinit var adapter: FoodAdapter
    private val foodList = mutableListOf<Food>()

    private var selectedImageUri: Uri? = null
    private var imagePreview: ImageView? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            imagePreview?.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(inflater, container, false)

        adapter = FoodAdapter(
            foodList,
            onDeleteClick = { food -> removeFood(food) },
            onEditClick = { food -> showEditFoodDialog(food) }
        )

        binding.recyclerView.layoutManager = GridLayoutManager(context, 1)
        binding.recyclerView.adapter = adapter

        binding.menuIcon.setOnClickListener {
            showAddFoodDialog()
        }

        return binding.root
    }

    private fun addNewFood(name: String, imageUri: String) {
        val newFood = Food(name = name, imageUri = imageUri)
        foodList.add(newFood)
        adapter.notifyItemInserted(foodList.size - 1)
    }

    private fun removeFood(food: Food) {
        val position = foodList.indexOf(food)
        if (position != -1) {
            foodList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }

    private fun showAddFoodDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_food_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        imagePreview = dialogView.findViewById(R.id.food_image_preview)
        val pickImageButton = dialogView.findViewById<Button>(R.id.btn_pick_image)

        pickImageButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        dialogView.findViewById<View>(R.id.btn_add_food).setOnClickListener {
            val name = dialogView.findViewById<EditText>(R.id.food_name_input).text.toString()

            if (name.isNotBlank() && selectedImageUri != null) {
                addNewFood(name, selectedImageUri.toString())
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showEditFoodDialog(food: Food) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_food_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        imagePreview = dialogView.findViewById(R.id.food_image_preview)
        val nameInput = dialogView.findViewById<EditText>(R.id.food_name_input)
        val pickImageButton = dialogView.findViewById<Button>(R.id.btn_pick_image)

        nameInput.setText(food.name)
        food.imageUri?.let {
            selectedImageUri = Uri.parse(it)
            imagePreview?.setImageURI(selectedImageUri)
        }

        pickImageButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        dialogView.findViewById<View>(R.id.btn_add_food).setOnClickListener {
            val newName = nameInput.text.toString()

            if (newName.isNotBlank() && selectedImageUri != null) {
                food.name = newName
                food.imageUri = selectedImageUri.toString()
                adapter.notifyItemChanged(foodList.indexOf(food))
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
