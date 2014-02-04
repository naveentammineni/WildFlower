package com.Richardson.wff;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.drawable.ColorDrawable;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.PopupMenu;
import org.holoeverywhere.widget.PopupMenu.OnDismissListener;
import org.holoeverywhere.widget.PopupMenu.OnMenuItemClickListener;

public class TabCamera extends Activity
{
  Bitmap bitmap;
  public int[] bottomDrawables = { 2130837819, 2130837744, 2130837788, 2130837748, 2130837967 };
  Camera camera;
  SurfaceView cameraOverlaySurfaceView;
  SurfaceView cameraSurfaceView;
  ImageButton captureButton;
  int currentCameraID = 0;
  int currentOverlayId = 0;
  String filepathString = "";
  boolean lockCameraUse = true;
  SurfaceHolder overlayHolder;
  PopupMenu popupMenu;
  ProgressDialog progressDialog;
  public int[] topDrawables = { 2130837812, 2130837812, 2130837789, 2130837812, 2130837812 };

  private void share(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Intent localIntent1 = new Intent("android.intent.action.SEND");
    localIntent1.setType("image/jpeg");
    List localList = getPackageManager().queryIntentActivities(localIntent1, 0);
    Iterator localIterator = null;
    if (!localList.isEmpty())
      localIterator = localList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        Intent localIntent3 = Intent.createChooser((Intent)localArrayList.remove(0), "Select Twitter App to Share");
        localIntent3.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[])localArrayList.toArray(new Parcelable[0]));
        startActivity(localIntent3);
        return;
      }
      ResolveInfo localResolveInfo = (ResolveInfo)localIterator.next();
      Intent localIntent2 = new Intent("android.intent.action.SEND");
      localIntent2.setType("image/jpeg");
      localIntent2.putExtra("android.intent.extra.TEXT", "#WildFlower");
      localIntent2.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(paramString)));
      localIntent2.setPackage(localResolveInfo.activityInfo.packageName);
      localArrayList.add(localIntent2);
    }
  }

  public int convertToDp(int paramInt)
  {
    return (int)(0.5F + getResources().getDisplayMetrics().density * paramInt);
  }

  public void drawOverlay()
  {
    Canvas localCanvas = this.overlayHolder.lockCanvas();
    localCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inPurgeable = true;
    localOptions.inInputShareable = true;
    Bitmap localBitmap1 = BitmapFactory.decodeResource(getResources(), this.topDrawables[this.currentOverlayId], localOptions);
    Bitmap localBitmap2 = BitmapFactory.decodeResource(getResources(), this.bottomDrawables[this.currentOverlayId], localOptions);
    float f = localCanvas.getWidth() / localBitmap1.getWidth();
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(f, f);
    localCanvas.drawBitmap(localBitmap1, localMatrix, null);
    localMatrix.reset();
    localMatrix.postScale(f, f);
    localMatrix.postTranslate(0.0F, localCanvas.getHeight() - f * localBitmap2.getHeight());
    localCanvas.drawBitmap(localBitmap2, localMatrix, null);
    localBitmap1.recycle();
    localBitmap2.recycle();
    this.overlayHolder.unlockCanvasAndPost(localCanvas);
  }

  public void loadLayoutElements()
  {
    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF8800")));
    this.camera = Camera.open(this.currentCameraID);
    this.captureButton = ((ImageButton)findViewById(2131165354));
    this.captureButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        TabCamera.this.camera.takePicture(null, null, new Camera.PictureCallback()
        {
          public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
          {
            if (paramArrayOfByte != null)
            {
              TabCamera.this.progressDialog = new ProgressDialog(TabCamera.this);
              TabCamera.this.progressDialog.setMessage("Processing");
              TabCamera.this.progressDialog.setCancelable(false);
              TabCamera.this.progressDialog.show();
              new TabCamera.decodeTask().execute(new byte[][] { paramArrayOfByte });
            }
          }
        });
      }
    });
    this.cameraSurfaceView = ((SurfaceView)findViewById(2131165351));
    SurfaceHolder localSurfaceHolder = this.cameraSurfaceView.getHolder();
    localSurfaceHolder.setType(3);
    localSurfaceHolder.addCallback(new SurfaceHolder.Callback()
    {
      public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
      {
        TabCamera.this.camera.setDisplayOrientation(90);
        TabCamera.this.camera.startPreview();
      }

      public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
      {
        try
        {
          TabCamera.this.camera.setPreviewDisplay(paramSurfaceHolder);
          return;
        }
        catch (IOException localIOException2)
        {
          localIOException2.printStackTrace();
          return;
        }
        catch (RuntimeException localRuntimeException)
        {
          TabCamera.this.camera = Camera.open(TabCamera.this.currentCameraID);
          TabCamera.this.camera.startPreview();
          try
          {
            TabCamera.this.camera.setPreviewDisplay(paramSurfaceHolder);
            return;
          }
          catch (IOException localIOException1)
          {
            localIOException1.printStackTrace();
          }
        }
      }

      public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
      {
        if (TabCamera.this.camera != null)
        {
          TabCamera.this.camera.stopPreview();
          TabCamera.this.camera.release();
        }
      }
    });
    this.cameraOverlaySurfaceView = ((SurfaceView)findViewById(2131165353));
    this.cameraOverlaySurfaceView.setZOrderOnTop(true);
    this.overlayHolder = this.cameraOverlaySurfaceView.getHolder();
    this.overlayHolder.setFormat(-2);
    this.overlayHolder.addCallback(new SurfaceHolder.Callback()
    {
      public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
      {
        TabCamera.this.drawOverlay();
      }

      public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
      {
      }

      public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
      {
      }
    });
    ((ImageButton)findViewById(2131165355)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (Camera.getNumberOfCameras() > 1)
        {
          TabCamera localTabCamera = TabCamera.this;
          localTabCamera.currentCameraID = (1 + localTabCamera.currentCameraID);
          Camera.getNumberOfCameras();
          if (Camera.getNumberOfCameras() <= TabCamera.this.currentCameraID)
            TabCamera.this.currentCameraID = 0;
          TabCamera.this.camera.stopPreview();
          TabCamera.this.camera.release();
          TabCamera.this.camera = Camera.open(TabCamera.this.currentCameraID);
        }
        try
        {
          TabCamera.this.camera.setPreviewDisplay(TabCamera.this.cameraSurfaceView.getHolder());
          TabCamera.this.camera.setDisplayOrientation(90);
          TabCamera.this.camera.startPreview();
          return;
        }
        catch (IOException localIOException)
        {
          while (true)
            localIOException.printStackTrace();
        }
      }
    });
    resizeSurfaceViews(this.camera.getParameters().getPreviewSize().width, this.camera.getParameters().getPreviewSize().height);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903122);
    loadLayoutElements();
    if (getIntent().hasExtra("frame"))
      this.currentOverlayId = getIntent().getExtras().getInt("frame");
  }

  protected void onDestroy()
  {
    super.onDestroy();
    EasyTracker.getInstance(getApplicationContext()).activityStop(this);
  }

  protected void onPause()
  {
    if (this.bitmap != null)
      this.bitmap.recycle();
    super.onPause();
  }

  protected void onStart()
  {
    super.onStart();
    EasyTracker.getInstance(getApplicationContext()).activityStart(this);
  }

  public void resizeSurfaceViews(int paramInt1, int paramInt2)
  {
    double d = paramInt1 / paramInt2;
    if (d < 1.0D)
    {
      int i = (int)(d * getWindowManager().getDefaultDisplay().getWidth());
      this.cameraSurfaceView.setLayoutParams(new LinearLayout.LayoutParams(-1, i));
      this.cameraOverlaySurfaceView.setLayoutParams(new LinearLayout.LayoutParams(-1, i));
    }
  }

  public void saveBitmapDialog()
  {
    this.popupMenu = new PopupMenu(this, this.captureButton);
    Menu localMenu = this.popupMenu.getMenu();
    localMenu.add("Tweet #WildFlower");
    localMenu.add("Save");
    localMenu.add("Cancel");
    this.popupMenu.show();
    this.popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener()
    {
      public void onDismiss(PopupMenu paramPopupMenu)
      {
        paramPopupMenu.dismiss();
        TabCamera.this.camera.startPreview();
      }
    });
    this.popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramMenuItem)
      {
        if (paramMenuItem.getTitle().equals("Tweet #WildFlower"))
          new TabCamera.saveTask().execute(new String[] { "tweet" });
        if (paramMenuItem.getTitle().equals("Save"))
          new TabCamera.saveTask().execute(new String[] { "" });
        if (paramMenuItem.getTitle().equals("Cancel"))
        {
          TabCamera.this.popupMenu.dismiss();
          TabCamera.this.bitmap.recycle();
          TabCamera.this.camera.startPreview();
        }
        return false;
      }
    });
  }

  public class decodeTask extends AsyncTask<byte[], Integer, Boolean>
  {
    public decodeTask()
    {
    }

    protected Boolean doInBackground(byte[][] paramArrayOfByte)
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inPurgeable = true;
      localOptions.inInputShareable = true;
      Bitmap localBitmap1 = BitmapFactory.decodeByteArray(paramArrayOfByte[0], 0, paramArrayOfByte[0].length, localOptions);
      paramArrayOfByte[0] = null;
      if (TabCamera.this.bitmap != null)
        TabCamera.this.bitmap.recycle();
      TabCamera.this.bitmap = Bitmap.createBitmap(localBitmap1.getHeight(), localBitmap1.getWidth(), Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(TabCamera.this.bitmap);
      Matrix localMatrix1 = new Matrix();
      localMatrix1.postRotate(90.0F, 0.0F, 0.0F);
      localMatrix1.postTranslate(TabCamera.this.bitmap.getWidth(), 0.0F);
      Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
      Camera.getCameraInfo(TabCamera.this.currentCameraID, localCameraInfo);
      if (localCameraInfo.facing == 1)
      {
        localMatrix1.postScale(1.0F, -1.0F);
        localMatrix1.postTranslate(0.0F, TabCamera.this.bitmap.getHeight());
      }
      localCanvas.drawBitmap(localBitmap1, localMatrix1, null);
      localBitmap1.recycle();
      Bitmap localBitmap2 = BitmapFactory.decodeResource(TabCamera.this.getResources(), TabCamera.this.topDrawables[TabCamera.this.currentOverlayId], localOptions);
      Bitmap localBitmap3 = BitmapFactory.decodeResource(TabCamera.this.getResources(), TabCamera.this.bottomDrawables[TabCamera.this.currentOverlayId], localOptions);
      float f = localCanvas.getWidth() / localBitmap2.getWidth();
      Matrix localMatrix2 = new Matrix();
      localMatrix2.postScale(f, f);
      localCanvas.drawBitmap(localBitmap2, localMatrix2, null);
      localMatrix2.reset();
      localMatrix2.postScale(f, f);
      localMatrix2.postTranslate(0.0F, localCanvas.getHeight() - f * localBitmap3.getHeight());
      localCanvas.drawBitmap(localBitmap3, localMatrix2, null);
      localBitmap2.recycle();
      localBitmap3.recycle();
      System.gc();
      return Boolean.valueOf(true);
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      TabCamera.this.progressDialog.dismiss();
      TabCamera.this.saveBitmapDialog();
      super.onPostExecute(paramBoolean);
    }
  }

  public class saveTask extends AsyncTask<String, Integer, Integer>
  {
    public saveTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      return 1;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if (paramInteger.intValue() == 1)
        Toast.makeText(TabCamera.this, "Image Saved", 0).show();
      if (paramInteger.intValue() == 2)
        TabCamera.this.share(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + TabCamera.this.filepathString);
      super.onPostExecute(paramInteger);
    }
  }
}