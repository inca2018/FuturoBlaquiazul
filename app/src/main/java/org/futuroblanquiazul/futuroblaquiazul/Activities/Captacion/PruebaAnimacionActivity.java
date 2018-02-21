package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.R;


public class PruebaAnimacionActivity extends Activity implements View.OnTouchListener {

    ImageView image;


    Matrix matrix =new Matrix();
    Matrix saveMatrix=new Matrix();
    static final int NONE=0;
    static final int DRAG=1;
    static final int ZOOM=2;
    int mode=NONE;
    private static final String TAG="Touch";
    PointF start =new PointF();
    PointF mid = new PointF();
    float oldDist =1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_captacion);
         image=(ImageView)findViewById(R.id.image_prueba);
         image.setOnTouchListener(this);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view =(ImageView)v;
        dumpEvent(event);

        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
               saveMatrix.set(matrix);
               start.set(event.getX(),event.getY());
               Log.d(TAG,"mode=DRAG");
               mode=DRAG;

               break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
              mode=NONE;
                Log.d(TAG,"mode=NONE");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist=spacing(event);
                Log.d(TAG,"Olddist"+oldDist);
                 if(oldDist>10f){
                     saveMatrix.set(matrix);
                     midPoint(mid,event);
                     mode=ZOOM;
                     Log.d(TAG,"mode=ZOOM");
                 }
                break;
            case MotionEvent.ACTION_MOVE:
                if(mode==DRAG){
                    matrix.set(saveMatrix);
                    matrix.postTranslate(event.getX()-start.x,event.getY()-start.y);

                }else if(mode==ZOOM){
                    float newDist=spacing(event);
                    Log.d(TAG,"newDist="+newDist);

                    if(newDist>10f){
                        matrix.set(saveMatrix);
                        float scale=newDist/oldDist;
                        matrix.postScale(scale,scale,mid.x,mid.y);
                    }
                }
                break;

        }
        view.setImageMatrix(matrix);

        return true;
    }

    private void midPoint(PointF point,MotionEvent event){
             float x=event.getX(0)+event.getY(1);
             float y=event.getX(0)+event.getY(1);
             point.set(x/2,y/2);
    }
    private float spacing(MotionEvent event){
        float x=event.getX(0)+event.getY(1);
        float y=event.getX(0)+event.getY(1);
        return (float)Math.sqrt(x*x+y*y);
    }
    private void dumpEvent(MotionEvent event){
      String names[]={"DOWN","UP","MOVE","CANCEL","OUTSIDE","POINTER_DOWN","POINTER_UP","7?","8?","9?"};
      StringBuilder sb=new StringBuilder();
      int action=event.getAction();
      int actionCode=action & MotionEvent.ACTION_MASK;
      sb.append("evento ACTION_").append(names[actionCode]);
      if(actionCode==MotionEvent.ACTION_POINTER_DOWN ||  actionCode == MotionEvent.ACTION_POINTER_UP){
          sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
          sb.append(")");
      }
      sb.append("[");
      for(int i=0;i<event.getPointerCount();i++){
          sb.append("#").append(i);
          sb.append("(pid ").append(event.getPointerId(i));
          sb.append(")").append((int) event.getX(i));
          sb.append(",").append((int)event.getY(i));
          if(i+1 < event.getPointerCount());
          sb.append(";");

      }

      sb.append("]");
        Log.d(TAG,sb.toString());
    }
}
