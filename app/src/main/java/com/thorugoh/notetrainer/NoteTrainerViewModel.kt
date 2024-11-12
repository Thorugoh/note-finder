package com.thorugoh.notetrainer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.AudioProcessor
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TIMER = 60
sealed interface NoteToFindUiState {
    object Loading : NoteToFindUiState
    data class Ready(val note: String, val score: Int, val timeLeft: Int) : NoteToFindUiState
    data class EndGame(val score: Int) : NoteToFindUiState

}

class NoteTrainerViewModel() : ViewModel() {
    var noteToFindUiState: NoteToFindUiState by mutableStateOf(NoteToFindUiState.Loading)

    var score by mutableIntStateOf(0)
    var timeLeft by mutableIntStateOf(TIMER)
    var currentNote by mutableStateOf(Note.entries.random().noteName)

    private val dispatcher: AudioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0)

    private val noteFrequencies = mapOf(
        82.41f to "E2", 87.31f to "F2", 92.50f to "F#2", 98.0f to "G2",
        103.83f to "G#2", 110.0f to "A2", 116.54f to "A#2", 123.47f to "B2",
        130.81f to "C3", 138.59f to "C#3", 146.83f to "D3", 155.56f to "D#3",
        164.81f to "E3", 174.61f to "F3", 185.0f to "F#3", 196.0f to "G3",
        207.65f to "G#3", 220.0f to "A3", 233.08f to "A#3", 246.94f to "B3",
        261.63f to "C4", 277.18f to "C#4", 293.66f to "D4", 311.13f to "D#4",
        329.63f to "E4", 349.23f to "F4", 369.99f to "F#4", 392.0f to "G4",
        415.3f to "G#4", 440.0f to "A4", 466.16f to "A#4", 493.88f to "B4",
        523.25f to "C5", 554.37f to "C#5", 587.33f to "D5", 622.25f to "D#5",
        659.26f to "E5", 698.46f to "F5", 739.99f to "F#5", 783.99f to "G5",
        830.61f to "G#5", 880.0f to "A5", 932.33f to "A#5", 987.77f to "B5",
        1046.5f to "C6", 1108.73f to "C#6", 1174.66f to "D6", 1244.51f to "D#6",
        1318.51f to "E6"
    )

    init {
        startGame()
        startPitchDetection()
    }

     fun startGame() {
         score = 0;
         loadNewNote()
         startTimer()
     }

    private fun loadNewNote(){
        currentNote = Note.entries.random().noteName
        noteToFindUiState = NoteToFindUiState.Ready(note = currentNote, score = score, timeLeft = timeLeft)
    }

    private fun startTimer(){
        timeLeft = TIMER
        viewModelScope.launch {
            while (timeLeft > 0){
                delay(1000)
                timeLeft--
            }
            noteToFindUiState = NoteToFindUiState.EndGame(score = score)
        }
    }


    private fun startPitchDetection() {
        val pitchDetectionHandler = PitchDetectionHandler { result, _ ->
            val pitchInHz = result.pitch
            if (pitchInHz > 0) {
                val detectedNote = getNoteFromFrequency(pitchInHz)
                if (detectedNote.contains(currentNote)) {
                    onNoteFound()
                }
            }
        }

        val pitchProcessor: AudioProcessor = PitchProcessor(
            PitchProcessor.PitchEstimationAlgorithm.FFT_YIN,
            22050f,
            1024,
            pitchDetectionHandler
        )

        dispatcher.addAudioProcessor(pitchProcessor)
        viewModelScope.launch(Dispatchers.Default) {
            dispatcher.run()
        }
    }

    private fun getMargin(frequency: Float): Float {
        return when {
            frequency < 130.81f -> 2.0f   // Lower frequencies
            frequency < 261.63f -> 3.0f   // Mid-range frequencies
            else -> 4.0f                  // Higher frequencies
        }
    }

    private fun getNoteFromFrequency(frequency: Float): String {
        val margin = getMargin(frequency)
        val closestNote = noteFrequencies.entries.minByOrNull { Math.abs(it.key - frequency) }

        return if (closestNote != null && Math.abs(closestNote.key - frequency) <= margin) {
            closestNote.value
        } else {
            ""
        }
    }

    override fun onCleared() {
        super.onCleared()
        dispatcher.stop()
    }


    private fun onNoteFound() {
        when (val currentState = noteToFindUiState) {
            is NoteToFindUiState.Ready -> {
                score += 1
                loadNewNote() // Load a new note to find
            }
            else -> {
                // Do nothing
            }
        }
    }

}