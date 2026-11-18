package com.example.mrbtech.di

import com.example.mrbtech.data.downloader.AndroidDownloader
import com.example.mrbtech.domain.repository.DownloadRepository
import com.example.mrbtech.data.repository.DownloadRepositoryImp
import com.example.mrbtech.domain.repository.Downloader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DownloaderModule {

    @Binds
    @Singleton
    abstract fun bindDownloader(
        downloader: AndroidDownloader
    ): Downloader

    @Binds
    @Singleton
    abstract fun bindDownloadImp(
        repositoryImp: DownloadRepositoryImp
    ): DownloadRepository
}