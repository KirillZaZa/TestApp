package com.kizadev.myapplication.domain.usecase

import com.kizadev.myapplication.domain.mapper.mapToAlbumListModel
import com.kizadev.myapplication.domain.model.AlbumListModel
import com.kizadev.myapplication.domain.repository.ForaRepository
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import io.reactivex.Flowable
import javax.inject.Inject
import kotlin.random.Random

class GetListFromNetworkUseCase @Inject constructor(
    private val repository: ForaRepository,
) {

    fun execute(
        searchQuery: String,
    ): Flowable<ResponseResult<AlbumListModel>> {
        return repository.getAlbumList(searchQuery)
            .map { albumDto ->
                when {
                    albumDto.results.isNullOrEmpty() -> ResponseResult.Failed("")
                    else -> {
                        ResponseResult.Success(
                            albumDto.mapToAlbumListModel(
                                Random.nextDouble(0.55, 1.56)
                            )
                        )
                    }
                }
            }
    }
}
