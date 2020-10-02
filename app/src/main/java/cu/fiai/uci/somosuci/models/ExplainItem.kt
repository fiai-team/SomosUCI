package cu.fiai.uci.somosuci.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ExplainItem {
    @Expose
    @SerializedName("title")
    var title = ""

    @Expose
    @SerializedName("explication")
    var explication = ""
}