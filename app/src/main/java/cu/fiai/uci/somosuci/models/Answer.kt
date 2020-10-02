package cu.fiai.uci.somosuci.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Answer {
    @Expose
    @SerializedName("possibleItem")
    var possibleItem = ArrayList<ExplainItem>()
}