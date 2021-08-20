package com.example.android.unscramble.ui.game

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    companion object {
        const val MAX_NO_OF_WORDS = 10
        const val SCORE_INCREASE = 20
        const val SPANISH = "spanish"
        const val ENGLISH = "english"

        // List with all the words for the Game
        val allEnglishWordsList: List<String> =
            listOf(
                "animal",
                "auto",
                "anecdote",
                "alphabet",
                "all",
                "awesome",
                "arise",
                "balloon",
                "basket",
                "bench",
                "best",
                "birthday",
                "book",
                "briefcase",
                "camera",
                "camping",
                "candle",
                "cat",
                "cauliflower",
                "chat",
                "children",
                "class",
                "classic",
                "classroom",
                "coffee",
                "colorful",
                "cookie",
                "creative",
                "cruise",
                "dance",
                "daytime",
                "dinosaur",
                "doorknob",
                "dine",
                "dream",
                "dusk",
                "eating",
                "elephant",
                "emerald",
                "eerie",
                "electric",
                "finish",
                "flowers",
                "follow",
                "fox",
                "frame",
                "free",
                "frequent",
                "funnel",
                "green",
                "guitar",
                "grocery",
                "glass",
                "great",
                "giggle",
                "haircut",
                "half",
                "homemade",
                "happen",
                "honey",
                "hurry",
                "hundred",
                "ice",
                "igloo",
                "invest",
                "invite",
                "icon",
                "introduce",
                "joke",
                "jovial",
                "journal",
                "jump",
                "join",
                "kangaroo",
                "keyboard",
                "kitchen",
                "koala",
                "kind",
                "kaleidoscope",
                "landscape",
                "late",
                "laugh",
                "learning",
                "lemon",
                "letter",
                "lily",
                "magazine",
                "marine",
                "marshmallow",
                "maze",
                "meditate",
                "melody",
                "minute",
                "monument",
                "moon",
                "motorcycle",
                "mountain",
                "music",
                "north",
                "nose",
                "night",
                "name",
                "never",
                "negotiate",
                "number",
                "opposite",
                "octopus",
                "oak",
                "order",
                "open",
                "polar",
                "pack",
                "painting",
                "person",
                "picnic",
                "pillow",
                "pizza",
                "podcast",
                "presentation",
                "puppy",
                "puzzle",
                "recipe",
                "release",
                "restaurant",
                "revolve",
                "rewind",
                "room",
                "run",
                "secret",
                "seed",
                "ship",
                "shirt",
                "should",
                "small",
                "spaceship",
                "stargazing",
                "skill",
                "street",
                "style",
                "sunrise",
                "taxi",
                "tidy",
                "timer",
                "together",
                "tooth",
                "tourist",
                "travel",
                "truck",
                "under",
                "useful",
                "unicorn",
                "unique",
                "uplift",
                "uniform",
                "vase",
                "violin",
                "visitor",
                "vision",
                "volume",
                "view",
                "walrus",
                "wander",
                "world",
                "winter",
                "well",
                "whirlwind",
                "x-ray",
                "xylophone",
                "yoga",
                "yogurt",
                "yoyo",
                "you",
                "year",
                "yummy",
                "zebra",
                "zigzag",
                "zoology",
                "zone",
                "zeal"
            )

        var allSpanishWordsList: MutableList<String> = mutableListOf()
        fun initializeSpanishWords(context: Context) {

            val assetManager: AssetManager = context.resources.assets
            val inputStream = assetManager.open("es.txt", AssetManager.ACCESS_STREAMING)
            inputStream.bufferedReader().forEachLine {
                allSpanishWordsList.add(it)
            }

        }
    }

    private var _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private var _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    private var _language: String = SPANISH
    val language: String
        get() = _language

    private var _listOfWords: List<String> = listOf()

    init {
        Log.d("GameFragment", "GameViewModel created!!")

        _listOfWords = getWordList(_language)
        //getNextWord()
        Log.d("GameFragment", "GameViewModel First word !!")
    }


    private fun getWordList(language: String): List<String> {
        return when (language) {
            SPANISH -> allSpanishWordsList
            else -> allEnglishWordsList
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!!")
    }


    private fun getNextWord() {

        currentWord = _listOfWords.random()

        while (currentWord.count() > 6) {
            currentWord = _listOfWords.random()
        }
        Log.d("GameFragment", "CurrentWord: $currentWord")
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        //val shuffleTempWord = tempWord

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData() {
        _listOfWords = getWordList(_language)
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

    fun changeLanguage(language: String) {
        _language = language
    }

}