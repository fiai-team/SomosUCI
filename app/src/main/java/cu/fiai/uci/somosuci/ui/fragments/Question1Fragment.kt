package cu.fiai.uci.somosuci.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_question1.*

class Question1Fragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_question1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configView()
    }

    private fun configView() {

        _titleQ1.text = getString(R.string.q1) + " " + mainViewModel.rol
        _bodyQ1.text = mainViewModel.selectedCourse.question1

        _cardTitleQ1.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                mainViewModel.foregroundColor
            )
        )

        val animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_animation
        )
        //animation.repeatMode = Animation.INFINITE
        _imgQuestion1.startAnimation(animation)
    }
}


