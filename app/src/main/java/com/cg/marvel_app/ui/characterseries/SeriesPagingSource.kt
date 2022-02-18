package com.cg.marvel_app.ui.characterseries

import android.util.Log
import androidx.paging.PagingSource
import com.cg.marvel_app.api.MarvelApi
import com.cg.marvel_app.data.series.SeriesResult
import retrofit2.HttpException

private const val STARTING_OFFSET = 0
private const val LOAD_SIZE = 20

class SeriesPagingSource(private val marvelApi: MarvelApi, private val characterId: String) :
    PagingSource<Int, SeriesResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesResult> {
        val position = params.key ?: STARTING_OFFSET
        return try {
            val response = marvelApi.getCharacterSeries(
                characterId = characterId,
                offset = position,
                limit = params.loadSize
            )
            val seriesData = response.data
            val series = seriesData.results

            LoadResult.Page(
                data = series,
                prevKey = if (position == STARTING_OFFSET) null else position - LOAD_SIZE,
                nextKey = if (series.isEmpty()) null else position + LOAD_SIZE
            )
        } catch (exception: Exception) {
            Log.i("EXCEPTION", exception.toString())
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}