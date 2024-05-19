package com.yajhz.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yajhz.BuildConfig
import com.yajhz.R
import java.io.*

object ImageUtils {
    fun getPath(uri: Uri?, activity: Activity): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            activity.contentResolver.query(uri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    fun getRealPathFromURI(
        context: Context,
        contentUri: Uri?
    ): String? {
        val out: OutputStream
        val file = File(getFilename(context))
        try {
            if (file.createNewFile()) {
                val iStream =
                    context.contentResolver.openInputStream(contentUri!!)
                val inputData = getBytes(iStream)
                out = FileOutputStream(file)
                out.write(inputData)
                out.close()
                return file.absolutePath
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    @Throws(IOException::class)
    private fun getBytes(inputStream: InputStream?): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream!!.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    private fun getFilename(context: Context): String {
        val mediaStorageDir =
            File(context.getExternalFilesDir(""), "image_data")
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs()
        }
        val mImageName =
            "IMG_" + System.currentTimeMillis().toString() + ".png"
        return mediaStorageDir.absolutePath + "/" + mImageName
    }

    fun reduceImageSize(file: File): File? {
        return try {

            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: Exception) {
            null
        }
    }

    fun loadImage(imgUrl: String, imageView: ImageView, context: Context) {
        Glide.with(context)
            .load(imgUrl)
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
            .into(imageView)

//        Picasso.get()
//            .load(imgUrl)
//            .fit()
//            .centerCrop()
//            .placeholder(R.color.bg_color)
//            .error(R.color.bg_color)
//            .into(imageView)
    }

    fun loadImageBase(imgUrl: String?, imageView: ImageView, context: Context) {
        Glide.with(context)
            .load(BuildConfig.IMAGE_BASE_URL + imgUrl)
            .placeholder(R.color.bg_color)
            .error(R.color.bg_color)
            .into(imageView)
    }


    fun rotate(imageView: ImageView, factor: Int) {
        val animSet = AnimationSet(true)
        animSet.interpolator = DecelerateInterpolator()
        animSet.fillAfter = true
        animSet.isFillEnabled = true
        val animRotate = RotateAnimation(
            0.0f, -180.0f * factor,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )
        animRotate.duration = 1000
        animRotate.fillAfter = true
        animSet.addAnimation(animRotate)
        imageView.startAnimation(animSet)
    }


}