package com.nojiko.tanoshi.animequiz_v2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.nojiko.tanoshi.animequiz_v2.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment() {
    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private var score = 0
    private var nbOnigiri = 0

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

        db.collection("easy_character_question")
            .get()
            .addOnSuccessListener { result ->
                val test = result.documents[0].data?.get("right_answer")
                binding.characterName.text = test.toString()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private val TAG = CharacterFragment::class.java.simpleName
    }
}