package domain

import java.util.Date

case class BlogEntry(
  id: Option[Long],
  title: String,
  content: String,
  location: String,
  creationDate: Date
) {
  def isNew() : Boolean = creationDate.after(new Date(System.currentTimeMillis() - 30*1000))
}


object BlogEntry {
  def apply(
      title: String,
      content: String,
      location: String,
      creationDate: Date) : BlogEntry = {
    BlogEntry(None, title, content, location, creationDate)
  }
}


