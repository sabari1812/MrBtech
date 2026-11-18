package com.example.mrbtech.di

import com.example.mrbtech.data.repository.FirebaseRepository
import com.example.mrbtech.data.repository.ImportantNotesRepositoryimp
import com.example.mrbtech.data.repository.ImportantSubjectRepositoryImp
import com.example.mrbtech.data.repository.QuestionPaperRepositoryImp
import com.example.mrbtech.domain.repository.ImportantSubjectRepository
import com.example.mrbtech.domain.repository.NotesRepository
import com.example.mrbtech.domain.repository.QuestionPaperRepository
import com.example.mrbtech.domain.repository.SubjectRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindQuestionPaperRepository(
        impl: QuestionPaperRepositoryImp
    ): QuestionPaperRepository

    @Binds
    abstract fun bindNotesRepository(
        impl: ImportantNotesRepositoryimp
    ): NotesRepository

    @Binds
    abstract fun bindImportantSubjectRepository(
        impl: ImportantSubjectRepositoryImp
    ): ImportantSubjectRepository

    @Binds
    abstract fun bindSubjectRepository(
        impl: FirebaseRepository
    ): SubjectRepository
}