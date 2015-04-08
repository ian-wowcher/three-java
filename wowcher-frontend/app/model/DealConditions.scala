package model

/**
 * Created on 22/08/2014.
 */
case class DealConditions(
                              hideDiscount: Boolean,
                              priceIndicative: Boolean,
                              soldOut: Boolean,
                              dayClosing: Boolean,
                              expiringSoon: Boolean,
                              hideBought: Boolean,
                              showDiscountAmount: Boolean,
                              mainDeal: Boolean,
                              dealOn: Boolean,
                              dealPurchasable: Boolean,
                              userExceededMaxPurchase: Boolean,
                              buyNowDeal: Boolean,
                              dealOpen: Boolean,
                              dealTipped: Boolean,
                              adultDeal: Boolean,
                              multiplePriceDealVoucher: Boolean,
                              multiplePriceDealVoucherWithDiffPrices: Boolean,
                              hideTimer: Option[Boolean])
