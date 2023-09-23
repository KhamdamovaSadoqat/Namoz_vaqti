package com.example.namozvaqti.ui.prayerTime

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.namozvaqti.R
import com.example.namozvaqti.data.comman.base.BindingFragment
import com.example.namozvaqti.databinding.DialogCityBinding
import com.example.namozvaqti.databinding.FragmentPrayerTimeBinding
import com.example.namozvaqti.domain.prayerTime.PrayerTimeEntity
import com.example.namozvaqti.utils.Constants.getCities
import com.example.namozvaqti.utils.Constants.getLatitude
import com.example.namozvaqti.utils.Constants.getLongitude
import com.example.namozvaqti.utils.SharedPref
import com.example.namozvaqti.utils.prayerTimeToMilliseconds
import com.example.namozvaqti.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PrayerTimeFragment : BindingFragment<FragmentPrayerTimeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentPrayerTimeBinding::inflate

    private var counrtyDialog: AlertDialog? = null
    private lateinit var countryDialogBinding: DialogCityBinding

    @Inject
    lateinit var sharedPref: SharedPref
    lateinit var cityAdapter: CityAdapter
    private val viewModel: PrayerTimeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
        observe()
        setUpUi()
    }

    private fun onBind(){
        viewModel.prayerTime()
    }

    private fun setUpUi(){
        countryDialogBinding = DialogCityBinding.inflate(LayoutInflater.from(requireContext()))
        binding.tvLocation.text = sharedPref.city
        binding.tvLocation.setOnClickListener {
            setUpDialog()
            (countryDialogBinding.root.parent as? ViewGroup)?.removeView(countryDialogBinding.root)
            showCityDialog()
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.mPrayerTimeState.collectLatest {
                handleStateChange(it)
            }
        }
    }

    private fun handleStateChange(prayerTimeState: PrayerTimeState) {
        Log.d("----------", "handleStateChange: start")
        when (prayerTimeState) {
            is PrayerTimeState.Init -> Unit
            is PrayerTimeState.ErrorPrayerTime -> {
                binding.linear.visibility = View.VISIBLE
                binding.pbPrayerTime.visibility = View.INVISIBLE
            }
            is PrayerTimeState.ShowToast -> {
                binding.linear.visibility = View.VISIBLE
                binding.pbPrayerTime.visibility = View.INVISIBLE
                binding.root.context.showToast(prayerTimeState.message)
            }
            is PrayerTimeState.IsLoading -> {
                Log.d("----------", "handleStateChange: Loading")
                binding.linear.visibility = View.INVISIBLE
                binding.pbPrayerTime.visibility = View.VISIBLE
            }
            is PrayerTimeState.SuccessPrayerTime -> {
                Log.d("----------", "handleStateChange: Success")
                binding.linear.visibility = View.VISIBLE
                binding.pbPrayerTime.visibility = View.INVISIBLE
                setPrayerTime(prayerTimeState.prayerTimeEntity)
                setUpMainTime(prayerTimeState.prayerTimeEntity)
            }
        }
    }

    private fun setPrayerTime(prayerTime: PrayerTimeEntity){
        binding.apply {
            timeFajr.text = prayerTime.fajr
            timeShuruq.text = prayerTime.sunrise
            timeThuhr.text = prayerTime.dhuhr
            timeAssr.text = prayerTime.asr
            timeMaghrib.text = prayerTime.maghrib
            timeIshaa.text = prayerTime.isha
        }
    }

    private fun setUpMainTime(prayerTime: PrayerTimeEntity){
        if(System.currentTimeMillis() - prayerTimeToMilliseconds(prayerTime.fajr)<0)
            setDrawables(R.drawable.ic_subah_prayer, R.string.fajr, prayerTime.fajr)
        else if(System.currentTimeMillis() - prayerTimeToMilliseconds(prayerTime.dhuhr)<0)
            setDrawables(R.drawable.ic_zuhar_prayer, R.string.dhuhr, prayerTime.dhuhr)
        else if(System.currentTimeMillis() - prayerTimeToMilliseconds(prayerTime.asr)<0)
            setDrawables(R.drawable.ic_ramadn_azhar, R.string.asr, prayerTime.asr)
        else if(System.currentTimeMillis() - prayerTimeToMilliseconds(prayerTime.asr)<0)
            setDrawables(R.drawable.ic_maghrib_prayer, R.string.maghrib, prayerTime.maghrib)
        else setDrawables(R.drawable.ic_isha_prayer, R.string.isha, prayerTime.isha)
    }

    private fun setDrawables(drawable: Int, timeName: Int, time: String){
        binding.prayerTimeIcon.setBackgroundResource(drawable)
        binding.prayerTime.text = "$time"
        binding.prayerTimeName.text = ContextCompat.getString(requireContext(), timeName)
    }

    private fun setUpDialog(){
        cityAdapter = CityAdapter{index ->
            sharedPref.city = getCities()[index]
            sharedPref.latitude = getLatitude()[index]
            sharedPref.longitude = getLongitude()[index]
            binding.tvLocation.text = sharedPref.city
            dismissCityDialog()
            viewModel.prayerTime()
        }
        countryDialogBinding.rvCountry.adapter =cityAdapter
        cityAdapter.items = getCities()
    }

    private fun showCityDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(countryDialogBinding.root)
        counrtyDialog = builder.create()
        counrtyDialog!!.show()
    }

    private fun dismissCityDialog() {
        if (counrtyDialog!!.isShowing) {
            counrtyDialog!!.dismiss()
        }
        counrtyDialog!!.dismiss()
    }

}