package com.chsltutorials.snakechallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnTouchListener {


    private var prevX : Float? = 0f
    private var prevY : Float? = 0f
    lateinit var gameEngine : GameEngine
    val handler = Handler()
    val updateDelay : Long = 175



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameEngine = GameEngine()

        init()

    }

    private fun init() {
        gameEngine.initNewGame()

        snackView.setSnakeViewType(gameEngine.map)
        snackView.invalidate()

        snackView.setOnTouchListener(this)

        startUpdateGame()

    }

    private fun onGameLost(){
        Toast.makeText(this,"VOCÊ PERDEU", Toast.LENGTH_SHORT).show()
    }

    private fun startUpdateGame() {

        handler.postDelayed(object : Runnable{
            override fun run() {
                gameEngine.update()

                if (gameEngine.currentGameState == GameState.RUNNING) {
                    handler.postDelayed(this, updateDelay )
                }
               if (gameEngine.currentGameState == GameState.LOST) {
                    onGameLost()
               }

                snackView.setSnakeViewType(gameEngine.map)
                snackView.invalidate()
            }

        },updateDelay)

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                prevX = event.x
                prevY = event.y
            }
            MotionEvent.ACTION_UP -> {
                val newX = event.x
                val newY = event.y

                if(Math.abs(newX - prevX!!) > Math.abs(newY - prevY!!)){
                    if (newX > prevX!!){
                        //RIGHT
                        gameEngine.updateDirection(Directions.EAST)
                    }else{
                        //LEFT
                        gameEngine.updateDirection(Directions.WEST)
                    }
                }else{
                    if (newY > prevY!!){
                        //UP
                        gameEngine.updateDirection(Directions.NORTH)
                    }else{
                        //DOWN
                        gameEngine.updateDirection(Directions.SOUTH)
                    }
                }

            }
        }
        return true
    }

    companion object{
        private var gameRestarted = false

        fun getGameState() = gameRestarted
    }


}
