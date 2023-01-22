package com.vholodynskyi.assignment.ui.contactslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vholodynskyi.assignment.databinding.FragmentContactsListBinding
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.di.GlobalFactory
import com.vholodynskyi.assignment.ui.details.DetailsViewModel


open class ContactsListFragment : Fragment() {

    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            requireActivity(),
            this::onContactClicked
        )
    }

    private val viewModel: ContactsListViewModel by viewModels { GlobalFactory }

    private fun onContactClicked(dbContact: DbContact) {
        findNavController()
            .navigate(ContactsListFragmentDirections.actionContactListToDetails(dbContact.id))
    }

    private var binding: FragmentContactsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Creates a vertical Layout Manager
        return FragmentContactsListBinding.inflate(layoutInflater, container, false)
            .apply {
                contactList.layoutManager = LinearLayoutManager(context)
                contactList.adapter = contactAdapter
            }
            .also {
                binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeDeleteItem()
        viewModel.refreshData()

        viewModel.getContactResult().observe(viewLifecycleOwner) {
            contactAdapter.items = it as MutableList<DbContact>
        }

       binding?.pullToRefresh?.setOnRefreshListener {
           viewModel.clearDBAndGetContactsFromAPI()
           binding?.pullToRefresh?.isRefreshing = false
       }
    }

    private fun swipeDeleteItem() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Toast.makeText(requireContext(), "on Move", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                Toast.makeText(requireContext(), "on Swiped ", Toast.LENGTH_SHORT).show()
                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.layoutPosition
                viewModel.deleteContact(contactAdapter.items[position].id)
                contactAdapter.items.removeAt(position)
                contactAdapter.notifyItemRangeRemoved(position,contactAdapter.items.size)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding?.contactList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}