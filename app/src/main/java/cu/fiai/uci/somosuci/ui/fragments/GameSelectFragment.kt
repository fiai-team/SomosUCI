package cu.fiai.uci.somosuci.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.adapters.QuizAdapter
import cu.fiai.uci.somosuci.models.Quiz
import cu.fiai.uci.somosuci.utils.JsonManager
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_game_select.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread

class GameSelectFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var adapter: QuizAdapter
    private lateinit var jsonManager: JsonManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        jsonManager = JsonManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel.actualFragment.value = 0
        return inflater.inflate(R.layout.fragment_game_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView()
    }

    private fun configView() {
        navController = findNavController()

        GlobalScope.launch(Dispatchers.IO) {
            val arrayOfQuiz = ArrayList<Quiz>()
            JsonManager.QUIZZES.forEach {
                arrayOfQuiz.add(jsonManager.getQuiz(it))
            }

            requireContext().runOnUiThread {
                adapter = QuizAdapter(requireContext(), mainViewModel, arrayOfQuiz)
                _progressbar.visibility = View.GONE
                _quizRecycler.adapter = adapter
                _quizRecycler.visibility = View.VISIBLE
            }
        }
    }
}