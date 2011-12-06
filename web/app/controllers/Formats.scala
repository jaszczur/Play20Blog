package controllers

import play.api._
import play.api.mvc._

abstract class OutputFormat
case class HTMLFormat extends OutputFormat
case class JSONFormat extends OutputFormat

object OutputFormat {
  def byName(str : String) : OutputFormat = {
    str match {
      case "json" => JSONFormat()
      case _ => HTMLFormat()
    }
  }
}

object Formats {

  def outputFormat( fun : OutputFormat => Result )(implicit request: Request[AnyContent]) : Result = {
    fun(request.queryString.get("format") match {
      case Some(values) => OutputFormat.byName(values(0))
      case None => HTMLFormat()
    })
  }
}


