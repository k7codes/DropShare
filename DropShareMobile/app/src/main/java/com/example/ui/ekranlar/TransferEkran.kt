package com.example.ui.ekranlar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ag.AagYardimcisi
import com.example.model.AktarimBilgisi
import com.example.model.AktarimDurumu

@Composable
fun TransferEkranKart(
    aktarim: AktarimBilgisi,
    onIptalEt: () -> Unit,
    onKapat: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("transfer_ekran_kart")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (aktarim.isGelen) "📥 Gelen Aktarım" else "📤 Gönderilen Aktarım",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                val durumMetni = when (aktarim.durum) {
                    AktarimDurumu.BEKLIYOR -> "Bekliyor..."
                    AktarimDurumu.ONAY_BEKLIYOR -> "Onay Bekleniyor..."
                    AktarimDurumu.AKTARILIYOR -> "Aktarılıyor"
                    AktarimDurumu.DURAKLATILDI -> "Duraklatıldı"
                    AktarimDurumu.TAMAMLANDI -> "Tamamlandı!"
                    AktarimDurumu.REDDEDILDI -> "Reddedildi"
                    AktarimDurumu.HATA -> "Hata Oluştu"
                }

                Text(
                    text = durumMetni,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (aktarim.durum == AktarimDurumu.TAMAMLANDI) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = aktarim.mevcutDosyaAdi,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            val aktarilanStr = AagYardimcisi.baytDönüştür(aktarim.aktarilanBayt)
            val toplamStr = AagYardimcisi.baytDönüştür(aktarim.toplamBayt)

            Text(
                text = "$aktarilanStr / $toplamStr",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            val ilerleme = if (aktarim.toplamBayt > 0) (aktarim.aktarilanBayt.toFloat() / aktarim.toplamBayt.toFloat()).coerceIn(0f, 1f) else 0f

            LinearProgressIndicator(
                progress = { ilerleme },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .testTag("transfer_progress_bar"),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val hizStr = AagYardimcisi.hizDönüştür(aktarim.aktarimHiziBaytSn)
                val sureStr = AagYardimcisi.sureFormati(aktarim.kalanSureSn)

                Text(
                    text = if (aktarim.durum == AktarimDurumu.AKTARILIYOR) "$hizStr • $sureStr" else "Cihaz: ${aktarim.hedefCihazAdi}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                val yuzde = (ilerleme * 100).toInt()
                Text(
                    text = "%$yuzde",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (aktarim.hataMesaji != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Error, contentDescription = "Hata", tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = aktarim.hataMesaji,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (aktarim.durum == AktarimDurumu.AKTARILIYOR || aktarim.durum == AktarimDurumu.ONAY_BEKLIYOR) {
                    OutlinedButton(
                        onClick = onIptalEt,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("transfer_iptal_button")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "İptal")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("İptal Et")
                    }
                } else {
                    Button(
                        onClick = onKapat,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("transfer_kapat_button")
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Kapat")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Tamam")
                    }
                }
            }
        }
    }
}
