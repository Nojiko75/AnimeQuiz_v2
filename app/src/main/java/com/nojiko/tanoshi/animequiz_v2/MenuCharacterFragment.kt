package com.nojiko.tanoshi.animequiz_v2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.nojiko.tanoshi.animequiz_v2.databinding.FragmentMenuCharacterBinding
import com.nojiko.tanoshi.animequiz_v2.home.HomeFragmentDirections

class MenuCharacterFragment : Fragment() {
    private var _binding: FragmentMenuCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action = HomeFragmentDirections.actionHomeFragmentToCharacterFragment()
        binding.easyButton.setOnClickListener { navigateFromHome(action) }
        binding.mediumButton.setOnClickListener {
            Toast.makeText(context, getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
        }
        binding.hardButton.setOnClickListener {
            Toast.makeText(context, getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun navigateFromHome(directions: NavDirections) {
        NavHostFragment.findNavController(requireParentFragment().requireParentFragment())
            .navigate(directions)
    }
}