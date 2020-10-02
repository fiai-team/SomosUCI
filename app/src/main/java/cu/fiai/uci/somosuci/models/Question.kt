package cu.fiai.uci.somosuci.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Question {
    @Expose
    @SerializedName("title")
    var title = ""

    @Expose
    @SerializedName("options")
    var options = ArrayList<Options>()
}