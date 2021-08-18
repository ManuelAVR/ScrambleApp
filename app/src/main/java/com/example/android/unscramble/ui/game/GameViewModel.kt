package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambleWord: String
        get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init{
        Log.d("GameFragment","GameViewModel created!!")
        //initializeSpanishWords(context)
        getNextWord()
        Log.d("GameFragment","GameViewModel First word !!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment","GameViewModel destroyed!!")
    }

    private fun getNextWord(){

        currentWord = allSpanishWordsList.random()

        while(currentWord.count() > 6 ){
            currentWord = allSpanishWordsList.random()
        }
        Log.d("GameFragment","Word: $currentWord")
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        //val shuffleTempWord = tempWord

        while(tempWord.toString().equals(currentWord,false)){
            tempWord.shuffle()
        }

        if(wordsList.contains(currentWord)){
            getNextWord()
        }else{
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun nextWord(): Boolean{
        return if (currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else false
    }

    private fun increaseScore(){
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if(playerWord.equals(currentWord,true)){
            increaseScore()
            return true
        }
        return false
    }

}