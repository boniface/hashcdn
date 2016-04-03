package conf.util

import javax.inject.Inject


import play.api.http.HttpFilters
import play.filters.cors.CORSFilter

//
///**
//  * Created by hashcode on 2016/03/09.
//  */
class HashFilters @Inject() (corsFilter: CORSFilter) extends HttpFilters {
  def filters = Seq(corsFilter)
}
