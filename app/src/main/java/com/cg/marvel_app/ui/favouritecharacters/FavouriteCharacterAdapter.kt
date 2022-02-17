package com.cg.marvel_app.ui.favouritecharacters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cg.marvel_app.R
import com.cg.marvel_app.data.characters.CharacterResult
import com.cg.marvel_app.databinding.ItemCharacterBinding
import com.cg.marvel_app.ui.allcharacters.CharacterClickListener
import com.cg.marvel_app.utils.Mapper.CHARACTER_MAPPER

class FavouriteCharacterAdapter(private val listener: CharacterClickListener):
    ListAdapter<CharacterResult, FavouriteCharacterAdapter.FavouriteCharacterViewHolder>(CHARACTER_MAPPER) {

    inner class FavouriteCharacterViewHolder(private val binding: ItemCharacterBinding)
        : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindCharacter(character: CharacterResult) {
            binding.apply {
                Glide.with(itemView)
                    .load(character.thumbnail.path + "." + character.thumbnail.extension)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            characterProgressbar.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            characterProgressbar.isVisible = false
                            return false
                        }
                    })
                    .centerCrop().into(characterImage)
                characterName.text = character.name
                val description = character.description
                if (description == "") {
                    characterDescription.text = "No Description"
                } else {
                    characterDescription.text = character.description
                }
                characterComics.text = "Comics: ${character.comics.available}"
                characterSeries.text = "Series: ${character.series.available}"
                likeButton.setImageResource(R.drawable.ic_liked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteCharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val favouriteCharacterViewHolder = FavouriteCharacterViewHolder(binding)
        binding.characterCardView.animation=   AnimationUtils.loadAnimation(binding.characterCardView.context, R.anim.translate)
        binding.characterCardView.setOnClickListener {
            listener.onClick(getItem(favouriteCharacterViewHolder.absoluteAdapterPosition))
        }
        binding.likeButton.setOnClickListener {
            listener.removeFromFavourite(getItem(favouriteCharacterViewHolder.absoluteAdapterPosition))
        }
        return favouriteCharacterViewHolder
    }

    override fun onBindViewHolder(holder: FavouriteCharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bindCharacter(character)
    }
}