package com.example.submissionjaranannusantara

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class DetailJaranan : AppCompatActivity() {

    companion object {
        const val key_jaranan = "key_jaranan"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jaranan)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val dataJaranan = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Jaranan>(key_jaranan, Jaranan::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Jaranan>(key_jaranan)
        }

        val viewDetailName: TextView = findViewById(R.id.view_name_item)
        val viewDetailDescription: TextView = findViewById(R.id.view_description_item)
        val viewDetailPhoto: ImageView = findViewById(R.id.img_item_photo)

        if (dataJaranan!==null) {
            viewDetailName.text = dataJaranan.name
            viewDetailDescription.text = dataJaranan.description
            viewDetailPhoto.setImageResource(dataJaranan.photo)

            val btnShare: Button = findViewById(R.id.action_share)
            btnShare.setOnClickListener{
                shareJarananDetail(dataJaranan)
            }
        }
    }

    private fun shareJarananDetail(jaranan: Jaranan) {
        val text = "Tahukah kamu kesenian ${jaranan.name} ?\n" +
                "\n ${jaranan.description}\n"

        val drawable = getDrawable(jaranan.photo)
        val bitmap = (drawable as BitmapDrawable).bitmap

        val cachePath = File(cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "image.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()

        val imageUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Bagikan Melalui"))
    }
}