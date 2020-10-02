package cu.fiai.uci.somosuci.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.models.Quiz
import cu.fiai.uci.somosuci.viewmodels.MainViewModel

class QuizAdapter(
    val context: Context,
    private val mainViewModel: MainViewModel, val values: ArrayList<Quiz>
) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quiz, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.apply {
            title.text = item.about

            itemView.setOnClickListener {
                mainViewModel.cont = 0
                mainViewModel.selectedQuiz = item
                mainViewModel.answersPoints.clear()
                item.answerList.forEach { _ ->
                    mainViewModel.answersPoints.add(0)
                }
                it.findNavController().navigate(R.id.action_gameSelectFragment_to_gameQuizFragment)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id._quizTitle)
    }

}