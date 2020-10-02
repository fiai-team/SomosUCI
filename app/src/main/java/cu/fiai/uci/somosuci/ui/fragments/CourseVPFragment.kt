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
import androidx.viewpager.widget.ViewPager
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.adapters.SectionsPagerAdapter
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_course_vp.*

class CourseVPFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_course_vp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView()
    }

    private fun configView() {
        navController = findNavController()

        _viewPager.adapter = SectionsPagerAdapter(childFragmentManager)
        _vpIndicator.setupWithViewPager(_viewPager)
        _vpIndicator.addOnPageChangeListener(viewListener)

        mainViewModel.selectedCourse.apply {
            when (course) {
                1 -> {
                    mainViewModel.backgroundColor = R.color.colorPrimary
                    mainViewModel.foregroundColor = R.color.colorAccent
                    mainViewModel.rol = getString(R.string.c_ici)
                }
                2 -> {
                    mainViewModel.backgroundColor = R.color.ibiDark
                    mainViewModel.foregroundColor = R.color.ibiLight
                    mainViewModel.rol = getString(R.string.c_ibi)
                }
                3 -> {
                    mainViewModel.backgroundColor = R.color.csDark
                    mainViewModel.foregroundColor = R.color.csLight
                    mainViewModel.rol = getString(R.string.c_cs)
                }
                4 -> {
                    mainViewModel.backgroundColor = R.color.redDark
                    mainViewModel.foregroundColor = R.color.redLight
                    mainViewModel.rol = getString(R.string.c_red)
                }
            }
        }

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), mainViewModel.backgroundColor)

        requireActivity().toolbar.setBackgroundColor(ContextCompat.getColor(requireActivity(), mainViewModel.foregroundColor))
    }

    var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
        override fun onPageSelected(i: Int) {}
        override fun onPageScrollStateChanged(i: Int) {}
    }
}