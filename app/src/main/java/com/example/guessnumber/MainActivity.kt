package com.example.guessnumber

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var randomNumber: Int = 0
    private var attempts: Int = 0
    private lateinit var numberInput: EditText
    private lateinit var checkButton: Button
    private lateinit var feedbackText: TextView
    private lateinit var victoryMessage: TextView
    private lateinit var guessedNumberText: TextView
    private lateinit var attemptsCountText: TextView
    private lateinit var inputLayout: LinearLayout
    private lateinit var victoryLayout: LinearLayout
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var restartButton: Button  // Кнопка для перезапуска игры

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация элементов
        numberInput = findViewById(R.id.numberInput)
        checkButton = findViewById(R.id.checkButton)
        feedbackText = findViewById(R.id.feedbackText)
        inputLayout = findViewById(R.id.inputLayout)
        victoryLayout = findViewById(R.id.victoryLayout)
        victoryMessage = findViewById(R.id.victoryMessage)
        guessedNumberText = findViewById(R.id.guessedNumber)
        attemptsCountText = findViewById(R.id.attemptsCount)
        restartButton = findViewById(R.id.restartButton)  // Инициализация кнопки перезапуска

        // Загадать случайное число от 1 до 100
        randomNumber = (1..100).random()

        // Воспроизвести звук при запуске игры
        playSound(R.raw.start_game)

        checkButton.setOnClickListener {
            // Получаем число, введенное пользователем
            val userInput = numberInput.text.toString()

            if (userInput.isNotEmpty()) {
                val guessedNumber = userInput.toIntOrNull()

                if (guessedNumber != null) {
                    attempts++
                    if (guessedNumber == randomNumber) {
                        // Если угадано число, показываем экран победы
                        showVictoryScreen(guessedNumber, attempts)
                    } else {
                        checkGuess(guessedNumber)
                    }
                    numberInput.setText("")  // Очищаем поле ввода
                } else {
                    Toast.makeText(this, "Введите корректное число", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Поле не должно быть пустым", Toast.LENGTH_SHORT).show()
            }
        }

        // Обработчик для кнопки "Попробовать ещё раз"
        restartButton.setOnClickListener {
            restartGame()  // Перезапуск игры
        }
    }

    private fun checkGuess(guessedNumber: Int) {
        // Используем один звук для обоих случаев (больше или меньше)
        playSound(R.raw.error_sound)  // Используем один звук для ошибок

        when {
            guessedNumber < randomNumber -> {
                feedbackText.text = "Попробуйте число больше!"
            }
            guessedNumber > randomNumber -> {
                feedbackText.text = "Попробуйте число меньше!"
            }
        }
    }

    private fun showVictoryScreen(guessedNumber: Int, attempts: Int) {
        // Скрываем экран ввода и показываем экран победы
        inputLayout.visibility = android.view.View.GONE
        victoryLayout.visibility = android.view.View.VISIBLE

        // Устанавливаем текст победы
        victoryMessage.text = "Поздравляем! Вы победили!"
        guessedNumberText.text = "Загаданное число: $guessedNumber"
        attemptsCountText.text = "Количество попыток: $attempts"

        // Воспроизводим звук при победе
        playSound(R.raw.victory)
    }

    // Функция для воспроизведения звука
    private fun playSound(soundResId: Int) {
        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer.start()
    }

    // Перезапуск игры
    private fun restartGame() {
        // Сброс всех данных
        randomNumber = (1..100).random()  // Загадать новое число
        attempts = 0  // Сбросить количество попыток
        numberInput.setText("")  // Очистить поле ввода
        feedbackText.text = ""  // Очистить текст обратной связи

        // Показать экран ввода и скрыть экран победы
        inputLayout.visibility = android.view.View.VISIBLE
        victoryLayout.visibility = android.view.View.GONE

        // Воспроизвести звук при перезапуске игры
        playSound(R.raw.start_game)
    }
}

