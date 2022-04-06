package com.nojiko.tanoshi.animequiz_v2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nojiko.tanoshi.animequiz_v2.databinding.FragmentMenuCharacterBinding

class MenuCharacterFragment : Fragment() {
    private var _binding: FragmentMenuCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMenuCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

}