package com.example.wifilocation997.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.wifilocation997.Constant.Constant;
import com.example.wifilocation997.R;
import com.example.wifilocation997.entity.Coordinate;
import com.example.wifilocation997.entity.FingerPrint;
import com.example.wifilocation997.entity.Message;
import com.example.wifilocation997.util.GetWifi;
import com.example.wifilocation997.util.OKHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySurfaceView  extends SurfaceView
        implements SurfaceHolder.Callback, Runnable {

    // SurfaceHolder
    private SurfaceHolder mHolder;
    // 用于绘图的Canvas
    private Canvas mCanvas;
    // 子线程标志位
    private boolean mIsDrawing;
    // 地图图片
    private Bitmap mBitmap;
    // 画笔
    private Paint mPaint;
    // 返回的定位相对坐标
    private float X=0.5F;
    private float Y=0.5F;

    private static final long DOUBLE_CLICK_TIME_SPACE = 300;    //两次点击的间隔
    private float mCurrentScaleMax; //最大缩放倍率
    private float mCurrentScale;    //当前缩放倍率
    private float mCurrentScaleMin; //最小缩放倍率
    private PointF mStartPoint; //
    private volatile PointF mapCenter;  // mapCenter表示地图中心在屏幕上的坐标
    private long lastClickTime; // 记录上一次点击屏幕的时间，以判断双击事件
    private float windowWidth, windowHeight;    //屏幕宽度，屏幕高度
    private float oldRate = 1;
    private float oldDist = 1;
    private float offsetX, offsetY;
    private boolean isShu = true;
    private Status mStatus = Status.NONE;
    private Bitmap location_icon;
    private Path mPath;
    private Switch switch_path;

    private enum Status {
        NONE, ZOOM, DRAG
    };


    public MySurfaceView(Context context) {
        super(context);
        initView();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    //初始化View
    private void initView() {
        //初始化mHolder
        mHolder = getHolder();
        mHolder.addCallback(this);

        //初始化画笔和点
        mPaint = new Paint();
        mStartPoint = new PointF();
        mapCenter = new PointF();

        // 获取屏幕的宽和高
        windowWidth = getResources().getDisplayMetrics().widthPixels;   //宽度就是屏幕宽度
        windowHeight = getResources().getDisplayMetrics().heightPixels*(float)(10f/16f);    //这里由于view未完全创建，不能直接获取view宽高，但可以通过控件的权重和屏幕高度算出来
/*        // 因为view不是全屏，所以获取view宽高
        windowWidth = getWidth();
        windowHeight = getHeight();*/

        Log.d("TEST","W:"+String.valueOf(windowWidth));
        Log.d("TEST","H:"+String.valueOf(windowHeight));

        //定义bitmap，这里写在HomeFragment里了
        //Bitmap map = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.map); //地图图片
        //setBitmap(map);
        location_icon = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.location_icon); //定位图标图片

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        //mHolder.setFormat(PixelFormat.OPAQUE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("PAN","=========surfaceCreated========");
        mIsDrawing = true;
        new Thread(this).start();   //创建surfaceview时新建一个子线程来绘制
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height) {
        Log.d("PAN","=========surfaceChanged========");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("PAN","=========surfaceDestroyed========");
        mIsDrawing = false;
    }

    //设置地图图片
    public void setBitmap(Bitmap bitmap) {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        mBitmap = bitmap;
        // 设置最小缩放为铺满屏幕，最大缩放为最小缩放的4倍
        mCurrentScaleMin = Math.min(windowHeight / mBitmap.getHeight(),
                windowWidth / mBitmap.getWidth());
        mCurrentScale = mCurrentScaleMin;
        mCurrentScaleMax = mCurrentScaleMin * 4;
        mapCenter.set(mBitmap.getWidth() * mCurrentScale / 2,
                mBitmap.getHeight() * mCurrentScale / 2);
        float bitmapRatio = mBitmap.getHeight() / mBitmap.getWidth();
        float winRatio = windowHeight / windowWidth;
        // 判断屏幕铺满的情况，isShu为true表示屏幕横向被铺满，为false表示屏幕纵向被铺满
        if (bitmapRatio <= winRatio) {
            isShu = true;
        } else {
            isShu = false;
        }
        //draw();
    }

    // 处理拖拽事件
    private void drag(MotionEvent event) {
        PointF currentPoint = new PointF();
        currentPoint.set(event.getX(), event.getY());
        offsetX = currentPoint.x - mStartPoint.x;
        offsetY = currentPoint.y - mStartPoint.y;
        Log.d("TEST", String.valueOf(offsetX));
        Log.d("TEST", String.valueOf(offsetY));
        // 以下是进行判断，防止出现图片拖拽离开屏幕
        if (offsetX > 0
                && mapCenter.x + offsetX - mBitmap.getWidth() * mCurrentScale
                / 2 > 0) {
            offsetX = 0;
        }
        if (offsetX < 0
                && mapCenter.x + offsetX + mBitmap.getWidth() * mCurrentScale
                / 2 < windowWidth) {
            offsetX = 0;
        }
        if (offsetY > 0
                && mapCenter.y + offsetY - mBitmap.getHeight() * mCurrentScale
                / 2 > 0) {
            offsetY = 0;
        }
        if (offsetY < 0
                && mapCenter.y + offsetY + mBitmap.getHeight() * mCurrentScale
                / 2 < windowHeight) {
            offsetY = 0;
        }
        mapCenter.x += offsetX;
        mapCenter.y += offsetY;
        //draw();
        mStartPoint = currentPoint;
    }


    // 处理多点触控缩放事件
    private void zoomAction(MotionEvent event) {
        float newDist = spacing(event);
        if (newDist > 10.0f) {
            mCurrentScale = oldRate * (newDist / oldDist);
            if (mCurrentScale < mCurrentScaleMin) {
                mCurrentScale = mCurrentScaleMin;
            } else if (mCurrentScale > mCurrentScaleMax) {
                mCurrentScale = mCurrentScaleMax;
            }

            if (isShu) {
                if (mapCenter.x - mBitmap.getWidth() * mCurrentScale / 2 > 0) {
                    mapCenter.x = mBitmap.getWidth() * mCurrentScale / 2;
                } else if (mapCenter.x + mBitmap.getWidth() * mCurrentScale / 2 < windowWidth) {
                    mapCenter.x = windowWidth - mBitmap.getWidth()
                            * mCurrentScale / 2;
                }
                if (mapCenter.y - mBitmap.getHeight() * mCurrentScale / 2 > 0) {
                    mapCenter.y = mBitmap.getHeight() * mCurrentScale / 2;
                }
            } else {

                if (mapCenter.y - mBitmap.getHeight() * mCurrentScale / 2 > 0) {
                    mapCenter.y = mBitmap.getHeight() * mCurrentScale / 2;
                } else if (mapCenter.y + mBitmap.getHeight() * mCurrentScale
                        / 2 < windowHeight) {
                    mapCenter.y = windowHeight - mBitmap.getHeight()
                            * mCurrentScale / 2;
                }

                if (mapCenter.x - mBitmap.getWidth() * mCurrentScale / 2 > 0) {
                    mapCenter.x = mBitmap.getWidth() * mCurrentScale / 2;
                }
            }
        }
        Log.d("touch", String.valueOf(mCurrentScale));
        //draw();
    }

    // 计算两个触摸点的距离
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        Log.d("touch", String.valueOf((float) Math.sqrt(x * x + y * y)));
        return (float) Math.sqrt(x * x + y * y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (event.getPointerCount() == 1) {
                    // 如果两次点击时间间隔小于一定值，则默认为双击事件
                    if (event.getEventTime() - lastClickTime < DOUBLE_CLICK_TIME_SPACE) {
                        //双击
                    } else {
                        mStartPoint.set(event.getX(), event.getY());
                        mStatus = Status.DRAG;
                    }
                }
                lastClickTime = event.getEventTime();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                float distance = spacing(event);
                if (distance > 10f) {
                    mStatus = Status.ZOOM;
                    oldDist = distance;
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mStatus == Status.DRAG) {
                    drag(event);
                } else if (mStatus == Status.ZOOM) {
                    zoomAction(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mStatus != Status.ZOOM) {
                    //点击标记
                }

            case MotionEvent.ACTION_POINTER_UP:
                oldRate = mCurrentScale;
                mStatus = Status.NONE;
                break;
            default:
                break;
        }

        return true;
    }


/*    float x=0;
    float y=0;
    Path mPath = new Path();*/

    public static final int TIME_IN_FRAME = 30;
    public int count1 = 0;//定位刷新频率
    public int count2 = 0;//路径刷新频率
    @Override
    public void run() {
        //画笔每次的变化
        while(mIsDrawing){
            long startTime = System.currentTimeMillis();

            //获取坐标,要有延迟，2.7s获取一次
            if(count1<90){
                count1++;
            }else{
                getXY();
                count1=0;
            }

            //获取路径,要有延迟，120ms获取一次
            if(count2<100){
                count2++;
            }else{
                getPath();
                count2=96;
            }

            //调用绘图函数
            draw();

            /**取得更新结束的时间**/
            long endTime = System.currentTimeMillis();

            /**计算出一次更新的毫秒数**/
            int diffTime  = (int)(endTime - startTime);

            /**确保每次更新时间为30毫秒**/
            while(diffTime <=TIME_IN_FRAME) {
                diffTime = (int)(System.currentTimeMillis() - startTime);
                /**线程等待**/
                Thread.yield();
            }
        }
    }


    public static final double RelativeDisPerX = 0.0746;    //代表地图上的每格X占到了地图宽度的比例
    public static final double RelativeDisPerY = 0.1012;    //代表地图上的每格Y占到了地图高度的比例

    private void getPath() {
        if(switch_path.isChecked()){
            //抽象图中编号抽象成坐标点
            Map<Integer,PointF> map_point = new HashMap<>();
            map_point.put(0,new PointF(1.3f,1.2f));
            map_point.put(1,new PointF(2.5f,6.5f));
            map_point.put(2,new PointF(3,6));//门
            map_point.put(3,new PointF(3,3.2f));
            map_point.put(4,new PointF(3.6f,1.3f));//门
            map_point.put(5,new PointF(4.3f,3.7f));
            map_point.put(6,new PointF(4.5f,2));//门
            map_point.put(7,new PointF(6.3f,4.8f));
            map_point.put(8,new PointF(6.3f,8.1f));
            map_point.put(9,new PointF(10.3f,4.2f));
            map_point.put(10,new PointF(11.2f,2));//门
            map_point.put(11,new PointF(11,8));
            map_point.put(12,new PointF(13,4.2f));


            //计算当前位置距离最近的展品
            //反算得到当前位置坐标
            float x_cur = (float) (X/RelativeDisPerX);
            float y_cur = (float) (Y/RelativeDisPerY);
            //计算距离，找最小距离
            float min_result=10000;//给个很大的初值
            int min_index=5;//默认为抗美援朝使用武器
            for(int i=0;i<=12;i++){
                //跳过门
                if(i==2||i==4||i==6||i==10){
                    continue;
                }
                float x = x_cur - map_point.get(i).x;
                float y = y_cur - map_point.get(i).y;
                float result = (float) Math.sqrt(x * x + y * y);
                if(result<min_result){
                    min_result=result;
                    min_index=i;
                }
            }

            //文字对应成抽象图中编号，其中2，4，6，10是门
            Map<String,Integer> map_name = new HashMap<>();
            map_name.put("当前位置",min_index);
            map_name.put("长城颂漆画",5);
            map_name.put("马克思手稿",7);
            map_name.put("一大会址复原",8);
            map_name.put("遵义会议复原",9);
            map_name.put("七大投票箱",12);
            map_name.put("开国大典影像",11);
            map_name.put("抗美援朝使用武器",0);
            map_name.put("脱贫攻坚数据",3);
            map_name.put("火神山医院模型",1);
            //获取下拉框选值
            List<Integer> list = new ArrayList<Integer>();
            List<Integer> list_result=null;
            Spinner spinner_start = getRootView().findViewById(R.id.spinner_start);
            Spinner spinner_end = getRootView().findViewById(R.id.spinner_end);
            String str_start=spinner_start.getSelectedItem().toString();
            String str_end=spinner_end.getSelectedItem().toString();
            if(str_start.equals(str_end)){
                //出发点和目的地在一个地方
            }else{
                //将选值的数字放到list并传到服务器
                list.add(map_name.get(str_start));
                list.add(map_name.get(str_end));
                Gson gson = new Gson();
                String json = gson.toJson(list);
                String args[] = new String[]{"navigation", "navigation"};
                String res = null;//服务器传回的json字符串
                res = OKHttpUtil.postSyncRequest(Constant.baseURL_lzl, json, args);
                if (res == null) {
                    //错误
                    Log.e("error","路径对象为空");
                    //在子线程中实现Toast
                    Looper.prepare();
                    Toast.makeText(getContext(),"服务器响应超时",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Log.d("同步:", res);
                    list_result = gson.fromJson(res, new TypeToken<List<Integer>>() {
                    }.getType());
                    mPath = new Path();

                    //坐标数
                    double x = map_point.get(list_result.get(0)).x;
                    double y =map_point.get(list_result.get(0)).y;

                    //占地图的比例
                    float x1=(float)(x*RelativeDisPerX);
                    float y1=(float)(y*RelativeDisPerY);

                    //在屏幕上实际绘制的坐标（像素数）
                    float x2 = mapCenter.x - location_icon.getWidth()
                            / 2 - mBitmap.getWidth()
                            * mCurrentScale / 2 //算到这里是获取当前地图左上角所在屏幕上的坐标（像素数）
                            + mBitmap.getWidth()
                            * x1 //相对坐标
                            * mCurrentScale;    //这三项相乘的结果是在当前缩放比下，目标位置离左边缘的像素数
                    float y2 = mapCenter.y - location_icon.getHeight()
                            - mBitmap.getHeight()
                            * mCurrentScale / 2
                            + mBitmap.getHeight()
                            * y1 //相对坐标
                            * mCurrentScale;


                    mPath.moveTo(x2,y2);
                    for(int i=1;i<list_result.size();i++){
                        //坐标数
                        double xx = map_point.get(list_result.get(i)).x;
                        double yy =map_point.get(list_result.get(i)).y;

                        //占地图的比例
                        float xx1=(float)(xx*RelativeDisPerX);
                        float yy1=(float)(yy*RelativeDisPerY);

                        //在屏幕上实际绘制的坐标（像素数）
                        float xx2 = mapCenter.x - location_icon.getWidth()
                                / 2 - mBitmap.getWidth()
                                * mCurrentScale / 2 //算到这里是获取当前地图左上角所在屏幕上的坐标（像素数）
                                + mBitmap.getWidth()
                                * xx1 //相对坐标
                                * mCurrentScale;    //这三项相乘的结果是在当前缩放比下，目标位置离左边缘的像素数
                        float yy2 = mapCenter.y - location_icon.getHeight()
                                - mBitmap.getHeight()
                                * mCurrentScale / 2
                                + mBitmap.getHeight()
                                * yy1 //相对坐标
                                * mCurrentScale;

                        mPath.lineTo(xx2,yy2);
                    }
                }
            }
        }
    }

    private void getXY() {
        Switch switch_location = getRootView().findViewById(R.id.switch_location);
        if(switch_location.isChecked()){
            GetWifi getWifi = new GetWifi(getContext());
            Coordinate coordinate = getWifi.scanWifi();
            if(coordinate == null){
                //错误
                Log.e("error","坐标对象为空");
                //在子线程中实现Toast
                Looper.prepare();
                Toast.makeText(getContext(),"服务器响应超时",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }else{
                X=(float)(coordinate.getPositionX()*RelativeDisPerX);
                Y=(float)(coordinate.getPositionY()*RelativeDisPerY);
            }
        }
    }

    //绘图操作
    private void draw() {
        Log.d("PAN","============draw========");
        try {
            mCanvas = mHolder.lockCanvas();
            // draw sth绘制过程
            // 定义画笔
            mPaint.setStyle(Paint.Style.STROKE); //只描边
            mPaint.setStrokeWidth(10);
            mPaint.setColor(Color.RED);
            mPaint.setAntiAlias(true); //设置抗锯齿
            mPaint.setDither(true); //设置防抖动


            // 绘制地图图片
            Matrix matrix = null;
            if (mBitmap != null) {
                mCanvas.drawColor(Color.GRAY);
                matrix = new Matrix();
                matrix.setScale(mCurrentScale, mCurrentScale,
                        mBitmap.getWidth() / 2,
                        mBitmap.getHeight() / 2);//以图片中心为原点进行放大，为什么不用mapCenter，因为在处理拖拽时，可能还没拖过去，这个mapCenter可能还不是当前实际上的图片中心，而是将要拖过去的图片中心
                matrix.postTranslate(
                        mapCenter.x - mBitmap.getWidth() / 2,
                        mapCenter.y - mBitmap.getHeight() / 2);//处理拖拽操作
                mCanvas.drawBitmap(mBitmap, matrix, mPaint);
            }
            // 设置图标矩阵缩放，图标不进行缩放
            matrix.setScale(1.0f, 1.0f);
            // 绘制定位点
            // 使用Matrix使得Bitmap的宽和高发生变化，在这里使用的mapX和mapY都是相对值
            matrix.postTranslate(
                    mapCenter.x - location_icon.getWidth()
                            / 2 - mBitmap.getWidth()
                            * mCurrentScale / 2 //算到这里是获取当前地图左上角所在屏幕上的坐标（像素数）
                            + mBitmap.getWidth()
                            * X //相对坐标
                            * mCurrentScale,    //这三项相乘的结果是在当前缩放比下，目标位置离左边缘的像素数
                    mapCenter.y - location_icon.getHeight()
                            - mBitmap.getHeight()
                            * mCurrentScale / 2
                            + mBitmap.getHeight()
                            * Y //相对坐标
                            * mCurrentScale);
            mCanvas.drawBitmap(location_icon, matrix, mPaint);

            //绘制路径
            switch_path = getRootView().findViewById(R.id.switch_path);
            if(switch_path.isChecked()){
                if(mPath !=null){
                    mCanvas.drawPath(mPath,mPaint);
                    Log.d("PAN","path");
                }
            }else{
                mPath=null;
            }


        } catch (Exception e) {
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);//保证每次都将绘图的内容提交
        }
    }
}