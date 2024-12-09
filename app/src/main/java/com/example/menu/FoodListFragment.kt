package com.example.menu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import android.widget.Toast
import android.media.MediaPlayer


class FoodListFragment : Fragment() {
    private lateinit var adapter: FoodAdapter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)


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
            Food("Sushi", "$12", R.drawable.sushi),
            Food("Pizza", "$10", R.drawable.pizza),
            Food("Burger", "$8", R.drawable.ham),
            Food("Sushi", "$12", R.drawable.sushi),
            Food("Pizza", "$10", R.drawable.pizza),
            Food("Burger", "$8", R.drawable.ham),
            Food("Sushi", "$12", R.drawable.sushi),
            Food("Pizza", "$10", R.drawable.pizza),
            Food("Burger", "$8", R.drawable.ham),
            Food("Sushi", "$12", R.drawable.sushi),
            Food("Pizza", "$10", R.drawable.pizza),
            Food("Burger", "$8", R.drawable.ham),
            Food("Sushi", "$12", R.drawable.sushi),
            Food("Pizza", "$10", R.drawable.pizza),
            Food("Burger", "$8", R.drawable.ham),
            Food("Sushi", "$12", R.drawable.sushi),
            Food("Pizza", "$10", R.drawable.pizza),
            Food("Burger", "$8", R.drawable.ham),
            Food("Sushi", "$12", R.drawable.sushi)

        )

        adapter = FoodAdapter(foodList, { food ->
            Toast.makeText(context, "View Details: ${food.name}", Toast.LENGTH_SHORT).show()
        }, { food ->
            Toast.makeText(context, "Order: ${food.name}", Toast.LENGTH_SHORT).show()
        })

        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 פריטים בשורה
        recyclerView.adapter = adapter

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