package com.vholodynskyi.assignment.repository

import com.vholodynskyi.assignment.db.contacts.DbContact

interface ContactDaoRepository {

    suspend fun getContacts() : List<DbContact>

    suspend fun addAll(contact: List<DbContact>)

    suspend fun getContactById(id: Int): DbContact

    suspend fun deleteContact(id: Int)

    suspend fun deleteAllContact()
}