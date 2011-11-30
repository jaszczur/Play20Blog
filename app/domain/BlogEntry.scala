package domain

import java.util.Date
import anorm.{Pk, NotAssigned}

case class BlogEntry(
  id: Pk[Long],
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
    BlogEntry(NotAssigned, title, content, location, creationDate)
  }
}


