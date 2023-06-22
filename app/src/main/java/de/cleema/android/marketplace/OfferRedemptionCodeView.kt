/*
 * Created by Kumpels and Friends on 2023-01-30
 * Copyright © 2023 Kumpels and Friends. All rights reserved.
 */

package de.cleema.android.marketplace

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.cleema.android.R
import de.cleema.android.core.styling.Action
import de.cleema.android.core.styling.CleemaTheme
import de.cleema.android.core.styling.DefaultText
import de.cleema.android.core.styling.RedeemVoucher

@Composable
fun OfferRedemptionCodeView(
    code: String,
    modifier: Modifier = Modifier,
    discount: Int = 0,
    onRedeemClick: (() -> Unit)? = null
) {
    // TODO: simplify
    Box(modifier = modifier) {
        Column(Modifier.height(254.dp)) {
            Spacer(Modifier.weight(1f))

            Surface(
                shape = MaterialTheme.shapes.medium, color = Color.White, shadowElevation = 19.dp, modifier = Modifier
                    .height(165.dp)
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .background(RedeemVoucher)
                            .fillMaxHeight()
                            .weight(0.5f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        if (discount > 0) {
                            Column(
                                horizontalAlignment = Alignment.Start, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    stringResource(R.string.marketplace_voucher_save_title),
                                    style = MaterialTheme.typography.displaySmall,
                                    color = Color.White
                                )

                                Text(
                                    text = stringResource(
                                        R.string.marketplace_voucher_percentage_format_string,
                                        discount
                                    ),
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Box(
                        Modifier
                            .background(Color.White)
                            .fillMaxHeight()
                            .weight(0.5f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End, modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Text(
                                stringResource(R.string.marketplace_voucher_title),
                                style = MaterialTheme.typography.displaySmall,
                                color = Color.LightGray
                            )
                            Text(code, style = MaterialTheme.typography.headlineSmall, color = DefaultText)


                            onRedeemClick?.let { action ->
                                Spacer(Modifier.weight(1f))

                                Button(
                                    onClick = action,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = MaterialTheme.shapes.medium,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Action,
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(stringResource(R.string.market_item_redeem), color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
        Row {
            Image(painterResource(R.drawable.voucher), contentDescription = null, Modifier.weight(0.5f))
            Spacer(Modifier.weight(0.5f))
        }
    }
}

@Preview(widthDp = 360, heightDp = 300, name = "With discount", showBackground = false)
@Composable
fun OfferRedemptionCodeViewPreview() {
    CleemaTheme {
        OfferRedemptionCodeView(
            code = "voucher1",
            Modifier.padding(20.dp),
            discount = 22,
            onRedeemClick = {}
        )
    }
}

@Preview(widthDp = 360, heightDp = 300, name = "Without discount", showBackground = false)
@Composable
fun OfferRedemptionCodeViewPreviewWithoutDiscount() {
    CleemaTheme {
        OfferRedemptionCodeView(
            code = "voucher1",
            Modifier.padding(20.dp),
            discount = 0,
            onRedeemClick = {}
        )
    }
}
