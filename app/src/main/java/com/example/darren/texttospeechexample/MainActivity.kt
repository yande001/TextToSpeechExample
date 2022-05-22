package com.example.darren.texttospeechexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.darren.texttospeechexample.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityMainBinding? = null
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)

        binding?.btnPress?.setOnClickListener {
            if(binding?.etInputText?.text!!.isEmpty()){
                Toast.makeText(
                    this@MainActivity,
                    "Enter a text to speak.",
                    Toast.LENGTH_LONG
                ).show()
            } else{
                speakOut(binding?.etInputText?.text.toString())
            }
        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language not supported!")
            }
        } else{
            Log.e("TTS","Initialization failed!")
        }

    }

    private fun speakOut(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()

        tts?.let {
            it.stop()
            it.shutdown()
        }

        binding = null
    }
}