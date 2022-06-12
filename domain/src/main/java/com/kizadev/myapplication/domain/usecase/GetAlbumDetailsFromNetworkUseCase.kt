package com.kizadev.myapplication.domain.usecase

import com.kizadev.myapplication.domain.mapper.mapToAlbumDetailsModel
import com.kizadev.myapplication.domain.model.AlbumDetailsModel
import com.kizadev.myapplication.domain.repository.ForaRepository
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import io.reactivex.Flowable
import javax.inject.Inject
import kotlin.random.Random

class GetAlbumDetailsFromNetworkUseCase @Inject constructor(
    private val repository: ForaRepository,
) {

    fun execute(
        collectionId: String,
    ): Flowable<ResponseResult<AlbumDetailsModel>> {
        return repository.getAlbumDetails(collectionId)
            .map { albumDetailsDto ->
                val randomK = Random.nextDouble(0.55, 1.56)
                val albumDetails = albumDetailsDto.mapToAlbumDetailsModel(randomK)

                when {
                    albumDetails.albumTracksList.isNullOrEmpty() -> {
                        ResponseResult.Failed("Failed")
                    }

                    else -> ResponseResult.Success(albumDetails)
                }
            }
    }
}
