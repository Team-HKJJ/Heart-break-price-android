package com.hkjj.heartbreakprice.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hkjj.heartbreakprice.domain.model.WishProduct
import java.text.NumberFormat
import java.util.Locale
import com.hkjj.heartbreakprice.ui.AppColors

@Composable
fun TargetPriceDialog(
    wishProduct: WishProduct,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit,
) {
    var priceInput by remember { mutableStateOf(wishProduct.targetPrice.toString()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "목표 가격 설정", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Text("상품명", style = MaterialTheme.typography.labelMedium)
                Text(wishProduct.name, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))
                Text("현재가", style = MaterialTheme.typography.labelMedium)
                Text(
                    "${NumberFormat.getNumberInstance(Locale.KOREA).format(wishProduct.price)}원",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = priceInput,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            priceInput = newValue
                        }
                    },
                    label = { Text("목표 가격") },
                    placeholder = { Text("예: 300000") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.Primary,
                        focusedLabelColor = AppColors.Primary,
                        cursorColor = AppColors.Primary
                    )
                )
                Text("이 가격 이하로 할인되면 알림을 받습니다", style = MaterialTheme.typography.bodySmall, color = AppColors.Gray500)

                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = AppColors.Gray500)
                    ) {
                        Text("취소")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val price = priceInput.toIntOrNull()
                            if (price != null && price > 0) {
                                onSave(price)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("저장", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TargetPriceDialogPreview() {
    val wishProduct = WishProduct(
        id = "1",
        name = "Canon EOS R6 Mark II 바디",
        price = 3190000,
        originalPrice = 3500000,
        image = "https://images.unsplash.com/photo-1579535984712-92fffbbaa266?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjYW1lcmElMjBwaG90b2dyYXBoeXxlbnwxfHx8fDE3NjgzMjQyMTB8MA&ixlib=rb-4.1.0&q=80&w=1080",
        category = "카메라",
        shop = "G마켓",
        targetPrice = 1000000,
        addedDate = "2026-01-14T14:45:20",
        url=""
    )

    TargetPriceDialog(
        wishProduct = wishProduct,
        onDismiss = {},
        onSave = {}
    )
}