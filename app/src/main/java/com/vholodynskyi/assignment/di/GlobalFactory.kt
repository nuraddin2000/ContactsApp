package com.vholodynskyi.assignment.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.vholodynskyi.assignment.api.RetrofitServicesProvider
import com.vholodynskyi.assignment.api.contacts.ContactsService
import com.vholodynskyi.assignment.db.AppDatabase
import com.vholodynskyi.assignment.db.contacts.ContactsDao
import com.vholodynskyi.assignment.repository.ContactDaoRepositoryImpl
import com.vholodynskyi.assignment.repository.ContactRepositoryImpl
import com.vholodynskyi.assignment.ui.contactslist.ContactsListViewModel
import com.vholodynskyi.assignment.ui.details.DetailsViewModel

object GlobalFactory: ViewModelProvider.Factory {

    val service: ContactsService by lazy {
        RetrofitServicesProvider().contactsService
    }

    val daoService: ContactsDao by lazy {
        db.userDao()
    }

    val daoRepository: ContactDaoRepositoryImpl by lazy {
        ContactDaoRepositoryImpl()
    }

    val repository: ContactRepositoryImpl by lazy {
        ContactRepositoryImpl()
    }

    lateinit var db: AppDatabase

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ContactsListViewModel::class.java -> ContactsListViewModel()
            DetailsViewModel::class.java -> DetailsViewModel()
            else -> throw IllegalArgumentException("Cannot create factory for ${modelClass.simpleName}")
        } as T
    }
}
