package com.example.voicerecorder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        when (position) {
            0 -> return SettingsFragment()
            1 -> return RecordingFragment()

            else -> {
                return NotesFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Настойки"
            1 -> "Запись"
            else -> "Заметки"

        }
    }


}
