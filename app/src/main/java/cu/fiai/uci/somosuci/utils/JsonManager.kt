package cu.fiai.uci.somosuci.utils

import android.content.Context

import com.google.gson.Gson
import cu.fiai.uci.somosuci.models.AboutInfo
import cu.fiai.uci.somosuci.models.ContactInfo
import cu.fiai.uci.somosuci.models.CoursesInfo
import cu.fiai.uci.somosuci.models.Quiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class JsonManager(private val context: Context, private val lang: String = LANG_ES) {

    private suspend fun loadTextFromAssets(fileName: String) = withContext(Dispatchers.IO) {
        val finalFilename = "${fileName}_$lang.json"
        var json = ""
        try {
            val inputStream = context.assets.open(finalFilename)

            val isr = InputStreamReader(inputStream)
            val br = BufferedReader(isr)

            val lines = br.readLines()
            lines.forEach { line ->
                json += line
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return@withContext json
    }

    suspend fun getQuiz(quizPath: String): Quiz = withContext(Dispatchers.IO) {
        val text = loadTextFromAssets(quizPath)
        return@withContext Gson().fromJson(text, Quiz::class.java)
    }

    suspend fun aboutInfo() = withContext(Dispatchers.IO) {
        val text = loadTextFromAssets(ABOUT_INFO_FILE)
        return@withContext Gson().fromJson(text, AboutInfo::class.java)
    }

    suspend fun contactInfo() = withContext(Dispatchers.IO) {
        val text = loadTextFromAssets(CONTACT_INFO_FILE)
        return@withContext Gson().fromJson(text, ContactInfo::class.java)
    }

    suspend fun courseInfo(course: String) = withContext(Dispatchers.IO) {
        val text = loadTextFromAssets(course)
        return@withContext Gson().fromJson(text, CoursesInfo::class.java)
    }

    companion object {
        const val QUIZ_1 = "quiz1"
        const val QUIZ_2 = "quiz2"
        const val QUIZ_3 = "quiz3"

        const val CONTACT_INFO_FILE = "contactInfo"
        const val ABOUT_INFO_FILE = "aboutInfo"
        const val ICI_INFO = "iciInfo"
        const val IBI_INFO = "ibiInfo"
        const val RED_INFO = "redInfo"
        const val CS_INFO = "csInfo"

        const val LANG_ES = "es-ES"
        const val LANG_EN = "en-EN"
        const val LANG_PT = "pt-PT"
        const val LANG_IT = "it-IT"

        val QUIZZES = arrayListOf(QUIZ_1, QUIZ_2, QUIZ_3)
    }
}