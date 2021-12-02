package com.kizadev.myapplication.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.kizadev.myapplication.R
import com.kizadev.myapplication.application.foraComponent
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.data.local.model.TrackItem
import com.kizadev.myapplication.databinding.AlbumTrackFragmentBinding
import com.kizadev.myapplication.extensions.changeUrlPhotoSize
import com.kizadev.myapplication.presentation.activity.MainActivity
import com.kizadev.myapplication.presentation.adapters.ItemRecyclerAdapter
import com.kizadev.myapplication.presentation.adapters.ItemType
import com.kizadev.myapplication.presentation.listeners.OnItemClick
import com.kizadev.myapplication.presentation.viewholders.ItemOffsetDecoration
import com.kizadev.myapplication.presentation.viewmodel.DetailsViewModelFactory
import com.kizadev.myapplication.presentation.viewmodel.DetailsViewModelImpl
import com.kizadev.myapplication.presentation.viewmodel.state.DetailScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.ScreenState
import javax.inject.Inject

class AlbumDetailsFragment : Fragment(R.layout.album_track_fragment), IAlbumDetailsFragment, View.OnClickListener {


    private lateinit var viewBinding: AlbumTrackFragmentBinding

    private lateinit var albumItem: AlbumItem

    private val detailViewModel: DetailsViewModelImpl by viewModels{
        detailViewModelFactory.create(albumItem)
    }

    private lateinit var adapter: ItemRecyclerAdapter<TrackItem>


    override fun onAttach(context: Context) {
        context.foraComponent.inject(this)
        super.onAttach(context)
    }
    @Inject
    lateinit var detailViewModelFactory: DetailsViewModelFactory.Factory

    companion object{

        private const val ALBUM_ITEM_KEY = "ALBUM_ITEM"

        fun newInstance(albumItem: AlbumItem): AlbumDetailsFragment {

            val args = Bundle()
            args.putParcelable(ALBUM_ITEM_KEY, albumItem)

            val fragment = AlbumDetailsFragment()
            fragment.arguments = args

            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!arguments!!.isEmpty && arguments != null){
            albumItem = arguments!!.getParcelable(ALBUM_ITEM_KEY)!!
        }

        detailViewModel.getAlbumDetails()

        detailViewModel.observeState(this, ::renderData)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = AlbumTrackFragmentBinding.inflate(inflater, container, false)


        initViews()

        return viewBinding.root
    }

    override fun initViews() {

        adapter = ItemRecyclerAdapter(itemType = ItemType.TrackIem())

        viewBinding.toolbar.setOnClickListener(this)

        with(viewBinding) {
            rvArtistSongs.layoutManager = LinearLayoutManager(requireContext())
            rvArtistSongs.addItemDecoration(ItemOffsetDecoration())
            rvArtistSongs.adapter = adapter
        }

    }

    override fun renderData(state: DetailScreenState) {

        with(viewBinding){
            Glide.with(requireContext())
                .load(state.albumItem!!.albumPhotoUrl.changeUrlPhotoSize())
                .error(ContextCompat.getDrawable(requireContext(), R.drawable.ic_name_album_icon))
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(ivAlbumImage)

            tvAlbumGenre.text = state.albumItem.albumGenre
            tvAlbumCountOfTracks.text = state.albumItem.albumTrackCount
            tvAlbumPrice.text = state.albumItem.albumPrice

            toolbar.title = state.albumItem.albumName

        }

        when(state.screenState){

            ScreenState.SHOW_LIST -> {
                Log.e("DetailsScreen", "renderData: ${state.trackList}", )
                with(viewBinding) {
                    progressBar.visibility = View.GONE
                    rvArtistSongs.visibility = View.VISIBLE
                    adapter.setData(newList = state.trackList!!)
                }

            }

            ScreenState.LOADING -> {
                with(viewBinding){
                    rvArtistSongs.visibility = View.GONE
                    tvEmptyListInfo.visibility = View.GONE

                    progressBar.visibility = View.VISIBLE
                }
            }

            ScreenState.FAILED ->{
                with(viewBinding){
                    progressBar.visibility = View.GONE

                    tvEmptyListInfo.visibility = View.VISIBLE
                }
            }


            else -> return
        }

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            viewBinding.toolbar.id -> {
                requireActivity().onBackPressed()
            }
        }
    }


}