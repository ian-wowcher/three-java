package model

/**
 * Created on 22/08/2014.
 */
case class Deal(
                    id: Int,
                    closeTime: String,
                    product: String,
                    title: String,
                    description: String,
                    prices: DealPrices,
                    businessName: String,
                    categoryId: String,
                    scheduledLocationName: String,
                    finePrint: Option[String],
                    highlights: List[String],
                    emailSubject: String,
                    purchaseCount: Int,
                    soldOrLeftCount: Int,
                    minLiveDeals: Int,
                    soldOrLeftText: String,
                    addresses: List[Address],
                    locations: List[Location],
                    imageLinks: ImageLinks,
                    conditions: DealConditions
                    ) {
                         def toSimpleDeal = SimpleDeal(id, title, product, locations)
                       }
