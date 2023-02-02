package com.vholodynskyi.assignment.repository

import com.vholodynskyi.assignment.api.contacts.ApiContactResponse
import com.vholodynskyi.assignment.api.contacts.ContactsService

class ContactRepositoryImpl(private val service: ContactsService): ContactRepository {

    override suspend fun getContacts(): ApiContactResponse {
        return service.getContacts()
    }

}