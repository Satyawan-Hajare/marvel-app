package com.cg.marvel_app.ui.charactercomics

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cg.marvel_app.R
import com.cg.marvel_app.data.comic.ComicResult
import com.cg.marvel_app.databinding.FragmentComicBinding
import com.cg.marvel_app.ui.allcharacters.MarvelLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComicFragment(private val characterId: String) :
    Fragment(R.layout.fragment_comic), ComicClickListener {

    private var _binding: FragmentComicBinding? = null
    private val binding get() = _binding!!
    private val comicViewModel by viewModels<ComicViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentComicBinding.bind(view)

        val comicAdapter = ComicRecyclerViewAdapter(this)

        comicAdapter.addLoadStateListener { loadState ->
            binding.apply {
                comicProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                comicRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                comicRetryButton.isVisible = loadState.source.refresh is LoadState.Error
                comicNoConnection.isVisible = loadState.source.refresh is LoadState.Error
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && comicAdapter.itemCount < 1
                ) {
                    comicRecyclerView.isVisible = false
                    noResultFoundTextView.isVisible = true
                } else {
                    noResultFoundTextView.isVisible = false
                }
            }
        }

        binding.comicRecyclerView.apply {
            adapter = comicAdapter.withLoadStateHeaderAndFooter(
                header = MarvelLoadStateAdapter { comicAdapter.retry() },
                footer = MarvelLoadStateAdapter { comicAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        binding.apply {
            blackScreen.setOnClickListener {
                blackScreen.visibility = View.GONE
                comicDetailConstraint.visibility = View.GONE
            }
            comicRetryButton.setOnClickListener {
                comicAdapter.retry()
            }
        }

        comicViewModel.getCharacterComics(characterId).observe(viewLifecycleOwner) {
            comicAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    override fun showComicDetail(comic: ComicResult) {
        binding.apply {
            blackScreen.visibility = View.VISIBLE
            comicDetailConstraint.visibility = View.VISIBLE
            val animZoomOut = AnimationUtils.loadAnimation(
                comicImage.context,
                R.anim.zoom_in
            )
            comicImage.startAnimation(animZoomOut)
            Glide.with(requireContext())
                .load(comic.thumbnail.path + "." + comic.thumbnail.extension).into(comicImage)
            comicDescription.text = comic.description
            comicTitle.text = comic.title
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}