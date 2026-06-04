package com.dhruva.paginationsearchbackground.di

import com.dhruva.paginationsearchbackground.data.remote.repository.GitHubRepository
import com.dhruva.paginationsearchbackground.data.remote.repository.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGitHubRepository(impl: GithubRepositoryImpl): GitHubRepository
}