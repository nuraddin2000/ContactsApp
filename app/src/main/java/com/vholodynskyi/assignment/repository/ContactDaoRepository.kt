package com.vholodynskyi.assignment.repository

import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.di.GlobalFactory.daoService

class ContactDaoRepository {

    suspend fun getContacts() : List<DbContact> {
       return daoService.getContacts()
    }

    suspend fun addAll(contact: List<DbContact>) {
        daoService.addAll(contact)
    }

}