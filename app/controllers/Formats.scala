package controllers

import play.api._
import play.api.mvc._

sealed trait OutputFormat
case object HTMLFormat extends OutputFormat
case object JSONFormat extends OutputFormat

object OutputFormat {
  def byName(str : String) : OutputFormat = {
    str match {
      case "json" => JSONFormat
      case _ => HTMLFormat
    }
  }
}

object Formats {

  def outputFormat( fun : OutputFormat => Result )(implicit request: Request[AnyContent]) : Result = {
    fun(request.queryString.get("format") match {
      case Some(values) => OutputFormat.byName(values(0))
      case None => HTMLFormat
    })
  }
}


