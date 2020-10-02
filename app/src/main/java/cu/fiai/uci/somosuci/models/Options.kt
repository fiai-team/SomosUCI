package cu.fiai.uci.somosuci.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Options {
    @Expose
    @SerializedName("text")
    var title = ""

    @Expose
    @SerializedName("pointToList")
    var pointToList = ArrayList<Int>()
}