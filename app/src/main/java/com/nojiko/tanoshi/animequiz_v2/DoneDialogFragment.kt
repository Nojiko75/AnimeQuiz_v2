package com.nojiko.tanoshi.animequiz_v2

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nojiko.tanoshi.animequiz_v2.databinding.FragmentDoneBinding

class DoneDialogFragment : DialogFragment() {

    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!
    private val args: DoneDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)

        val gameData = args.dataGame
        binding.nbCharacter.text =
            getString(R.string.index, gameData.nbRightAnswer, gameData.nbQuestion)
        binding.score.text = getString(R.string.index, gameData.score, gameData.maxScore)

        val action = DoneDialogFragmentDirections.actionDoneDialogFragmentToHomeFragment()
        binding.homeButton.setOnClickListener {
            findNavController().navigate(action)
        }
        binding.rankingButton.setOnClickListener {
            Toast.makeText(
                context,
                getString(R.string.coming_soon),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }
}