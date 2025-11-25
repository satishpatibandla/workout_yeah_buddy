package com.example.workout.utils

object MotivationalQuotes {
    private val quotes = listOf(
        "Yeah Buddy!",
        "Light Weight Baby!",
        "Ain't Nothin' But A Peanut!",
        "Everybody Wanna Be A Bodybuilder, But Don't Nobody Wanna Lift No Heavy-Ass Weights!",
        "I Do Whatever It Takes To Win!",
        "Yeah! There's Nothin' To It But To Do It!",
        "All Day You May!",
        "Nothin' To It!",
        "Light Weight!",
        "Get Your Mind Right!",
        "Yeah Buddy, Light Weight!",
        "Still Your Set!",
        "Ain't Nothing But A Peanut! Light Weight!",
        "Come On Now!",
        "Real Quick, Get It Done!",
        "Gotta Lift Big To Get Big!",
        "Hard Work And Determination!",
        "Yeah! Can't Stop, Won't Stop!"
    )
    
    fun getRandomQuote(): String {
        return quotes.random()
    }
    
    fun getAllQuotes(): List<String> = quotes
}
