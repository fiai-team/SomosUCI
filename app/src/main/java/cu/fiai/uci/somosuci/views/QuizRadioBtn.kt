package cu.fiai.uci.somosuci.views

import android.content.Context
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatRadioButton

class QuizRadioBtn(context: Context, msg: String) : AppCompatRadioButton(context) {
    init {
        text = msg
        textSize = 16F

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(5, 10, 5, 10)
        layoutParams = params
    }
}