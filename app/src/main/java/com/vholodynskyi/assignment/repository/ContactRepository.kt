package com.vholodynskyi.assignment.repository

import com.vholodynskyi.assignment.api.contacts.ApiContactResponse

interface ContactRepository {

    suspend fun getContacts(): ApiContactResponse

}