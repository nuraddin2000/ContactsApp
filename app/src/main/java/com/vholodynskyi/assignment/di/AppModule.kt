package com.vholodynskyi.assignment.di

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.vholodynskyi.assignment.api.contacts.ContactsService
import com.vholodynskyi.assignment.db.AppDatabase
import com.vholodynskyi.assignment.db.contacts.ContactsDao
import com.vholodynskyi.assignment.repository.ContactDaoRepository
import com.vholodynskyi.assignment.repository.ContactDaoRepositoryImpl
import com.vholodynskyi.assignment.repository.ContactRepository
import com.vholodynskyi.assignment.repository.ContactRepositoryImpl
import com.vholodynskyi.assignment.ui.contactslist.ContactsListViewModel
import com.vholodynskyi.assignment.ui.details.DetailsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val apiModule = module {

    fun provideMoshiConverter(): Moshi {
        return Moshi.Builder()
            .build()
    }

    fun provideRetrofitService(moshi: Moshi): ContactsService {
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(ContactsService::class.java)

    }

    single { provideMoshiConverter() }
    single { provideRetrofitService(get()) }
    single<ContactRepository> {
        ContactRepositoryImpl(get())
    }

    viewModel {
        ContactsListViewModel(get(), get())
    }
    viewModel {
        DetailsViewModel(get())
    }

}

val roomModule = module {
    fun provideDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "app-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: AppDatabase): ContactsDao {
        return dataBase.userDao()
    }

    fun provideDaoRepository(dao: ContactsDao): ContactDaoRepository {
        return ContactDaoRepositoryImpl(dao)
    }

    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
    single { provideDaoRepository(get()) }
}