package com.vholodynskyi.assignment.repository

import com.vholodynskyi.assignment.db.contacts.ContactsDao
import com.vholodynskyi.assignment.db.contacts.DbContact

class ContactDaoRepositoryImpl(private val daoService : ContactsDao): ContactDaoRepository {

    override suspend fun getContacts() : List<DbContact> {
       return daoService.getContacts()
    }

   override suspend fun addAll(contact: List<DbContact>) {
        daoService.addAll(contact)
    }

   override suspend fun getContactById(id: Int): DbContact {
        return daoService.getContactById(id)
    }

   override suspend fun deleteContact(id: Int) {
        daoService.deleteById(id)
    }

   override suspend fun deleteAllContact() {
        daoService.deleteAll()
    }

}