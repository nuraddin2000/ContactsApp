package com.vholodynskyi.assignment.repository

import com.vholodynskyi.assignment.api.contacts.ApiContactResponse
import com.vholodynskyi.assignment.di.GlobalFactory.service

class ContactRepository {

    suspend fun getContacts(): ApiContactResponse {
        return service.getContacts()
    }

}