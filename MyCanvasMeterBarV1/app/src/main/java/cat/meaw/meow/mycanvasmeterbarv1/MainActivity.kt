package cat.meaw.meow.mycanvasmeterbarv1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        meter1.setProgressColor(Color.YELLOW)
        meter1.setProgressInsideColor(Color.GREEN)
        meter1.setProgressBackgroundColor(Color.GRAY)
        meter1.setProgressWidth(45f)

        btn_next.setOnClickListener {
            lifecycleScope.launch {
                for(i in 1..50){
                    meter1.setProgress(i.toFloat())
                    txt_value.text = "$i%"
                    delay(16)
                }
            }
        }
        btn_back.setOnClickListener {
            lifecycleScope.launch {
                for(i in 50 downTo 1){
                    meter1.setProgress(i.toFloat())
                    txt_value.text = "$i%"
                    delay(80)
                }
            }
        }
    }
}