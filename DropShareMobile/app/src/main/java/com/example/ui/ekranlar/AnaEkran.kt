package com.example.ui.ekranlar

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ag.AagYardimcisi
import com.example.model.CihazModeli
import com.example.model.CihazTuru
import com.example.model.DosyaModeli
import com.example.ui.viewmodel.DropShareViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DropShareAnaArayuz(viewModel: DropShareViewModel) {
    var mevcutSekme by remember { mutableStateOf("ANA") } // "ANA", "ESLESME", "METIN", "GECMIS", "AYARLAR", "WEB_DROP", "PC"
    val snackbarHostState = remember { SnackbarHostState() }

    val gelenIstek by viewModel.gelenIstek.collectAsState()
    val gonderilenAktarim by viewModel.gonderilenAktarim.collectAsState()
    val gelenAktarim by viewModel.gelenAktarim.collectAsState()

    // Bildirim Mesajlarını Dinle
    LaunchedEffect(Unit) {
        viewModel.bildirimMesaji.collect { mesaj ->
            snackbarHostState.showSnackbar(mesaj)
        }
    }

    // Storage Access Framework (SAF) Coklu Dosya Secici
    val cokluDosyaSecici = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            viewModel.dosyaEkleUriListesi(uris)
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = mevcutSekme == "ANA",
                    onClick = { mevcutSekme = "ANA" },
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Ana Ekran") },
                    label = { Text("Ana Ekran") },
                    modifier = Modifier.testTag("nav_ana_ekran")
                )
                NavigationBarItem(
                    selected = mevcutSekme == "ESLESME",
                    onClick = { mevcutSekme = "ESLESME" },
                    icon = { Icon(imageVector = Icons.Default.QrCodeScanner, contentDescription = "Eşleştir") },
                    label = { Text("Eşleştir") },
                    modifier = Modifier.testTag("nav_eslestir")
                )
                NavigationBarItem(
                    selected = mevcutSekme == "METIN",
                    onClick = { mevcutSekme = "METIN" },
                    icon = { Icon(imageVector = Icons.Default.TextFields, contentDescription = "Metin/Link") },
                    label = { Text("Metin/Link") },
                    modifier = Modifier.testTag("nav_metin")
                )
                NavigationBarItem(
                    selected = mevcutSekme == "GECMIS",
                    onClick = { mevcutSekme = "GECMIS" },
                    icon = { Icon(imageVector = Icons.Default.History, contentDescription = "Geçmiş") },
                    label = { Text("Geçmiş") },
                    modifier = Modifier.testTag("nav_gecmis")
                )
                NavigationBarItem(
                    selected = mevcutSekme == "AYARLAR",
                    onClick = { mevcutSekme = "AYARLAR" },
                    icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Ayarlar") },
                    label = { Text("Ayarlar") },
                    modifier = Modifier.testTag("nav_ayarlar")
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (mevcutSekme) {
                "ANA" -> AnaDashboardEkran(
                    viewModel = viewModel,
                    onDosyaSec = { cokluDosyaSecici.launch("*/*") },
                    onFotoSec = { cokluDosyaSecici.launch("image/*") },
                    onVideoSec = { cokluDosyaSecici.launch("video/*") },
                    onSekmeDegistir = { yeniSekme -> mevcutSekme = yeniSekme }
                )
                "ESLESME" -> EslesmeEkran(viewModel)
                "METIN" -> MetinBaglantiEkran(viewModel)
                "GECMIS" -> GecmisEkran(viewModel)
                "AYARLAR" -> AyarlarEkran(viewModel)
                "WEB_DROP" -> WebDropEkran(viewModel)
                "PC" -> PcGorunumEkran(viewModel)
            }

            // Gelen Istek Pop-up Dialog
            gelenIstek?.let { istek ->
                GelenIstekDialog(
                    istek = istek,
                    onKabulEt = { viewModel.gelenIstegiYanitla(istek.istekId, true) },
                    onReddet = { viewModel.gelenIstegiYanitla(istek.istekId, false) }
                )
            }

            // Aktif Aktarım Progress Card Overlay
            val aktifAktarim = gonderilenAktarim ?: gelenAktarim
            if (aktifAktarim != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    TransferEkranKart(
                        aktarim = aktifAktarim,
                        onIptalEt = { viewModel.aktarimiIptalEt() },
                        onKapat = { viewModel.aktarimiSifirla() }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnaDashboardEkran(
    viewModel: DropShareViewModel,
    onDosyaSec: () -> Unit,
    onFotoSec: () -> Unit,
    onVideoSec: () -> Unit,
    onSekmeDegistir: (String) -> Unit
) {
    val bulunanCihazlarMap by viewModel.bulunanCihazlarMap.collectAsState()
    val secilenCihaz by viewModel.secilenCihaz.collectAsState()
    val secilenDosyalar by viewModel.secilenDosyalar.collectAsState()
    val yerelIp by viewModel.yerelIpAdresi.collectAsState()
    val cihazAdi by viewModel.cihazAdi.collectAsState()
    val webDropAktif by viewModel.webDropAktif.collectAsState()

    val cihazListesi = bulunanCihazlarMap.values.toList()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("ana_dashboard_ekran"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // DropShare Header Bar
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "DropShare",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Wifi,
                                contentDescription = "Wi-Fi",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Yerel Ağ: $yerelIp • $cihazAdi",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }

                    if (webDropAktif) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = "WEB DROP",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }

        // Yakındaki Cihazlar Bölümü
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Yakındaki Cihazlar (${cihazListesi.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    IconButton(onClick = { viewModel.ipYenile() }) {
                        Icon(imageVector = Icons.Default.Wifi, contentDescription = "Yenile", tint = MaterialTheme.colorScheme.primary)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (cihazListesi.isEmpty()) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = "Cihaz Aranıyor",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ağda yeni cihazlar aranıyor...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Diğer cihazda da DropShare açık olmalıdır.",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(cihazListesi, key = { it.cihazId }) { cihaz ->
                            val isSecili = secilenCihaz?.cihazId == cihaz.cihazId
                            CihazKarti(
                                cihaz = cihaz,
                                isSecili = isSecili,
                                onSec = { viewModel.cihazSec(if (isSecili) null else cihaz) }
                            )
                        }
                    }
                }
            }
        }

        // Seçilen Dosyalar Önizleme Kartı (Eğer dosya seçildiyse)
        if (secilenDosyalar.isNotEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Seçilen Dosyalar (${secilenDosyalar.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            IconButton(onClick = { viewModel.dosyaSeciminiTemizle() }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Temizle", tint = MaterialTheme.colorScheme.error)
                            }
                        }

                        val toplamBoyut = secilenDosyalar.sumOf { it.dosyaBoyutu }
                        Text(
                            text = "Toplam Boyut: ${AagYardimcisi.baytDönüştür(toplamBoyut)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        secilenDosyalar.take(3).forEach { dosya ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "📄 ${dosya.dosyaAdi}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = AagYardimcisi.baytDönüştür(dosya.dosyaBoyutu),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (secilenDosyalar.size > 3) {
                            Text(
                                text = "+${secilenDosyalar.size - 3} dosya daha...",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Button(
                            onClick = { viewModel.dosyaGonder() },
                            enabled = secilenCihaz != null,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("ana_dosyalanri_gonder_button")
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Gönder")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (secilenCihaz != null) "${secilenCihaz!!.cihazAdi} Cihazına Gönder" else "Lütfen Hedef Cihaz Seçin")
                        }
                    }
                }
            }
        }

        // Büyük "Dosya Bırakmak İçin Dokunun" Alanı
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDosyaSec() }
                    .testTag("dosya_birak_alani")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.UploadFile,
                        contentDescription = "Dosya Seç",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Dosya bırakmak veya seçmek için dokunun",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tüm dosya türleri, fotoğraflar, videolar ve arşivler desteklenir",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Hızlı Aksiyonlar Grid
        item {
            Column {
                Text(
                    text = "Hızlı Aksiyonlar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    maxItemsInEachRow = 2
                ) {
                    HizliAksiyonKarti("Dosya Gönder", Icons.Default.Description, Modifier.weight(1f)) { onDosyaSec() }
                    HizliAksiyonKarti("Fotoğraf Gönder", Icons.Default.Image, Modifier.weight(1f)) { onFotoSec() }
                    HizliAksiyonKarti("Video Gönder", Icons.Default.Movie, Modifier.weight(1f)) { onVideoSec() }
                    HizliAksiyonKarti("Klasör Gönder", Icons.Default.Folder, Modifier.weight(1f)) { onDosyaSec() }
                    HizliAksiyonKarti("Metin Gönder", Icons.Default.TextFields, Modifier.weight(1f)) { onSekmeDegistir("METIN") }
                    HizliAksiyonKarti("Bağlantı Gönder", Icons.Default.Link, Modifier.weight(1f)) { onSekmeDegistir("METIN") }
                }
            }
        }

        // Alternatif Seçenekler (Web Drop & PC Client)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSekmeDegistir("WEB_DROP") }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Language, contentDescription = "Web Drop", tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Web Drop", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Tarayıcı ile paylaş", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSekmeDegistir("PC") }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Computer, contentDescription = "PC Client", tint = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "DropShare PC", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Windows istemcisi", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
fun CihazKarti(
    cihaz: CihazModeli,
    isSecili: Boolean,
    onSec: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSecili) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        border = if (isSecili) CardDefaults.outlinedCardBorder() else null,
        modifier = Modifier
            .width(160.dp)
            .clickable { onSec() }
            .testTag("cihaz_karti_${cihaz.cihazId}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val icon = when (cihaz.cihazTuru) {
                CihazTuru.BILGISAYAR -> Icons.Default.Computer
                CihazTuru.TABLET -> Icons.Default.Smartphone
                CihazTuru.WEB -> Icons.Default.Language
                else -> Icons.Default.PhoneAndroid
            }

            Icon(
                imageVector = icon,
                contentDescription = cihaz.cihazAdi,
                tint = if (isSecili) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = cihaz.cihazAdi,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = cihaz.ipAdresi,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color(0xFF10B981), shape = RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isSecili) "Seçildi" else "Hazır",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun HizliAksiyonKarti(
    baslik: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = baslik, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = baslik,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
