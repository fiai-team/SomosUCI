package cu.fiai.uci.somosuci.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import cu.fiai.uci.somosuci.ui.fragments.Question2Fragment
import cu.fiai.uci.somosuci.ui.fragments.Question3Fragment
import cu.fiai.uci.somosuci.ui.fragments.Question1Fragment

class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Question1Fragment()
            1 -> Question2Fragment()
            else -> Question3Fragment()
        }
    }

    override fun getPageTitle(position: Int): String {
        return ""
    }

    override fun getCount(): Int {
        return 3
    }
}