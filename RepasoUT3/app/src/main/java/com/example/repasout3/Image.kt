package com.example.repasout3

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.repasout3.databinding.ImageBinding
import java.io.IOException

class Image : AppCompatActivity() {
    private lateinit var imageView: ImageView

    companion object{
        val TAKE_PHOTO = 1
        val SELECT_IMAGE = 2
        val CAMERA_REQUEST_CODE = 123
    }

    private lateinit var viewBinding : ImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ImageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.takePhotoButton.setOnClickListener{
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                showMessage("La App tiene los permisos para la camara")
                takePhoto()
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
            }
        }
        viewBinding.selectImagenButton.setOnClickListener {
            val intetogale = Intent()
            intetogale.type = "image/*"
            intetogale.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intetogale, SELECT_IMAGE)
        }

        // Get the action bar and add a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView = viewBinding.imageView
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK){
            val image = data!!.extras?.get("data") as Bitmap
            imageView.setImageBitmap(image)
        }else if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK){
            val uri: Uri? = data!!.data
            try{
                val image1 = MediaStore.Images.Media.getBitmap(contentResolver,uri)
                imageView.setImageBitmap(image1)
            }catch (e: IOException){}
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //Con el when comprobamos la respuesta del usuario a la petición de permisos
        when (requestCode){
            CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    showMessage("Permiso concedido")
                    takePhoto()
                }else{
                    showMessage("Permiso concedido")
                }
                return
            }
            else -> {
                showMessage("Otro permiso.")
            }
        }
    }

    // Action bar back button click listener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this@Image, MainActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun takePhoto() {
        val tryTakePhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(tryTakePhoto, TAKE_PHOTO)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}