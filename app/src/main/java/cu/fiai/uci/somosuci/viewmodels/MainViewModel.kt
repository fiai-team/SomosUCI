package cu.fiai.uci.somosuci.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cu.fiai.uci.somosuci.models.CoursesInfo
import cu.fiai.uci.somosuci.models.Quiz

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val actualFragment = MutableLiveData(1)

    //course section
    var selectedCourse = CoursesInfo()
    var rol = ""
    var backgroundColor = 0
    var foregroundColor = 0


    //quiz section
    var selectedQuiz = Quiz()
    var cont = 0
    var answersPoints = ArrayList<Int>()

}