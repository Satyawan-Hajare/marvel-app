package com.cg.marvel_app.ui.favouritecharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cg.marvel_app.api.repository.MarvelRepository
import com.cg.marvel_app.data.characters.CharacterResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteCharacterViewModel @Inject constructor(private val marvelRepository: MarvelRepository) :
    ViewModel() {

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