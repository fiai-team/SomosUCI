package cu.fiai.uci.somosuci.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.main_menu_first_btn.*
import kotlin.random.Random

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var fadeOut: ObjectAnimator
    private lateinit var fadeIn: ObjectAnimator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel.actualFragment.value = 1
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView()
    }

    private fun configView() {
        navController = findNavController()

        _seeMoreBtn.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_universityFragment)
        }

        _games.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_gameSelectFragment)
        }

        _contacts.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_contactFragment)
        }

        _courses.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_coursesListFragment)
        }
        putImg()
    }

    private fun putImg() {
        val imgGroup = arrayListOf(
            R.mipmap.air_view, R.mipmap.pregrade, R.mipmap.building1, R.mipmap.campus
        )
        val random = Random.nextInt(0, imgGroup.size)
        _img1?.setImageResource(imgGroup[random])
    }
}