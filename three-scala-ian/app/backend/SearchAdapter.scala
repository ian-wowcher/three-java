package backend

import model.{SimpleDealsListing, Location, SimpleDeal}

/** I have bodged a "search" adapter here.
* In Three, getDeals returns a Future[DealsListing]
  *
  * Here, I am instead retrieving a list of simple deals.
  */


class SearchAdapter {

    def getDeals(/* TODO filter*/) : /*TODO Future[DealListing]*/SimpleDealsListing = {

      val deals_list = List(
        SimpleDeals.deal_1,
        SimpleDeals.deal_2,
        SimpleDeals.deal_3
      )
      SimpleDealsListing(deals_list);
    }
  }

object SimpleDeals {

  val deal_1 = SimpleDeal(1, "title 1", "product 1", List(Locations.london, Locations.oxford))
  val deal_2 = SimpleDeal(1, "title 2", "product 2", List(Locations.birmingham))
  val deal_3 = SimpleDeal(1, "title 3", "product 3", List(Locations.london))

}

object Locations {

  val london = Location("london", "London")
  val oxford = Location("oxford", "Oxford")
  val birmingham = Location("birmingham", "Birmingham")

}