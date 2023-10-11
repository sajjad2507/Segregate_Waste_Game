package com.example.segregateimagegame

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var startX = 0f
    private var startY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var originalX = 0f
    private var originalY = 0f

    val wasteImagesId = arrayOf(
        Triple("w", R.drawable.w1, "Fruit peels"),
        Triple("w", R.drawable.w2, "Vegetable scraps"),
        Triple("w", R.drawable.w3, "Food leftovers"),
        Triple("w", R.drawable.w4, "Coffee grounds"),
        Triple("w", R.drawable.w5, "Tea bags"),
        Triple("w", R.drawable.w6, "Eggshells"),
        Triple("w", R.drawable.w7, "Dairy products"),
        Triple("w", R.drawable.w8, "Potato"),
        Triple("w", R.drawable.w9, "Bakery items"),
        Triple("w", R.drawable.w10, "Garden waste"),
        Triple("d", R.drawable.d1, "Paper"),
        Triple("d", R.drawable.d2, "Cardboard box"),
        Triple("d", R.drawable.d3, "Plastic bottle"),
        Triple("d", R.drawable.d4, "Aluminum cans"),
        Triple("d", R.drawable.d5, "Glass bottle"),
        Triple("d", R.drawable.d7, "Disposable cutlery"),
        Triple("d", R.drawable.d8, "Styrofoam packaging"),
        Triple("d", R.drawable.d9, "Metal cans"),
        Triple("d", R.drawable.d10, "Plastic bags"),
        Triple("s", R.drawable.s1, "Tissues"),
        Triple("s", R.drawable.s2, "Sanitary napkin"),
        Triple("s", R.drawable.s3, "Cotton swabs"),
        Triple("s", R.drawable.s4, "Disposable gloves"),
        Triple("s", R.drawable.s5, "Razor blade"),
        Triple("s", R.drawable.s7, "Diapers"),
        Triple("s", R.drawable.s8, "Mask"),
        Triple("e", R.drawable.e2, "Printer"),
        Triple("e", R.drawable.e3, "Laptop Parts"),
        Triple("e", R.drawable.e4, "Gaming Console"),
        Triple("e", R.drawable.e5, "Juicer Machine"),
        Triple("e", R.drawable.e7, "Batteries"),
        Triple("e", R.drawable.e8, "Television"),
        Triple("e", R.drawable.e10, "Mouse")
    )

    private var currentImageIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val imageView: ImageView = findViewById(R.id.imageView)
        val wasteName: TextView = findViewById(R.id.imageName)
        val sanitaryWaste: ImageView = findViewById(R.id.sanitaryWaste)
        val wetWaste: ImageView = findViewById(R.id.wetWaste)
        val dryWaste: ImageView = findViewById(R.id.dryWaste)
        val eWaste: ImageView = findViewById(R.id.eWaste)

        wasteImagesId.shuffle()

        val id = wasteImagesId[currentImageIndex].second
        wasteName.text = wasteImagesId[currentImageIndex].third
        imageView.setImageDrawable(getDrawable(id))
        currentImageIndex++


        imageView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    // Store the original position of the imageView
                    originalX = view.x
                    originalY = view.y

                    startX = event.rawX
                    startY = event.rawY
                    offsetX = view.x - event.rawX
                    offsetY = view.y - event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + offsetX
                    val newY = event.rawY + offsetY
                    view.animate()
                        .x(newX)
                        .y(newY)
                        .setDuration(0)
                        .start()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (isViewOverlapping(imageView, sanitaryWaste)) {
                        checkAnswer("s")
                    } else if (isViewOverlapping(imageView, wetWaste)) {
                        checkAnswer("w")
                    } else if (isViewOverlapping(imageView, dryWaste)) {
                        checkAnswer("d")
                    } else if (isViewOverlapping(imageView, eWaste)) {
                        checkAnswer("e")
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun isViewOverlapping(view1: View, view2: View): Boolean {
        val rect1 = IntArray(2)
        val rect2 = IntArray(2)
        view1.getLocationOnScreen(rect1)
        view2.getLocationOnScreen(rect2)

        val view1Rect = Rect(
            rect1[0], rect1[1], rect1[0] + view1.width, rect1[1] + view1.height
        )
        val view2Rect = Rect(
            rect2[0], rect2[1], rect2[0] + view2.width, rect2[1] + view2.height
        )

        return view1Rect.intersect(view2Rect)
    }

    private fun checkAnswer(selectedBin: String) {
        val imageName = wasteImagesId[currentImageIndex - 1].first

        if (selectedBin == imageName ||
            selectedBin == imageName ||
            selectedBin == imageName ||
            selectedBin == imageName
        ) {

            score++
            Toast.makeText(
                this, "Correct! Score: $score", Toast.LENGTH_SHORT
            ).show()
            val scoreTv = findViewById<TextView>(R.id.hitScoreTv)
            scoreTv.text = score.toString()

            val id = wasteImagesId[currentImageIndex].second
            val imageView: ImageView = findViewById(R.id.imageView)
            val wasteName: TextView = findViewById(R.id.imageName)

            // Reset the image view's position using animation
            imageView.animate()
                .x(originalX)
                .y(originalY)
                .setDuration(0)
                .withStartAction {
                    // Fade out the image view while moving
                    imageView.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .start()
                }
                .withEndAction {
                    // Fade in the image view when it returns to its original position
                    imageView.animate()
                        .alpha(1f)
                        .setDuration(200)
                        .start()
                }

            imageView.setImageDrawable(getDrawable(id))
            wasteName.text = wasteImagesId[currentImageIndex].third


        } else {
            Toast.makeText(
                this, "Wrong bin! Game Over. Final Score: $score", Toast.LENGTH_SHORT
            ).show()

            val imageView: ImageView = findViewById(R.id.imageView)
            imageView.setImageDrawable(null)

            val dialog = Dialog(MainActivity@ this)
            dialog.setContentView(R.layout.dialog_box)
            dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.dialog_box))

            val dialogBtn = dialog.findViewById<ImageView>(R.id.dialogBtn)
            val dialogHits = dialog.findViewById<TextView>(R.id.dialogHits)
            val dialogCorrectAnswer = dialog.findViewById<TextView>(R.id.dialogCorrectAnswer)

            dialogHits.text = score.toString()
            if (imageName == "w") {
                dialogCorrectAnswer.text = "Wet Bin"
            } else if (imageName == "d") {
                dialogCorrectAnswer.text = "Dry Bin"
            } else if (imageName == "s") {
                dialogCorrectAnswer.text = "Sanitary Bin"
            } else if (imageName == "e") {
                dialogCorrectAnswer.text = "Electronic Bin"
            }

            dialogBtn.setOnClickListener {

                val intent = Intent(this, GetStarted::class.java)
                startActivity(intent)
                dialog.dismiss()

            }

            dialog.setCancelable(false)
            dialog.show()

            return // End the game if the answer is incorrect
        }

        currentImageIndex++
        if (currentImageIndex < wasteImagesId.size) {

        } else {

            val imageView: ImageView = findViewById(R.id.imageView)
            imageView.setImageDrawable(null)

            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_box)
            dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.dialog_box))

            val dialogBtn = dialog.findViewById<ImageView>(R.id.dialogBtn)
            val dialogHits = dialog.findViewById<TextView>(R.id.dialogHits)
            val correctBin = dialog.findViewById<TextView>(R.id.correctBin)

            dialogHits.text = score.toString()
            correctBin.text = ""

            dialogBtn.setOnClickListener {

                val intent = Intent(this, GetStarted::class.java)
                startActivity(intent)
                dialog.dismiss()

            }

            dialog.setCancelable(false)
            dialog.show()

        }
    }
}
