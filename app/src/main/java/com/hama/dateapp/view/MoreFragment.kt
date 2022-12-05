package com.hama.dateapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hama.dateapp.databinding.MoreFragmentBinding
import com.hama.dateapp.model.PlaceInfo
import com.hama.dateapp.viewmodel.PlaceItemViewModel

class MoreFragment : Fragment() {

    private var _binding: MoreFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel: PlaceItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MoreFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


    fun onItemClicked(item: PlaceInfo) {
        // ViewModel의 selectItem을 호출하여 MutableLiveData.setValue 호출
        viewModel.selectItem(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}