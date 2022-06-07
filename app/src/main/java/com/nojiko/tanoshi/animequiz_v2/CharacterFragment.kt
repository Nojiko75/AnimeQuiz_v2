package com.nojiko.tanoshi.animequiz_v2

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nojiko.tanoshi.animequiz_v2.databinding.FragmentCharacterBinding
import com.nojiko.tanoshi.animequiz_v2.firebase.convertToModel
import com.nojiko.tanoshi.domain.EasyCharacterQuestion
import com.nojiko.tanoshi.domain.GameData

class CharacterFragment : Fragment() {
    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private var index = 0
    private var score = 0
    private var nbOnigiri = 10
    private var nbCharacter = 0
    private var nbFounded = 0
    private lateinit var rightAnswer: String
    private lateinit var questionList: List<EasyCharacterQuestion>
    private lateinit var proposals: List<ShapeableImageView>

    var storageRef = storage.reference
    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        proposals =
            mutableListOf(binding.answerA, binding.answerB, binding.answerC, binding.answerD)

        db.collection(QUESTION_COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                questionList = convertToModel(result)
                nbCharacter = questionList.size
                showGame()
                binding.index.text = getString(R.string.index, index + 1, nbCharacter)
                binding.nbOnigiri.text = nbOnigiri.toString()
                binding.score.text = score.toString()
                checkAnswer()
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

    private fun showGame() {
        if (index < nbCharacter) {
            val question = questionList[index]
            rightAnswer = question.image
            binding.characterName.text = question.rightAnswer

            listOf(rightAnswer, question.answerA, question.answerB, question.answerC)
                .shuffled().forEachIndexed { index, image ->
                    GlideApp.with(this)
                        .load(getStorageReference(image))
                        .into(proposals[index])
                    proposals[index].tag = image
                }
        }
    }

    private fun checkAnswer() {
        var answerColor: Int
        for (image in proposals) {
            image.setOnClickListener {
                answerColor = if (rightAnswer == image.tag.toString()) {
                    RIGHT_COLOR
                } else {
                    WRONG_COLOR
                }
                updateData(answerColor)
                image.strokeColor = ColorStateList.valueOf(getColor(answerColor))
                Handler(Looper.getMainLooper()).postDelayed({
                    if (index + 1 < nbCharacter) {
                        ++index
                        showGame()
                        binding.index.text = getString(R.string.index, index + 1, nbCharacter)
                        image.strokeColor = ColorStateList.valueOf(getColor(R.color.border_image))
                    } else {
                        //the game is over, go to DoneFragment
                        val bundle = Bundle().apply {
                            putSerializable(
                                "data",
                                GameData(score, 27, nbFounded, nbCharacter)
                            )
                        }
                        childFragmentManager.setFragmentResult("requestKey", bundle)

                        DoneDialogFragment().show(childFragmentManager, DoneDialogFragment.TAG)
                    }
                }, 500)
            }
        }
    }

    private fun updateData(answerColor: Int) {
        when (answerColor) {
            RIGHT_COLOR -> {
                nbOnigiri += GAINED_ONIGIRI
                binding.nbOnigiri.text = nbOnigiri.toString()
                score += GAINED_SCORE
                binding.score.text = score.toString()
                nbFounded++
            }
            WRONG_COLOR -> {
                if (score >= GAINED_SCORE) score -= GAINED_SCORE else score = 0
                binding.score.text = score.toString()
            }
        }
    }

    private fun getStorageReference(image: String) =
        storageRef.child("${IMAGE_COLLECTION}/${image}.jpg")

    private fun getColor(color: Int) = ContextCompat.getColor(requireContext(), color)

    companion object {
        private val TAG = CharacterFragment::class.java.simpleName
        private const val IMAGE_COLLECTION = "easy_character_image"
        private const val QUESTION_COLLECTION = "easy_character_question"
        private const val RIGHT_COLOR = R.color.button_green
        private const val WRONG_COLOR = R.color.button_close
        private const val GAINED_SCORE = 3
        private const val GAINED_ONIGIRI = 5
    }
}