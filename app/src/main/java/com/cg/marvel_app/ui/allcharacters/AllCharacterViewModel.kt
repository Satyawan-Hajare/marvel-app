package com.cg.marvel_app.ui.allcharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cg.marvel_app.api.MarvelRepository
import com.cg.marvel_app.data.CharacterResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCharacterViewModel @Inject constructor(private val marvelRepository: MarvelRepository) :
    ViewModel() {

    val searchQuery = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    val searchResult = searchQuery.flatMapLatest { query ->
        marvelRepository.searchCharacter(query).cachedIn(viewModelScope)
    }.asLiveData()

    fun addToFavourite(characterResult: CharacterResult) =
        viewModelScope.launch {
            marvelRepository.addCharacterToFavourite(characterResult)
        }

    fun removeFromFavourite(characterResult: CharacterResult) =
        viewModelScope.launch {
            marvelRepository.removeCharacterFromFavourite(characterResult)
        }

    fun getFavouriteCharacters() =
        marvelRepository.getFavouriteCharacters().asLiveData()

}