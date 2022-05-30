package com.nojiko.tanoshi.animequiz_v2

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener
import com.nojiko.tanoshi.animequiz_v2.databinding.FragmentDoneBinding
import com.nojiko.tanoshi.domain.GameData

class DoneDialogFragment : DialogFragment() {

    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)

        setFragmentResultListener("requestKey") { key, bundle ->
            val gameData = bundle.getSerializable("data") as GameData
            Toast.makeText(requireContext(), "$key: $gameData", Toast.LENGTH_SHORT).show()
            binding.nbCharacter.text =
                getString(R.string.index, gameData.nbRightAnswer, gameData.nbQuestion)
            binding.score.text = getString(R.string.index, gameData.score, gameData.maxScore)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "DoneDialogFragment"
    }

}