package com.kizadev.myapplication.presentation.viewmodel.mapper

import com.kizadev.myapplication.presentation.viewmodel.state.MainScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.SearchState


fun MainScreenState.toSearchState(): SearchState{
    return SearchState(
        searchQuery = searchQuery,
        isOpened = isSearchOpened
    )
}