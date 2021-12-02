package com.kizadev.myapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kizadev.myapplication.R
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.databinding.AlbumTrackFragmentBinding
import com.kizadev.myapplication.presentation.activity.MainActivity

class AlbumDetailsFragment : Fragment(R.layout.album_track_fragment) {


    private lateinit var viewBinding: AlbumTrackFragmentBinding

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
        requireActivity().actionBar?.hide()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = AlbumTrackFragmentBinding.inflate(inflater, container, false)


        return viewBinding.root
    }




}