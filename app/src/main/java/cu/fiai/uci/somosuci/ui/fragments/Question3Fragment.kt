package cu.fiai.uci.somosuci.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_question3.*

class Question3Fragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView()
    }

    private fun configView() {
        _titleQ3.text = getString(R.string.q3) + " " + mainViewModel.rol
        _bodyQ3.text = mainViewModel.selectedCourse.question3

        _cardTitleQ3.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                mainViewModel.foregroundColor
            )
        )
        movImg1()
        movImg2()
    }

    private fun movImg1() {
        val movRight = TranslateAnimation(0F, 40F, 0F, 0F)
        val movLeft = TranslateAnimation(40F, 0F, 0F, 0F)
        movRight.duration = 1000
        movRight.fillAfter = true
        movLeft.duration = 1000
        movLeft.fillAfter = true

        movRight.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                _img1.startAnimation(movLeft)
            }

            override fun onAnimationStart(p0: Animation?) {}
        })

        movLeft.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                _img1.startAnimation(movRight)
            }

        })
        _img1.startAnimation(movLeft)
    }

    private fun movImg2() {
        val movDown = TranslateAnimation(0F, 0F, -40F, 0F)
        val movUp = TranslateAnimation(0F, 0F, 0F, -40F)
        movDown.duration = 1000
        movDown.fillAfter = true
        movUp.duration = 1000
        movUp.fillAfter = true

        movUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                _img2.startAnimation(movDown)
            }

            override fun onAnimationStart(p0: Animation?) {}
        })

        movDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                _img2.startAnimation(movUp)
            }

            override fun onAnimationStart(p0: Animation?) {}
        })
        _img2.startAnimation(movDown)
    }
}
