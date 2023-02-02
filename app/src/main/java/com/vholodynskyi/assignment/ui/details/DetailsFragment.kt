package com.vholodynskyi.assignment.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.vholodynskyi.assignment.databinding.FragmentDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


open class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding? = null
    private val args: DetailsFragmentArgs by navArgs()
    private var id: Int? = null

    private val detailsViewModel by viewModel<DetailsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailsBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = args.id
        detailsViewModel.getContactById(id!!)

        detailsViewModel.getContactResult().observe(viewLifecycleOwner) {
            binding?.firstName?.text = it.firstName
            binding?.lastName?.text = it.lastName
            binding?.email?.text = it.email
            Picasso.get().load(it.photo).into(binding?.userImage)
        }
        detailsViewModel.getDeleteResult().observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding?.deleteButton?.setOnClickListener {
            detailsViewModel.deleteContact(id!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}