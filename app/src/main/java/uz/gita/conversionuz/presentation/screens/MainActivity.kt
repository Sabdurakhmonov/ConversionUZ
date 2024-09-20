package uz.gita.conversionuz.presentation.screens

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.conversionuz.R
import uz.gita.conversionuz.navigation.AppNavigationHandler
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var navigationHandler:AppNavigationHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navController = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
            .getFragment<NavHostFragment>().navController

        navigationHandler.navigationStack.onEach {
            it(navController)
        }.launchIn(lifecycleScope)

    }
}