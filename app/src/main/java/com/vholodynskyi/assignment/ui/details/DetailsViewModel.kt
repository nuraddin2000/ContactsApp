package com.vholodynskyi.assignment.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.repository.ContactDaoRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val daoRepository: ContactDaoRepository): ViewModel() {

    private var contactResult = MutableLiveData<DbContact>()

    private var deleteResult = MutableLiveData<Boolean>()

    fun getDeleteResult() : MutableLiveData<Boolean> {
        return deleteResult
    }

    fun getContactResult() : MutableLiveData<DbContact> {
        return contactResult
    }

    fun getContactById(contactId:Int) = viewModelScope.launch {
        try {
            val result = daoRepository.getContactById(contactId)
            contactResult.postValue(result)
        }catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun deleteContact(contactId:Int) = viewModelScope.launch {
        try {
            val result = daoRepository.deleteContact(contactId)
            deleteResult.postValue(true)
        }catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
