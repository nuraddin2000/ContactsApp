package com.vholodynskyi.assignment.di

import com.squareup.moshi.Moshi
import com.vholodynskyi.assignment.ui.contactslist.ContactsListViewModel
import com.vholodynskyi.assignment.ui.main.MainActivity
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val apiModule = module {

    scope<MainActivity> {
        val moshi = Moshi.Builder()
            .build()

        Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()




    }

    scope<MainActivity> {

    }

}

val viewModelModule = module {
ContactsListViewModel()
}

val roomModule = module {

}