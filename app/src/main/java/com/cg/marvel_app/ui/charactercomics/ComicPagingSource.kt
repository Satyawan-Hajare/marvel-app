package com.cg.marvel_app.ui.charactercomics

import android.util.Log
import androidx.paging.PagingSource
import com.cg.marvel_app.api.MarvelApi
import com.cg.marvel_app.data.comic.ComicResult
import retrofit2.HttpException

private const val STARTING_OFFSET = 0
private const val LOAD_SIZE = 20

class ComicPagingSource(
    private val marvelApi: MarvelApi,
    private val characterId: String
) : PagingSource<Int, ComicResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComicResult> {
        val position = (params.key ?: STARTING_OFFSET)
        return try {

            val response = marvelApi.getCharacterComics(
                characterId = characterId,
                offset = position,
                limit = params.loadSize
            )
            val comicData = response.data
            val comics = comicData.results

            LoadResult.Page(
                data = comics,
                prevKey = if (position == STARTING_OFFSET) null else position - LOAD_SIZE,
                nextKey = if (comics.isEmpty()) null else position + LOAD_SIZE
            )
        } catch (exception: Exception) {
            Log.i("exception", exception.toString())
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}