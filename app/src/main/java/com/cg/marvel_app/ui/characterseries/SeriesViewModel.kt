package com.cg.marvel_app.ui.characterseries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cg.marvel_app.api.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val marvelRepository: MarvelRepository) :
    ViewModel() {

    fun getCharacterSeries(characterId: String) =
        marvelRepository.getCharacterSeries(characterId).asLiveData().cachedIn(viewModelScope)

}