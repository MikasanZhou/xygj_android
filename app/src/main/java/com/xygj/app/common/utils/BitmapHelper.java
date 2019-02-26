package com.xygj.app.common.utils;

/**
 * bitmap 工具类
 * Created by xuyougen on 2018/2/28.
 */

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;


public class BitmapHelper {

    /**
     * 压缩图片大小
     *
     * @param image 源Bitmap
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 根据图片的Uri得到它的路径
     *
     * @param activity
     * @param imageUri
     * @return 返回null表示不能找到此图片
     */
    public static String getImagePathFromImageUri(Activity activity, Uri imageUri) {

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(imageUri, filePathColumn, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();

            if (imagePath == null || imagePath.equals("null")) {
                Toast toast = Toast.makeText(activity, "不能发现图片", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;
            }

            return imagePath;
        } else {
            File file = new File(imageUri.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(activity, "不能发现图片", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;
            }
            return file.getAbsolutePath();
        }
    }

    /**
     * 根据图片的路径得到图片资源(压缩后)
     * 如果targetW或者targetH为0就自动压缩
     *
     * @param path
     * @param
     * @return 压缩后的图片
     */
    public static Bitmap getYaSuoBitmapFromImagePath(String path, int targetW, int targetH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = calculateInSampleSize(options);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(path, options);

        if (src == null) {
            return null;
        }
        Bitmap bitmap = null;

        if (targetH == 0 || targetW == 0) {
            bitmap = Bitmap.createScaledBitmap(src, width / inSampleSize, height / inSampleSize, false);
        } else {
            bitmap = Bitmap.createScaledBitmap(src, targetW, targetH, false);
        }

        if (src != bitmap) {
            src.recycle();
        }

        return bitmap;
    }

    /**
     * 计算压缩比
     *
     * @param options
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options) {
        int height = options.outHeight;
        int width = options.outWidth;

        int min = height > width ? width : height;
        int inSampleSize = min / 400;

        if (inSampleSize == 0) return 1;
        return inSampleSize;
    }


    /**
     * 根据图片的路径得到该图片在表中的ID
     *
     * @param cr
     * @param fileName
     * @return
     */
    public static String getImageIdFromPath(ContentResolver cr, String fileName) {

        //select condition.
        String whereClause = MediaStore.Images.Media.DATA + " = '" + fileName + "'";

        //colection of results.
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID}, whereClause, null, null);
        if (cursor == null || cursor.getCount() == 0) {
            if (cursor != null)
                cursor.close();
            return null;
        }
        cursor.moveToFirst();
        //imageView id in imageView table.
        String imageId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        cursor.close();
        if (imageId == null) {
            return null;
        }
        return imageId;
    }

    /**
     * 根据图片的ID得到缩略图
     *
     * @param cr
     * @param imageId
     * @return
     */
    public static Bitmap getThumbnailsFromImageId(ContentResolver cr, String imageId) {
        if (imageId == null || imageId.equals(""))
            return null;

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        long imageIdLong = Long.parseLong(imageId);
        //via imageid get the bimap type thumbnail in thumbnail table.
        bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, imageIdLong, MediaStore.Images.Thumbnails.MINI_KIND, options);

        return bitmap;
    }

    /**
     * 获得圆角图片的方法
     *
     * @param bitmap  源Bitmap
     * @param roundPx 圆角大小
     * @return 期望Bitmap
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}