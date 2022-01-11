package com.kizadev.myapplication.domain.usecase

import com.kizadev.myapplication.domain.mapper.mapToAlbumDetailsModel
import com.kizadev.myapplication.domain.model.AlbumDetailsModel
import com.kizadev.myapplication.domain.repository.ForaRepository
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import io.reactivex.Flowable
import javax.inject.Inject

class GetAlbumDetailsFromNetworkUseCase @Inject constructor(
    private val repository: ForaRepository,
) {

    fun execute(
        collectionId: String,
    ): Flowable<ResponseResult<AlbumDetailsModel>> {
        return repository.getAlbumDetails(collectionId)
            .map { albumDetailsDto ->
                val albumDetails = albumDetailsDto.mapToAlbumDetailsModel()

                when {
                    albumDetails.albumTracksList.isNullOrEmpty() -> {
                        ResponseResult.Failed("Failed")
                    }

                    else -> ResponseResult.Success(albumDetails)
                }
            }
    }
}
