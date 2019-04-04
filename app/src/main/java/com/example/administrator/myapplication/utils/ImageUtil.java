package com.example.administrator.myapplication.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtil {
    public static final int REQUEST_FROM_ALBUM=10000;
    public static final int REQUEST_FROM_CAMERA=10001;
    public static final int REQUEST_FROM_CROP=10002;

    private static Uri latestImage;

    private ImageUtil(){
        //不实例化
    }

    /**
     * 取得缩放后的图像，默认低质量
     * @param path 路径
     * @param limit 限制大小
     * @return
     */
    public static Bitmap getThumbBitmap(String path, int limit){
        return getThumbBitmap(path, limit, false);
    }

    /**
     * 取得缩放后的图像
     * @param path 路径
     * @param limit 限制大小
     * @param highQuality 质量renderscriptTargetApi 15
    renderscriptSupportModeEnabled true
     * @return
     */
    public static Bitmap getThumbBitmap(String path, int limit, boolean highQuality){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(path,options);
        int max= Math.max(options.outHeight,options.outWidth);

        options.inJustDecodeBounds=false;
        if(max>limit)
            options.inSampleSize = max / limit+1;
        if(!highQuality)
            options.inPreferredConfig= Bitmap.Config.ARGB_4444;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 写图片文件 文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @throws IOException
     */
    public static void saveImage(Context context, String fileName, Bitmap bitmap) throws IOException {
        saveImage(context, fileName, bitmap, 100);
    }

    public static void saveImage(Context context, String fileName, Bitmap bitmap, int quality) throws IOException {
        if (bitmap == null || fileName == null || context == null)
            return;

        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
        byte[] bytes = stream.toByteArray();
        fos.write(bytes);
        fos.close();
    }

    /**
     * Bitmap转Drawable
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap){
        Drawable drawable=new BitmapDrawable(bitmap);
        return drawable;
    }

    /**
     * Drawable转Bitmat
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable){
        Bitmap bitmap= Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 预览相册
     * @param activity
     */
    public static void openAlbum(Activity activity){
        openAlbum(activity, false);
    }

    @TargetApi(18)
    public static void openAlbum(Activity activity, boolean multiChoose){
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        else
            intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.CATEGORY_OPENABLE, true);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiChoose);
        activity.startActivityForResult(intent, REQUEST_FROM_ALBUM);
    }

    public static void openCamera(Activity activity){
        openCamera(activity, Uri.fromFile(getTempFile(null))); //默认写在存储卡内
    }

    /**
     * 指定照片存储位置启动相机
     * @param activity
     * @param output
     */
    public static void openCamera(Activity activity, Uri output){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,output);
        latestImage=output;

        activity.startActivityForResult(intent, REQUEST_FROM_CAMERA);
    }

    /**
     * 裁剪图片
     * @param activity
     * @param data
     * @param crop
     * @param outPut
     */
    public static void startActionCrop(Activity activity, Uri data, int crop, Uri outPut) {
        Intent intent = new Intent("com.android.camera.action.CROP", null);
        intent.setDataAndType(data, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPut);
        intent.putExtra("circleCrop", true);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", crop);// 输出图片大小
        intent.putExtra("outputY", crop);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        activity.startActivityForResult(intent,REQUEST_FROM_CROP);
    }

    /**
     * 创建可指定父级的临时文件
     * @param parent
     */
    public static File getTempFile(@Nullable File parent){
        File file=null;
        if(parent!=null&&parent.exists())
            file=new File(parent.getAbsolutePath()+ File.separator+ Long.toString(System.currentTimeMillis())+".tmp");
        else{
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+ Environment.getDataDirectory()+ File.separator+ System.currentTimeMillis()+".tmp");
        }
        return file;
    }

    /**
     * 获取来自某种动作的照片uri.如果授权失败或者没有选择任何照片，返回null
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public static Uri getImageFromActive(int requestCode, int resultCode, Intent data){
        if(resultCode!= Activity.RESULT_OK)
            return null;

        switch (requestCode){
            case REQUEST_FROM_CAMERA:
                return latestImage;
            case REQUEST_FROM_ALBUM:
                return data.getData();
            default:
                return null;
        }
    }

    /**
     * 获取图片路径
     * @param context
     * @param uri
     * @return
     */
    public static String getImagePath(Context context, Uri uri){
        boolean isKitKat= Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT;

        if(isKitKat)
            return getImagePathForNewSdk(context,uri);
        else
            return getImagePathForOldSdk(context,uri);
    }

    /**
     * 获取图片路径(老sdk)
     *
     * @param context
     * @param uri
     */
    public static String getImagePathForOldSdk(Context context, Uri uri) {

        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            String ImagePath = cursor.getString(columnIndex);
            cursor.close();
            return ImagePath;
        }

        return uri.toString();
    }

    /**
     * 获取图片路径（新sdk）
     *
     * @param uri
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImagePathForNewSdk(Context context, Uri uri){
        if(DocumentsContract.isDocumentUri(context,uri)){
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {split[1]};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(contentUri,new String[]{MediaStore.Images.Media.DATA}, selection, selectionArgs,
                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    return cursor.getString(index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }
        else{
            String scheme=uri.getScheme();
            if(scheme.equals("file"))
                return uri.getPath();
        }
        return null;
    }


    /**
     * 高斯模糊，默认值25
     * @param context
     * @param raw
     * @return
     */
//    public static Bitmap blurBitmap(Context context,Bitmap raw){
//        return blurBitmap(context,raw,25);
//    }
    /**
     * 高斯模糊
     * @return
     */
//    public static Bitmap blurBitmap(Context context,Bitmap raw,int radius){
//        RenderScript rs= RenderScript.create(context);
//        Bitmap bitmap=Bitmap.createBitmap(raw.getWidth(), raw.getHeight(), Bitmap.Config.ARGB_8888);
//        Allocation allocIn=Allocation.createFromBitmap(rs, raw);
//        Allocation allocOut=Allocation.createFromBitmap(rs,bitmap);
//        ScriptIntrinsicBlur blur=ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//
//        if(radius<0)
//            radius=0;
//        else if(radius>25)
//            radius=25;
//        blur.setRadius(radius);
//        blur.setInput(allocIn);
//        blur.forEach(allocOut);
//        allocOut.copyTo(bitmap);
//        rs.destroy();
//        return bitmap;
//    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 1080, 1920);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     //     * 压缩图片（质量压缩）
     //     * @param bitmap
     //     */
    public static File compress(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static  void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps==null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }

    /**
     * 获取文件长度
     * @param file
     */
    public  static void getFileSize(File file) {
        if (file.exists() && file.isFile()) {
            String fileName = file.getName();
            System.out.println("文件"+fileName+"的大小是："+getNetFileSizeDescription(file.length()));
        }
    }

    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        }
        else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        }
        else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        }
        else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            }
            else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }
}
