package uz.gita.conversionuz.presentation.screens.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.conversionuz.R
import uz.gita.conversionuz.databinding.ScreenSplashBinding
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen:Fragment(R.layout.screen_splash) {

    private val viewModel by viewModels<SplashViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorMessage.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        viewModel.nextToScreen()
    }
}