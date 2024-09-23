package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.page.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita_abdurakhmonov.conversionuz.R
import uz.gita_abdurakhmonov.conversionuz.databinding.PageSettingBinding

class SettingPage:Fragment(R.layout.page_setting) {
    private val binding by viewBinding(PageSettingBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.text9.setOnClickListener {
            val url = "https://t.me/gitauz"
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(url))
            startActivity(i)
        }
        binding.text10.setOnClickListener {
            val url = "https://t.me/Abdurakhmonov_sardorbek"
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(url))
            startActivity(i)
        }
    }
}