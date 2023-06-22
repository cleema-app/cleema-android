/*
 * Created by Kumpels and Friends on 2023-01-30
 * Copyright © 2023 Kumpels and Friends. All rights reserved.
 */

package de.cleema.android.marketplace

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.cleema.android.core.models.MarketItem.VoucherRedemption

@Composable
fun OfferRedemptionView(
    offerRedemption: VoucherRedemption,
    modifier: Modifier = Modifier,
    discount: Int? = null,
    onRequestClick: () -> Unit,
    onRedeemClick: () -> Unit,
) {
    when (offerRedemption) {
        is VoucherRedemption.Generic -> OfferRedemptionCodeView(
            offerRedemption.code,
            modifier,
            discount ?: 0,
            onRedeemClick = null
        )
        is VoucherRedemption.Exhausted -> return
        is VoucherRedemption.Pending -> OfferRedemptionPendingView(modifier, discount ?: 0, onRequestClick)
        is VoucherRedemption.Redeemed -> OfferRedemptionCodeView(
            offerRedemption.code,
            modifier,
            discount ?: 0,
            onRedeemClick = onRedeemClick
        )
    }
}
