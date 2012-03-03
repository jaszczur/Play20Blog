package domain

import java.util.Date

case class BlogEntry(
  id: Option[Long],
  title: String,
  content: String,
  creationDate: Date,
  location: Location
) {
  def isNew() : Boolean = creationDate.after(new Date(System.currentTimeMillis() - 10*1000))
}


object BlogEntry {
  def apply(
      title: String,
      content: String,
      creationDate: Date,
      location: Location) : BlogEntry = {
    BlogEntry(None, title, content, creationDate, location)
  }
}


