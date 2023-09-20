package com.example.namozvaqti.ui.prayerTime

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.namozvaqti.data.comman.base.BindingFragment
import com.example.namozvaqti.databinding.FragmentPrayerTimeBinding
import com.example.namozvaqti.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PrayerTimeFragment : BindingFragment<FragmentPrayerTimeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentPrayerTimeBinding::inflate

    @Inject
    lateinit var sharedPref: SharedPref
//    private val viewModel: PrayerTimeViewModel by viewModels()

}