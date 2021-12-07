package com.kizadev.myapplication.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizadev.myapplication.R
import com.kizadev.myapplication.application.foraComponent
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.databinding.MainFragmentBinding
import com.kizadev.myapplication.presentation.adapters.ItemRecyclerAdapter
import com.kizadev.myapplication.presentation.adapters.ItemType
import com.kizadev.myapplication.presentation.listeners.OnItemClick
import com.kizadev.myapplication.presentation.ui.SearchEditText
import com.kizadev.myapplication.presentation.viewholders.ItemOffsetDecoration
import com.kizadev.myapplication.presentation.viewmodel.MainViewModelFactory
import com.kizadev.myapplication.presentation.viewmodel.MainViewModelImpl
import com.kizadev.myapplication.presentation.viewmodel.mapper.toSearchState
import com.kizadev.myapplication.presentation.viewmodel.state.MainScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.ScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.SearchState
import javax.inject.Inject

class MainFragment : Fragment(), IMainFragment, OnItemClick, SearchEditText.KeyImeChange {

    private lateinit var searchEditText: SearchEditText

    private val mainViewModel: MainViewModelImpl by viewModels {
        mainViewModelFactory.create()
    }

    private lateinit var viewBinding: MainFragmentBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory.Factory

    private lateinit var adapter: ItemRecyclerAdapter<AlbumItem>

    override fun onAttach(context: Context) {
        context.foraComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.e("MainFragment", "onCreateView $mainViewModel")

        viewBinding = MainFragmentBinding.inflate(inflater, container, false)

        mainViewModel.observeState(viewLifecycleOwner, ::renderData)

        mainViewModel.observeSubState(
            viewLifecycleOwner,
            MainScreenState::toSearchState,
            ::renderSearch
        )

        setupSearch()

        initViews()

        return viewBinding.root
    }

    override fun initViews() {

        adapter = ItemRecyclerAdapter(itemType = ItemType.AlbumItem())

        adapter.setOnItemListener(this)

        with(viewBinding) {
            rvAlbum.layoutManager = LinearLayoutManager(requireContext())
            rvAlbum.addItemDecoration(ItemOffsetDecoration())
            rvAlbum.adapter = adapter
        }
    }

    override fun renderData(screenState: MainScreenState) {
        when (screenState.screenState) {

            ScreenState.LOADING -> {
                with(viewBinding) {
                    tvEmptyListInfo.visibility = View.GONE
                    rvAlbum.visibility = View.GONE

                    progressBar.visibility = View.VISIBLE
                }
            }

            ScreenState.SHOW_LIST -> {
                with(viewBinding) {
                    tvEmptyListInfo.visibility = View.GONE
                    progressBar.visibility = View.GONE

                    rvAlbum.visibility = View.VISIBLE
                    adapter.setData(screenState.albumList!!)
                }
            }

            ScreenState.ERROR -> {
                with(viewBinding) {
                    rvAlbum.visibility = View.GONE
                    progressBar.visibility = View.GONE

                    tvEmptyListInfo.visibility = View.VISIBLE
                    tvEmptyListInfo.text = getString(R.string.search_bad_request)
                }
            }

            ScreenState.EMPTY_LIST -> {
                with(viewBinding) {
                    rvAlbum.visibility = View.GONE
                    progressBar.visibility = View.GONE

                    tvEmptyListInfo.visibility = View.VISIBLE
                    tvEmptyListInfo.text = getString(R.string.text_empty_list_hint)
                }
            }

            ScreenState.FAILED -> {
                with(viewBinding) {
                    rvAlbum.visibility = View.GONE
                    progressBar.visibility = View.GONE

                    tvEmptyListInfo.visibility = View.VISIBLE
                    tvEmptyListInfo.text = getString(R.string.search_failed_hint)
                }
            }
        }
    }

    override fun setupSearch() {
        searchEditText = viewBinding.customSearchView.searchBinding.searchEditText
        searchEditText.setOnKeyImeChangeListener(this)

        searchEditText.addTextChangedListener {
            it.toString().trim()
            mainViewModel.handleSearchQuery(it.toString())
        }
    }

    override fun renderSearch(searchState: SearchState) {
        if (!searchState.isOpened) {
            searchEditText.clearFocus()
        } else {
            searchEditText.requestFocus()
        }
    }

    override fun onItemClick(position: Int) {
        val albumItem = mainViewModel.currentState.albumList?.get(position)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AlbumDetailsFragment.newInstance(albumItem!!))
            .setTransition(TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN)
            .addToBackStack(null)
            .commit()
    }

    override fun onKeyIme(keyCode: Int, event: KeyEvent?) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event!!.action == KeyEvent.ACTION_DOWN)

            mainViewModel.handleSearchState(isOpened = false)
        else if (keyCode == EditorInfo.IME_ACTION_GO) {
            mainViewModel.handleSearchState(isOpened = false)
        }
    }
}
