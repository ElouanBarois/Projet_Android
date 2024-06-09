package fr.epf.min1.flags

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DrawFlagActivity : AppCompatActivity() {

    private lateinit var drawingView: DrawingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_flag)

        drawingView = findViewById(R.id.drawingView)

        val buttonColorRed = findViewById<Button>(R.id.buttonColorRed)
        buttonColorRed.setOnClickListener {
            drawingView.setColor("#FF0000")
        }

        val buttonColorBlue = findViewById<Button>(R.id.buttonColorBlue)
        buttonColorBlue.setOnClickListener {
            drawingView.setColor("#0000FF")
        }

        val buttonColorGreen = findViewById<Button>(R.id.buttonColorGreen)
        buttonColorGreen.setOnClickListener {
            drawingView.setColor("#00FF00")
        }
        val buttonColorWhite = findViewById<Button>(R.id.buttonColorWhite)
        buttonColorWhite.setOnClickListener {
            drawingView.setColor("#FFFFFF")
        }

        val buttonBrushSmall = findViewById<Button>(R.id.buttonBrushSmall)
        buttonBrushSmall.setOnClickListener {
            drawingView.setBrushSize(10f)
        }

        val buttonBrushMedium = findViewById<Button>(R.id.buttonBrushMedium)
        buttonBrushMedium.setOnClickListener {
            drawingView.setBrushSize(20f)
        }

        val buttonBrushLarge = findViewById<Button>(R.id.buttonBrushLarge)
        buttonBrushLarge.setOnClickListener {
            drawingView.setBrushSize(30f)
        }
        val buttonGoBack = findViewById<Button>(R.id.buttonGoBack)
        buttonGoBack.setOnClickListener {
            finish()
        }
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        buttonClear.setOnClickListener {
            drawingView.clearCanvas()
        }

    }
}
