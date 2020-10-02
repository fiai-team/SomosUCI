package cu.fiai.uci.somosuci.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import cu.fiai.uci.somosuci.R
import cu.fiai.uci.somosuci.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val drawerLayout = drawer_layout
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        //NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
    }

    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        mainViewModel.actualFragment.observe(this, Observer {
            supportActionBar?.setDisplayHomeAsUpEnabled(it != 1)
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var destination = 0

        if (item.itemId != android.R.id.home) {
            when (item.itemId) {
                R.id.actionAboutUs -> destination = R.id.aboutUsFragment
            }

            val options = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .setPopUpTo(R.id.mainFragment, false)
                .build()

            navController.navigate(
                destination,
                null,
                options
            )
        }

        return super.onOptionsItemSelected(item)
    }
}