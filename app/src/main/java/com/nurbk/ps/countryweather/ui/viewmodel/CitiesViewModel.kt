package com.nurbk.ps.countryweather.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.countryweather.repositories.DetailsCountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    val detailsCountriesRepository: DetailsCountriesRepository,
    application: Application,
) : AndroidViewModel(application) {

    fun getCitiesLiveData() = detailsCountriesRepository.getCitiesLiveData()
    fun getCitiesSearchLiveData() = detailsCountriesRepository.getCitiesSearchLiveData()

    fun getWeatherLiveData() =
        detailsCountriesRepository.getWeatherLiveData()

    fun getPhotosLiveData() = detailsCountriesRepository.getPhotosLiveData()
    fun getCountryNameLiveData() = detailsCountriesRepository.getCountryNameLiveData()

    fun searchCities(name: String) {
        viewModelScope.launch {
            detailsCountriesRepository.searchCities(name)
        }
    }

    fun getDayWeather(query: String) {
        detailsCountriesRepository.getWeather(query)
    }
    fun getWeatherCity(query: String) {
        detailsCountriesRepository.getWeatherCity(query)
    }
}