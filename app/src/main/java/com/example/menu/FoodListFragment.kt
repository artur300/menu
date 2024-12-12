package com.example.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.media.MediaPlayer
import androidx.appcompat.app.AlertDialog
import android.animation.ObjectAnimator
import com.example.menu.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {
    private var isMenuOpen = false
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

        // Listener לאייקון התפריט
        binding.menuIcon.setOnClickListener {
            isMenuOpen = !isMenuOpen // שינוי המצב (פתיחה/סגירה)
            toggleMenuAnimation(it, isMenuOpen) // הפעלת האנימציה
            if (isMenuOpen) {
                showMenuDialog() // הצגת התפריט
            }
        }

        val view = binding.root

        // הגדרת MediaPlayer
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.wild_west_music)
        mediaPlayer.isLooping = true // הפעלת לולאה
        mediaPlayer.start()

        // יצירת רשימה לדוגמה
        val foodList = listOf(
            Food("Pizza", "$10", R.drawable.pizza),
            Food("Burger", "$8", R.drawable.ham),
            Food("Sushi", "$12", R.drawable.sushi)
        )

        // אתחול ה-RecyclerView
        adapter = FoodAdapter(foodList, { food ->
            // פעולה לדוגמה
        }, { food ->
            addToCart(food) // הוספה לעגלה
        })

        binding.recyclerView.layoutManager = GridLayoutManager(context, 1)
        binding.recyclerView.adapter = adapter

        return view
    }

    private fun toggleMenuAnimation(view: View, isOpening: Boolean) {
        val rotationAngle = if (isOpening) 90f else 0f // סיבוב ל-90 מעלות כשנפתח
        ObjectAnimator.ofFloat(view, "rotation", rotationAngle).apply {
            duration = 300 // משך האנימציה
            start()
        }
    }

    private fun showMenuDialog() {
        // שימוש בעיצוב מותאם אישית
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_menu, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView) // שימוש ב-Layout מותאם
            .create()

        // קישור כפתורים מתוך ה-Layout
        dialogView.findViewById<View>(R.id.btn_view_order).setOnClickListener {
            // פעולה לבחירת "View Order"
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.btn_go_back).setOnClickListener {
            // פעולה לבחירת "Go Back"
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.btn_settings).setOnClickListener {
            // פעולה לבחירת "Settings"
            dialog.dismiss()
        }

        // מאזין לסגירת הדיאלוג
        dialog.setOnDismissListener {
            isMenuOpen = false // עדכון מצב התפריט
            toggleMenuAnimation(binding.menuIcon, false) // החזרת האייקון למצבו המקורי
        }

        dialog.show()
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
