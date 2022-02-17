package com.cg.marvel_app.ui.charactercomics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cg.marvel_app.api.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(private val marvelRepository: MarvelRepository) :
    ViewModel() {

    fun getCharacterComics(characterId: String) =
        marvelRepository.getCharacterComics(characterId).asLiveData().cachedIn(viewModelScope)

}