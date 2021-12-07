package com.kizadev.myapplication.domain.impl

import com.kizadev.myapplication.data.local.model.AlbumListModel
import com.kizadev.myapplication.data.mapper.mapToAlbumListModel
import com.kizadev.myapplication.data.repository.ForaRepositoryImpl
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import com.kizadev.myapplication.domain.usecase.AlbumListUseCase
import io.reactivex.Flowable
import javax.inject.Inject

class AlbumListUseCaseImpl @Inject constructor(
    private val repository: ForaRepositoryImpl,
) : AlbumListUseCase {

    override fun getAlbums(
        searchQuery: String,
    ): Flowable<ResponseResult<AlbumListModel>> {
        return repository.getAlbumList(searchQuery)
            .map { albumDto ->
                when {
                    albumDto.results.isNullOrEmpty() -> ResponseResult.Failed("")
                    else -> ResponseResult.Success(albumDto.mapToAlbumListModel())
                }
            }
    }
}
