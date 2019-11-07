package com.chsltutorials.snakechallenge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

class SnakeView extends View {

    private Paint paint = new Paint();
    private Type snakeViewMap[][];

    public SnakeView( Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public void setSnakeViewType(Type[][] map){
        snakeViewMap = map;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (snakeViewMap != null) {

            float typeX = canvas.getWidth() / snakeViewMap.length;
            float typeY = canvas.getHeight() / snakeViewMap[0].length;

            float circleSize = Math.min(typeX,typeY);

            for(int x = 0; x < snakeViewMap.length; x++){

                for(int y = 0; y < snakeViewMap[x].length; y++){

                    switch (snakeViewMap[x][y]){
                        case NOTHING:
                            paint.setColor(Color.WHITE);
                            break;

                        case WALL:
                            paint.setColor(Color.RED);
                            break;

                        case SNAKEHEAD:
                            paint.setColor(Color.RED);
                            break;

                        case SNAKETAIL:
                            paint.setColor(Color.GREEN);
                            break;

                        case FOOD:
                            paint.setColor(Color.YELLOW);
                            break;
                    }

                    canvas.drawCircle(
                            x * typeX + typeX / 2f + circleSize / 2,
                            y * typeY + typeY / 2f + circleSize /2,
                            circleSize,
                            paint);

                }
            }


        }
    }
}
