package com.cg.marvel_app.data.characters

import android.os.Parcelable
import androidx.room.*
import com.cg.marvel_app.utils.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.DbConstant.TABLE_NAME)
data class CharacterResult(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var description: String = "",
    @Embedded
    var thumbnail: Thumbnail = Thumbnail("", ""),
    @Embedded
    var comics: Comics = Comics(""),
    @Embedded
    var series: Series = Series(""),
    @Ignore
    var urls: List<Url> = ArrayList(),
) : Parcelable {

    @Parcelize
    data class Thumbnail(val path: String, val extension: String) : Parcelable

    @Parcelize
    data class Url(val type: String, val url: String) : Parcelable

    @Parcelize
    data class Comics(@ColumnInfo(name = "comic_available") val available: String) : Parcelable

    @Parcelize
    data class Series(@ColumnInfo(name = "series_available") val available: String) : Parcelable

}