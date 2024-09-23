package uz.gita_abdurakhmonov.conversionuz.presentation.screens.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita_abdurakhmonov.conversionuz.R

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