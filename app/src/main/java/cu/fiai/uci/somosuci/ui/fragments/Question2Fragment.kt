package cu.fiai.uci.somosuci.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_question2.*

class Question2Fragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_question2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView()
    }

    private fun configView() {
        _titleQ2.text = getString(R.string.q2) + " " + mainViewModel.rol
        _bodyQ2.text = mainViewModel.selectedCourse.question2

        _cardTitleQ2.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                mainViewModel.foregroundColor
            )
        )
        changeImg()
    }

    private fun changeImg() {
        var cont = 0

        val imgGroup = arrayListOf(
            R.drawable.ic_img_q2_1,
            R.drawable.ic_img_q2_2,
            R.drawable.ic_img_q2_3
        )

        val fadeOut = ObjectAnimator.ofFloat(_imgQuestion2, "alpha", 1F, 0F)
        fadeOut.duration = 2500
        fadeOut.start()
        fadeOut.doOnEnd {
            if (cont < imgGroup.size - 1) cont++ else cont = 0
            _imgQuestion2?.setImageResource(imgGroup[cont])

            val fadeIn = ObjectAnimator.ofFloat(_imgQuestion2, "alpha", 0F, 1F)
            fadeIn.duration = 1500
            fadeIn.start()
            fadeIn.doOnEnd {
                fadeOut.start()
            }
        }
    }
}
