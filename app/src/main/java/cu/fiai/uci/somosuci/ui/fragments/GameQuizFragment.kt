package cu.fiai.uci.somosuci.ui.fragments

import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.animation.doOnEnd
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.models.ExplainItem
import cu.fiai.uci.somosuci.models.Question
import cu.fiai.uci.somosuci.views.QuizRadioBtn
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.dialog_end_quiz.*
import kotlinx.android.synthetic.main.fragment_game_quiz.*
import kotlin.random.Random

class GameQuizFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel.actualFragment.value = 0
        return inflater.inflate(R.layout.fragment_game_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView()
    }

    private fun configView() {
        navController = findNavController()

        _quizTitle.text = mainViewModel.selectedQuiz.about

        if (mainViewModel.selectedQuiz.about.isEmpty()) navController.popBackStack(R.id.mainFragment, false)
        else setQuestion()
    }

    private fun setQuestion() {
        _questionItems.removeAllViews()
        val actualQuestion = mainViewModel.selectedQuiz.questionList[mainViewModel.cont]
        _questionText.text = actualQuestion.title

        val arrayOfRadioBtns = ArrayList<QuizRadioBtn>()
        actualQuestion.options.forEach {
            val quizRadioBtn = QuizRadioBtn(requireContext(), it.title)
            arrayOfRadioBtns.add(quizRadioBtn)
            _questionItems.addView(quizRadioBtn)
        }

        (_questionItems[0] as RadioButton).isChecked = true

        _continueBtn.setOnClickListener {
            actionGo(arrayOfRadioBtns, actualQuestion)
            interpolateView(_questionCard)
        }
    }

    private fun interpolateView(view: View) {
        val fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1F, 0F)
        fadeOut.duration = 500
        fadeOut.start()
        fadeOut.doOnEnd {
            setQuestion()
            val fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0F, 1F)
            fadeIn.duration = 500
            fadeIn.start()
        }
    }

    private fun actionGo(
        arrayOfRadioBtns: ArrayList<QuizRadioBtn>,
        actualQuestion: Question
    ) {
        if (mainViewModel.cont < mainViewModel.selectedQuiz.questionList.size - 1) {
            var selectedIndex = 0
            arrayOfRadioBtns.forEachIndexed { index, quizRadioBtn ->
                if (quizRadioBtn.isChecked) selectedIndex = index
            }
            val selectedOption = actualQuestion.options[selectedIndex]
            selectedOption.pointToList.forEach {
                mainViewModel.answersPoints[it] += 1
            }
            mainViewModel.cont++
        } else {
            //the end
            _questionCard.visibility = View.GONE
            var greatest = 0
            var greatestIndex = 0
            mainViewModel.answersPoints.forEachIndexed { index, cant ->
                if (cant > greatest) {
                    greatest = cant
                    greatestIndex = index
                }
            }
            val possibles = mainViewModel.selectedQuiz.answerList[greatestIndex].possibleItem
            val random = Random.nextInt(0, possibles.size)

            val theOne = possibles[random]
            endDialog(mainViewModel.selectedQuiz.answerTitle, theOne)
        }
    }

    private fun endDialog(answerTitle: String, theOne: ExplainItem) {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_end_quiz)

        val metrics = resources.displayMetrics
        dialog._title.layoutParams.width = metrics.widthPixels * 4/5
        dialog._title.text = answerTitle

        dialog._answerTitle.text = theOne.title
        dialog._answerExplication.text = theOne.explication

        dialog._repeat.setOnClickListener {
            mainViewModel.cont = 0
            mainViewModel.answersPoints.clear()
            mainViewModel.selectedQuiz.answerList.forEach { _ ->
                mainViewModel.answersPoints.add(0)
            }
            dialog.dismiss()
            setQuestion()
            _questionCard.visibility = View.VISIBLE
        }

        dialog._back.setOnClickListener {
            navController.navigateUp()
            dialog.dismiss()
        }

        dialog.show()
    }
}