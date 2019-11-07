package com.chsltutorials.snakechallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {

    private static final int  GAMEWIDHT = 28;
    private static final int  GAMEHEIGHT = 42;

    private List<Coordinates> walls = new ArrayList<>();
    private List<Coordinates> snake = new ArrayList<>();
    private List<Coordinates> foods = new ArrayList<>();

    private Random random = new Random();

    private Coordinates foodToRemove = null;

    private boolean increaseTail = false;

    private Directions currentDirection = Directions.EAST;

    private GameState currentGameState = GameState.RUNNING;

    public Coordinates getSnakeHead(){
        return snake.get(0);
    }

    public void initNewGame() {
        addWalls();
        addSnake();
        addFood();
    }

    private void addFood() {
        Coordinates coordinates = null;
        boolean added = false;

        while (!added){
            int x = 1 + random.nextInt(GAMEWIDHT - 2);
            int y = 1 + random.nextInt(GAMEHEIGHT - 2);

            coordinates = new Coordinates(x,y);
            boolean collision = false;
            for (Coordinates s : snake){
                if (s.equals(coordinates)){
                    collision = true;
                    break;
                }
            }

            for (Coordinates f : foods){
                if (f.equals(coordinates)){
                    collision = true;
                }
            }
            added = !collision;
        }



        foods.add(coordinates);
    }

    private void addSnake() {
        snake.clear();

        snake.add(new Coordinates(7,7));
        snake.add(new Coordinates(6,7));
        snake.add(new Coordinates(5,7));
        snake.add(new Coordinates(4,7));
        snake.add(new Coordinates(3,7));
        snake.add(new Coordinates(2,7));
    }

    private void addWalls() {
        for (int x = 0; x < GAMEWIDHT; x++) {
            walls.add(new Coordinates(x,0));
            walls.add(new Coordinates(x,GAMEHEIGHT - 1));
        }

        for (int y = 1; y < GAMEHEIGHT; y++) {
            walls.add(new Coordinates(0,y));
            walls.add(new Coordinates(GAMEWIDHT - 1,y));
        }

    }

    public void updateDirection(Directions newDirection){
        if (Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1){
            currentDirection = newDirection;
        }
    }

    public void update(){

        if (MainActivity.Companion.getGameState()){
            currentGameState = GameState.RESTARTED;
        }

        switch (currentDirection){
            case NORTH:
                updateSnakeDirections(0,1);
                break;

            case EAST:
                updateSnakeDirections(1,0);
                break;

            case WEST:
                updateSnakeDirections(-1,0);
                break;

            case SOUTH:
                updateSnakeDirections(0,-1);
                break;
        }

        for (Coordinates w : walls){
            if (snake.get(0).equals(w)){
                currentGameState = GameState.LOST;
            }
        }

        for (int i = 1; i< snake.size() ; i++){

            if (getSnakeHead().equals(snake.get(i))){
                currentGameState = GameState.LOST;
                return;
            }
        }


        for (Coordinates food : foods){
            if (getSnakeHead().equals(food)){
                foodToRemove = food;
                increaseTail = true;
            }
        }

        if (foodToRemove != null){
            foods.remove(foodToRemove);
            addFood();
        }

    }



    private void updateSnakeDirections(int x, int y){

        int newX = snake.get(snake.size() - 1).getX();
        int newY = snake.get(snake.size() - 1).getY();

        for (int i = snake.size() - 1 ; i > 0 ; i--){
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }

        if (increaseTail){
            snake.add(new Coordinates(newX,newY));
            increaseTail = false;
        }

        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);
    }

    public Type[][] getMap(){

        Type[][] map = new Type[GAMEWIDHT][GAMEHEIGHT];

        for (int x = 0; x < GAMEWIDHT; x++) {
            for (int y = 0; y < GAMEHEIGHT; y++) {
                map[x][y] = Type.NOTHING;
            }
        }

        for (Coordinates s : snake){
            map[s.getX()][s.getY()] = Type.SNAKETAIL;
        }

        for (Coordinates wall : walls){
            map[wall.getX()][wall.getY()] = Type.WALL;
        }

        for (Coordinates f : foods){
            map[f.getX()][f.getY()] = Type.FOOD;
        }

        map[snake.get(0).getX()][snake.get(0).getY()] = Type.SNAKEHEAD;

        return map;
    }

    public GameState getCurrentGameState(){
        return currentGameState;
    }


}
