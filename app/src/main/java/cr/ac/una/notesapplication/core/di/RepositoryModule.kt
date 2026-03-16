package cr.ac.una.notesapplication.core.di

import cr.ac.una.notesapplication.data.repository.NoteRepositoryImpl
import cr.ac.una.notesapplication.domain.repository.INoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        impl: NoteRepositoryImpl
    ): INoteRepository
}