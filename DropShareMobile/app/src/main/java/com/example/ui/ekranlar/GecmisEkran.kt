package com.example.ui.ekranlar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ag.AagYardimcisi
import com.example.ui.viewmodel.DropShareViewModel
import com.example.veritabani.TransferGecmisiEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun GecmisEkran(viewModel: DropShareViewModel) {
    val tumGecmis by viewModel.tumGecmis.collectAsState()
    var secilenFiltre by remember { mutableStateOf("TUMU") } // "TUMU", "GONDERILEN", "ALINAN"

    val filtrelenmisGecmis = when (secilenFiltre) {
        "GONDERILEN" -> tumGecmis.filter { it.yon == "GONDERILDI" }
        "ALINAN" -> tumGecmis.filter { it.yon == "ALINDI" }
        else -> tumGecmis
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("gecmis_ekrani"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Transfer Geçmişi",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Gönderilen ve alınan tüm dosya kayıtları.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (tumGecmis.isNotEmpty()) {
                    IconButton(
                        onClick = { viewModel.gecmisiTemizle() },
                        modifier = Modifier.testTag("gecmis_temizle_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Temizle",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

        // Filtreleme Seçenekleri
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = secilenFiltre == "TUMU",
                    onClick = { secilenFiltre = "TUMU" },
                    label = { Text("Tümü (${tumGecmis.size})") }
                )
                FilterChip(
                    selected = secilenFiltre == "GONDERILEN",
                    onClick = { secilenFiltre = "GONDERILEN" },
                    label = { Text("Gönderilenler") }
                )
                FilterChip(
                    selected = secilenFiltre == "ALINAN",
                    onClick = { secilenFiltre = "ALINAN" },
                    label = { Text("Alınanlar") }
                )
            }
        }

        if (filtrelenmisGecmis.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Boş Geçmiş",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Henüz transfer kaydı bulunmuyor.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(filtrelenmisGecmis, key = { it.id }) { item ->
                GecmisOgesiKart(item)
            }
        }
    }
}

@Composable
fun GecmisOgesiKart(item: TransferGecmisiEntity) {
    val isGonderilen = item.yon == "GONDERILDI"
    val isBasarili = item.durum == "BASARILI"
    val format = SimpleDateFormat("dd MMM, HH:mm", Locale("tr"))
    val tarihStr = format.format(Date(item.tarihMs))

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isGonderilen) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                contentDescription = item.yon,
                tint = if (isGonderilen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.dosyaAdi,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${if (isGonderilen) "Hedef: " else "Kaynak: "}${item.hedefKaynakCihaz} • ${AagYardimcisi.baytDönüştür(item.dosyaBoyutu)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = tarihStr,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = if (isBasarili) Icons.Default.CheckCircle else Icons.Default.Error,
                contentDescription = item.durum,
                tint = if (isBasarili) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
            )
        }
    }
}
