package com.kizadev.myapplication.presentation.activity

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kizadev.myapplication.R
import com.kizadev.myapplication.application.foraComponent
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.databinding.ActivityMainBinding
import com.kizadev.myapplication.presentation.adapters.ItemRecyclerAdapter
import com.kizadev.myapplication.presentation.adapters.ItemType
import com.kizadev.myapplication.presentation.viewmodel.MainViewModelFactory
import com.kizadev.myapplication.presentation.viewmodel.MainViewModelImpl
import com.kizadev.myapplication.presentation.viewmodel.state.MainScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.ScreenState
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IMainActivity {

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


        adapter = ItemRecyclerAdapter(itemType = ItemType.AlbumItem())

        viewBinding.rvAlbum.layoutManager = LinearLayoutManager(this)
        viewBinding.rvAlbum.adapter = adapter


        initViews()

        mainViewModel.observeState(this){ mainState->
            renderData(mainState)
        }

    }

    override fun initViews() {
        supportActionBar?.let {
            it.title = "Поиск"
        }


    }

    override fun renderData(screenState: MainScreenState) {
        when(screenState.screenState){

            ScreenState.LOADING-> {
                with(viewBinding){

                }
            }

            ScreenState.SHOW_LIST->{

            }

            ScreenState.ERROR -> {

            }

            ScreenState.EMPTY_LIST -> {

            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {



        return true
    }


}