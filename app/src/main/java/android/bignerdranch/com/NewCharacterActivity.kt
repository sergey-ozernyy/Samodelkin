package android.bignerdranch.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch


private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

private var Bundle.characterData
        get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
        set(value) = putSerializable(CHARACTER_DATA_KEY, value)


class NewCharacterActivity : AppCompatActivity() {
    private var characterData = CharacterGenerator.generate()

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.characterData = characterData
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterData = savedInstanceState?.characterData ?:
            CharacterGenerator.generate()
        generateButton.setOnClickListener{
            launch(UI) {
                characterData = fetchCharacterData().await()
                displayChracterData()
            }

        }

        displayChracterData()

    }

    private fun displayChracterData(){
        characterData.run {
            nameTextView.text = name
            raceTextView.text = race
            dexterityTextView.text = dex
            wisdomTextView.text = wis
            strengthTextView.text = str
        }
    }
}
