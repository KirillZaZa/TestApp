package com.kizadev.myapplication.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kizadev.myapplication.R
import com.kizadev.myapplication.application.foraComponent
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.databinding.ActivityMainBinding
import com.kizadev.myapplication.presentation.adapters.ItemRecyclerAdapter
import com.kizadev.myapplication.presentation.adapters.ItemType
import com.kizadev.myapplication.presentation.viewholders.ItemOffsetDecoration
import com.kizadev.myapplication.presentation.viewmodel.MainViewModelFactory
import com.kizadev.myapplication.presentation.viewmodel.MainViewModelImpl
import com.kizadev.myapplication.presentation.viewmodel.state.MainScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.ScreenState
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IMainActivity {

    private lateinit var searchView: SearchView

    private val mainViewModel: MainViewModelImpl by viewModels {
        mainViewModelFactory.create()
    }

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory.Factory

    private lateinit var adapter: ItemRecyclerAdapter<AlbumItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        foraComponent.inject(this)

        initViews()

        mainViewModel.observeState(this, ::renderData)

    }

    override fun initViews() {
        supportActionBar?.let {
            it.title = "Поиск"
        }

        adapter = ItemRecyclerAdapter(itemType = ItemType.AlbumItem())

        viewBinding.rvAlbum.layoutManager = LinearLayoutManager(this)
        viewBinding.rvAlbum.addItemDecoration(ItemOffsetDecoration())
        viewBinding.rvAlbum.adapter = adapter

    }

    override fun renderData(screenState: MainScreenState) {
        when(screenState.screenState){

            ScreenState.LOADING-> {
                with(viewBinding){
                    tvEmptyListInfo.visibility = View.GONE
                    rvAlbum.visibility = View.GONE

                    progressBar.visibility = View.VISIBLE
                }
            }

            ScreenState.SHOW_LIST->{
                with(viewBinding){
                    tvEmptyListInfo.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    rvAlbum.visibility = View.VISIBLE

                    adapter.setData(screenState.albumList!!)
                }
            }

            ScreenState.ERROR -> {
                with(viewBinding){
                    rvAlbum.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    tvEmptyListInfo.visibility = View.VISIBLE
                    tvEmptyListInfo.text = getString(R.string.search_bad_request)
                }
            }

            ScreenState.EMPTY_LIST -> {
                with(viewBinding){
                    rvAlbum.visibility = View.GONE
                    progressBar.visibility = View.GONE

                    tvEmptyListInfo.visibility = View.VISIBLE
                    tvEmptyListInfo.text = getString(R.string.text_empty_list_hint)

                }
            }

            ScreenState.FAILED -> {
                with(viewBinding){
                    rvAlbum.visibility = View.GONE
                    progressBar.visibility = View.GONE

                    tvEmptyListInfo.visibility = View.VISIBLE
                    tvEmptyListInfo.text = getString(R.string.search_failed_hint)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.action_search)

        searchView = (menuItem.actionView as SearchView)
        searchView.queryHint = getString(R.string.search_hint)

        if (mainViewModel.currentState.isSearchOpened){

            menuItem.expandActionView()
            searchView.setQuery(mainViewModel.currentState.searchQuery, false)
            searchView.requestFocus()

        } else searchView.clearFocus()

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                mainViewModel.handleSearchState(true)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                mainViewModel.handleSearchState(false)
                return true
            }

        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.handleSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mainViewModel.handleSearchQuery(newText)
                viewBinding.rvAlbum.scrollToPosition(0)

                return true
            }

        })



        return super.onCreateOptionsMenu(menu)
    }


}