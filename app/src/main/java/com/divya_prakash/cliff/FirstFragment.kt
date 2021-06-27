package com.divya_prakash.cliff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.divya_prakash.cliff.databinding.FragmentFirstBinding
import com.divya_prakash.cliff.view.adapter.ImageAdapter
import com.divya_prakash.cliff.viewmodel.UrlItemsViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val adapter: ImageAdapter = ImageAdapter()

    private val imageItemsViewModel: UrlItemsViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        imageItemsViewModel.getItems().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}