package model

/**
* Created on 22/08/2014.
*/
case class DealsListing(filter: ListDealsOptions, total: Int = 3, totalPages: Int, deals: List[Deal])
