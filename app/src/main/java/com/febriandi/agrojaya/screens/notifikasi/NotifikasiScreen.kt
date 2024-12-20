package com.febriandi.agrojaya.screens.notifikasi

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonBack
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource
import kotlinx.coroutines.launch


@Composable
fun NotifikasiScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchNotifications()
    }

    val notificationState by viewModel.notifications.collectAsState()
    val deleteStatus by viewModel.deleteStatus.collectAsState()

    LaunchedEffect(deleteStatus) {
        deleteStatus?.let { status ->
            when (status) {
                is Resource.Success -> {
                    Toast.makeText(context, "Notifikasi berhasil dihapus", Toast.LENGTH_SHORT).show()
                    viewModel.resetDeleteStatus()
                }
                is Resource.Error -> {
                    Toast.makeText(context, status.message, Toast.LENGTH_SHORT).show()
                    viewModel.resetDeleteStatus()
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ButtonBack {
                    navController.popBackStack()
                }
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Notifikasi",
                    fontSize = 16.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.text_color)
                )
            }

            IconButton(
                onClick = {
                    scope.launch {
                        viewModel.deleteAllNotifications()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus Semua Notifikasi",
                    tint = colorResource(id = R.color.text_color)
                )
            }
        }

        when (val resource = notificationState) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                if (resource.data.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tidak ada notifikasi",
                            textAlign = TextAlign.Center,
                            fontFamily = CustomFontFamily,
                            color = colorResource(id = R.color.text_color)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = resource.data,
                            key = { notification -> notification.id }
                        ) { notification ->
                            NotifikasiItem(notification)
                        }

                    }
                }
            }
            is Resource.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Gagal memuat notifikasi: ${resource.message}",
                        textAlign = TextAlign.Center,
                        color = Color.Red,
                        fontFamily = CustomFontFamily
                    )
                }
            }
        }
    }
}

