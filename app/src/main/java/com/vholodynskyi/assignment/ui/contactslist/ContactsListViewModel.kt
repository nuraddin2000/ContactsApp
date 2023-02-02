package com.vholodynskyi.assignment.ui.contactslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.App
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.repository.ContactDaoRepository
import com.vholodynskyi.assignment.repository.ContactRepository
import com.vholodynskyi.assignment.utils.CustomSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsListViewModel(
    private val daoRepository: ContactDaoRepository,
    private val repository: ContactRepository
) : ViewModel() {

    private var contactResult = MutableLiveData<List<DbContact>>()
    private var customPreferences = CustomSharedPreferences(App.application)
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    fun getContactResult(): MutableLiveData<List<DbContact>> {
        return contactResult
    }

    fun refreshData() {

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getContactsFromRoom()
        } else {
            getContactsFromAPI()
        }

    }

    private fun getContactsFromAPI() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val result = repository.getContacts()
            val dbContactList = mutableListOf<DbContact>()
            for (item in result.results!!) {
                val dbContact =
                    DbContact(
                        firstName = item.name?.firstName,
                        lastName = item.name?.lastName,
                        email = item.email,
                        photo = item.picture?.medium
                    )
                dbContactList.add(dbContact)
            }
            saveContactsDb(dbContactList)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getContactsFromRoom() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val result = daoRepository.getContacts()
            contactResult.postValue(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun saveContactsDb(contactList: List<DbContact>) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("TAG", "saveContactsDb: ")
                daoRepository.addAll(contactList)
                customPreferences.saveTime(System.nanoTime())
                refreshData()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    fun clearDBAndGetContactsFromAPI() =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoRepository.deleteAllContact()
                getContactsFromAPI()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    fun deleteContact(contactId: Int) = viewModelScope.launch {
        try {
            daoRepository.deleteContact(contactId)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
