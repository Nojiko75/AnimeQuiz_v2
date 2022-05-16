package com.nojiko.tanoshi.animequiz_v2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.nojiko.tanoshi.animequiz_v2.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment() {
    private val storage = Firebase.storage
    var storageRef = storage.reference
    var answerA: StorageReference? = storageRef.child("easy_character_image/kakashi_hatake.jpg")
    var answerB: StorageReference? = storageRef.child("easy_character_image/namikaze_minato.jpg")
    var answerC: StorageReference? = storageRef.child("easy_character_image/toramizu_ginta.jpg")
    var answerD: StorageReference? = storageRef.child("easy_character_image/uzumaki_naruto.jpg")

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlideApp.with(this)
            .load(answerA)
            .into(binding.answerA)

        GlideApp.with(this /* context */)
            .load(answerB)
            .into(binding.answerB)

        GlideApp.with(this /* context */)
            .load(answerC)
            .into(binding.answerC)

        GlideApp.with(this /* context */)
            .load(answerD)
            .into(binding.answerD)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }
}