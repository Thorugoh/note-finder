package com.thorugoh.notetrainer

enum class Note(val noteName: String, val enharmonic: String? = null) {
    A("A"),
    ASHARP("A#", "Bb"),
    B("B"),
    C("C"),
    CSHARP("C#", "Db"),
    D("D"),
    DSHARP("D#", "Eb"),
    E("E"),
    F("F"),
    FSHARP("F#", "Gb"),
    G("G"),
    GSHARP("G#", "Ab");
}