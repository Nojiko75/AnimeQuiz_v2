package com.nojiko.tanoshi.animequiz_v2

import android.content.res.ColorStateList
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nojiko.tanoshi.animequiz_v2.databinding.FragmentCharacterBinding
import com.nojiko.tanoshi.animequiz_v2.extension.disable
import com.nojiko.tanoshi.animequiz_v2.extension.enable
import com.nojiko.tanoshi.animequiz_v2.extension.isProposal
import com.nojiko.tanoshi.animequiz_v2.extension.wrong
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
    private var clueUsed = false
    private var counterIsActive = false
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var rightAnswer: String
    private lateinit var questionList: List<EasyCharacterQuestion>
    private lateinit var proposals: List<ShapeableImageView>

    var storageRef = storage.reference
    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timer.max = 15
        binding.timer.progress = 15000

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

                binding.nextButton.setOnClickListener { pass() }
                binding.clueButton.setOnClickListener { clue() }
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showGame() {
        if (index < nbCharacter) {
            startTimer()
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
            updateButton(binding.nextButton)
            updateButton(binding.clueButton)
            if (clueUsed) {
                proposals.forEach { proposal ->
                    proposal.isProposal()
                }
                clueUsed = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
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
                image.strokeColor = ColorStateList.valueOf(getColor(requireContext(), answerColor))
                resetTimer()
                //endTimer()
                Handler(Looper.getMainLooper()).postDelayed({
                    if (index + 1 < nbCharacter) {
                        ++index
                        showGame()
                        binding.index.text = getString(R.string.index, index + 1, nbCharacter)
                        image.strokeColor =
                            ColorStateList.valueOf(getColor(requireContext(), R.color.border_image))
                        for (proposal in proposals) {
                            proposal.isProposal()
                        }
                    } else {
                        //the game is over, go to DoneFragment
                        gameOver()
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun pass() {
        nbOnigiri -= GAINED_ONIGIRI
        binding.nbOnigiri.text = nbOnigiri.toString()
        nextQuestion()
        if (nbOnigiri < GAINED_ONIGIRI) {
            binding.clueButton.disable()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun nextQuestion() {
        if (index + 1 < nbCharacter) {
            ++index
            showGame()
            binding.index.text = getString(R.string.index, index + 1, nbCharacter)
            checkAnswer()
        } else {
            //the game is over, go to DoneFragment
            gameOver()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun clue() {
        clueUsed = true
        nbOnigiri -= GAINED_ONIGIRI
        binding.nbOnigiri.text = nbOnigiri.toString()
        binding.clueButton.disable()

        val clue = proposals.filter { it.tag != rightAnswer }
            .shuffled()
            .toMutableList()

        clue.removeAt(0)

        clue.forEach { proposal ->
            proposal.wrong()
        }
        if (nbOnigiri < GAINED_ONIGIRI) {
            binding.nextButton.disable()
        }
    }

    private fun gameOver() {
        val gameData = GameData(score, 27, nbFounded, nbCharacter)
        val action =
            CharacterFragmentDirections.actionCharacterFragmentToDoneDialogFragment(
                gameData
            )
        findNavController().navigate(action)
    }

    private fun updateButton(button: Button) {
        if (nbOnigiri >= GAINED_ONIGIRI) {
            button.enable()
        } else {
            button.disable()
        }
    }

    private fun startTimer() {
        if (!counterIsActive) {
            counterIsActive = true
            binding.timer.isEnabled = false

            countDownTimer = object : CountDownTimer(binding.timer.progress * 1000L, 1000) {
                override fun onFinish() {

                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun onTick(currentValue: Long) {
                    val remainingSeconds = (currentValue / 1000).toInt()
                    if (remainingSeconds == 0) {
                        nextQuestion()
                    } else {
                        updateTimer(remainingSeconds)
                    }
                }
            }.start()
        } else {
            resetTimer()
            startTimer()
        }
    }

    private fun updateTimer(currentValue: Int) {
        binding.timer.progress = currentValue
    }

    private fun resetTimer() {
        binding.timer.progress = 15000
        countDownTimer.cancel()
        binding.timer.isEnabled = true
        counterIsActive = false
    }

    private fun endTimer() {
        binding.timer.isEnabled = false
    }

    override fun onPause() {
        super.onPause()
        if (counterIsActive) {
            countDownTimer.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (counterIsActive) {
            countDownTimer.cancel()
        }
    }

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