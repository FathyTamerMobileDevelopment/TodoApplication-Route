package com.example.todoapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.base.BaseFragment
import com.example.todoapp.databinding.FragmentSettingBinding
import com.example.todoapp.utils.ARABIC_CODE
import com.example.todoapp.utils.DARK
import com.example.todoapp.utils.DARK_MODE_KEY
import com.example.todoapp.utils.ENGLISH_CODE
import com.example.todoapp.utils.LIGHT
import com.example.todoapp.utils.SETTING_SP_NAME
import com.example.todoapp.utils.applyModeChange
import com.example.todoapp.utils.getCurrentDeviceLanguageCode
import com.example.todoapp.utils.showBottomAppBarViews
import com.example.todoapp.utils.showToolbarSettingTitle

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    private lateinit var settingSP:SharedPreferences

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentSettingBinding.inflate(inflater, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbarSettingTitle()

        settingSP =  requireContext().getSharedPreferences(SETTING_SP_NAME,Context.MODE_PRIVATE)

        setInitialLanguageState()
        setInitialModeState()

        initLanguageTvAdapter()
        initModeTvAdapter()

        onLanguageTvClick()
        onModeTvClick()

    }

    override fun onStart() {
        super.onStart()
        initLanguageTvAdapter()
        initModeTvAdapter()
    }

    private fun onModeTvClick() {
        binding.autoCompleteTVModes.setOnItemClickListener { adapterView, view, position, id ->
            val selectedMode = adapterView.getItemAtPosition(position).toString()
            binding.autoCompleteTVModes.setText(selectedMode)
            val isDark = (selectedMode == getString(R.string.dark))
            applyModeChange(isDark)
            val editor = settingSP.edit()
            editor.apply{
                putBoolean(DARK_MODE_KEY,isDark)
                apply()
            }
        }
    }


    private fun onLanguageTvClick() {
        binding.autoCompleteTVLanguages.setOnItemClickListener { adapterView, view, position, id ->
            val selectedLanguage = adapterView.getItemAtPosition(position).toString()
            binding.autoCompleteTVLanguages.setText(selectedLanguage)
            val languageCode = if (selectedLanguage == getString(R.string.english)) ENGLISH_CODE
            else ARABIC_CODE
            applyLanguageChange(languageCode)
        }

    }

    private fun applyLanguageChange(languageCode: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
        activity?.let {
            it.finish()
            it.startActivity(it.intent)
        }
    }

    private fun initLanguageTvAdapter() {
        val languages = resources.getStringArray(R.array.languages).toList()
        val languagesAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, languages)
        binding.autoCompleteTVLanguages.setAdapter(languagesAdapter)
    }

    private fun initModeTvAdapter() {
        val modes = resources.getStringArray(R.array.modes).toList()
        val modesAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, modes)
        binding.autoCompleteTVModes.setAdapter(modesAdapter)

    }


    private fun setInitialModeState() {
        val currentMode = resources.configuration.uiMode and UI_MODE_NIGHT_MASK
        if (currentMode == UI_MODE_NIGHT_NO) {
            binding.autoCompleteTVModes.setText(getString(R.string.light))
            changeModeIcon(LIGHT)
        } else {
            binding.autoCompleteTVModes.setText(getString(R.string.dark))
            changeModeIcon(DARK)
        }
        binding.modeTil.refreshStartIconDrawableState()
    }

    private fun changeModeIcon(mode: String) {
        if (mode == LIGHT) {
            binding.modeTil.setStartIconDrawable(R.drawable.ic_light_mode)
        } else {
            binding.modeTil.setStartIconDrawable(R.drawable.ic_dark)
        }
    }

    private fun setInitialLanguageState() {
        val currentLocaleCode = AppCompatDelegate.getApplicationLocales()[0]?.language
                ?: getCurrentDeviceLanguageCode(requireContext())

        val language = if (currentLocaleCode == ENGLISH_CODE)
            getString(R.string.english) else getString(R.string.arabic)
        binding.autoCompleteTVLanguages.setText(language)
    }


}