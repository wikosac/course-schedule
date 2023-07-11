package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.executeThread
import com.dicoding.courseschedule.util.today

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        // Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val nightMode = when (newValue) {
                getString(R.string.pref_dark_on) -> AppCompatDelegate.MODE_NIGHT_YES
                getString(R.string.pref_dark_off) -> AppCompatDelegate.MODE_NIGHT_NO
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            updateTheme(nightMode)
            true
        }

        // Schedule and cancel notification in DailyReminder based on SwitchPreference
        val notificationPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        notificationPreference?.setOnPreferenceChangeListener { preference, newValue ->
            val isEnabled = newValue as Boolean
            val context = preference.context
            val dailyReminder = DailyReminder()

            if (isEnabled) {
//                dailyReminder.setDailyReminder(context)
                executeThread {
                    val repository = DataRepository.getInstance(context)
                    val courses = repository?.getTodaySchedule(today())

                    courses?.let {
                        if (it.isNotEmpty()) dailyReminder.showNotification(context, it)
                    }
                }
            } else {
                dailyReminder.cancelAlarm(context)
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}