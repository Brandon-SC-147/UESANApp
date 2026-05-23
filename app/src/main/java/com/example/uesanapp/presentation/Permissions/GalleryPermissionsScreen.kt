package com.example.uesanapp.presentation.Permissions

import android.R
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil3.compose.rememberAsyncImagePainter

@Composable
fun GaleryPermissionsScreen(){
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
        ){ uri: Uri? ->
        if (uri != null) selectedImageUri = uri
    }
    // Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    )
    { isGranted: Boolean ->
        if (isGranted) {
            pickImageLauncher.launch("image/*")
        }
    }

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text("Selecciona una imagen de su galería",
          style=MaterialTheme.typography.titleLarge)
        Button(
            onClick = {
                val isGranted = ContextCompat.checkSelfPermission(context, permission)
                if (isGranted == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    pickImageLauncher.launch("image/*")
                } else {
                    permissionLauncher.launch(permission)
                }
            }
        ){
            Text("Seleccionar Imagen")
        }
        selectedImageUri?.let{
            Spacer(modifier = Modifier.height( 20.dp))
            Image(
                painter = rememberAsyncImagePainter( it),
                contentDescription = "Imagen Seleccionada",
                modifier = Modifier.size(250.dp).padding(8.dp)
            )
        }
    }
}

