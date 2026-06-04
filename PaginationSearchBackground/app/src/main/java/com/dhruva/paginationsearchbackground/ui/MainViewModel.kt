package com.dhruva.paginationsearchbackground.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dhruva.paginationsearchbackground.data.model.RepositoryItem
import com.dhruva.paginationsearchbackground.data.remote.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val repositoryFlow =
        queryFlow.debounce(300).distinctUntilChanged().flatMapLatest { query ->
            if (query.isNotEmpty()) {
                gitHubRepository.searchRepositoriesCombined(query).cachedIn(viewModelScope)
            } else {
                flowOf(PagingData.empty())
            }
        }

    fun searchRepositories(query: String) {
        queryFlow.value = query
    }

    fun getRepositories(): Flow<PagingData<RepositoryItem>> {
        return repositoryFlow
    }
}