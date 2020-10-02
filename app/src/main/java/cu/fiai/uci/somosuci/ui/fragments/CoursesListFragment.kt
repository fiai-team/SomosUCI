package cu.fiai.uci.somosuci.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.utils.JsonManager
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_courses_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread

class CoursesListFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController
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
        return inflater.inflate(R.layout.fragment_courses_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView()
    }

    private fun configView() {
        navController = findNavController()

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark)

        requireActivity().toolbar.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.colorPrimary
            )
        )

        GlobalScope.launch(Dispatchers.IO) {
            val iciInfo = jsonManager.courseInfo(JsonManager.ICI_INFO)
            val ibiInfo = jsonManager.courseInfo(JsonManager.IBI_INFO)
            val csInfo = jsonManager.courseInfo(JsonManager.CS_INFO)
            val redInfo = jsonManager.courseInfo(JsonManager.RED_INFO)

            requireContext().runOnUiThread {
                _progressbar.visibility = View.GONE
                _ici.visibility = View.VISIBLE
                _ici.setOnClickListener {
                    mainViewModel.selectedCourse = iciInfo
                    navController.navigate(R.id.action_coursesListFragment_to_courseVPFragment)
                }

                _ibi.visibility = View.VISIBLE
                _ibi.setOnClickListener {
                    mainViewModel.selectedCourse = ibiInfo
                    navController.navigate(R.id.action_coursesListFragment_to_courseVPFragment)
                }

                _cs.visibility = View.VISIBLE
                _cs.setOnClickListener {
                    mainViewModel.selectedCourse = csInfo
                    navController.navigate(R.id.action_coursesListFragment_to_courseVPFragment)
                }

                _red.visibility = View.VISIBLE
                _red.setOnClickListener {
                    mainViewModel.selectedCourse = redInfo
                    navController.navigate(R.id.action_coursesListFragment_to_courseVPFragment)
                }
            }

        }
    }
}