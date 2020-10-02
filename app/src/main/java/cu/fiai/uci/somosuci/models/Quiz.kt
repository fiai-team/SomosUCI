package cu.fiai.uci.somosuci.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Quiz {

    @Expose
    @SerializedName("about")
    var about: String = ""

    @Expose
    @SerializedName("answerTitle")
    var answerTitle: String = ""

    @Expose
    @SerializedName("answerList")
    var answerList = ArrayList<Answer>()

    @Expose
    @SerializedName("questionList")
    var questionList = ArrayList<Question>()

}