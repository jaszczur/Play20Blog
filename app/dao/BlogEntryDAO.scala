package dao

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.Date

import domain._

class BlogEntryDAO extends DAO[Long, BlogEntry] {
  // -- Utils

  implicit def pk2option[ID](pk : Pk[ID]) : Option[ID] = pk.toOption

  // -- Parsers
  
  val simpleParser = {
    def parseDate(s: String) : Date = new Date(java.lang.Long.valueOf(s))
  
    get[Pk[Long]]("blog_entry.id") ~ 
    get[String]("blog_entry.title") ~
    get[Date]("blog_entry.creation_date") map {
      case id~title~creationDate => BlogEntry(
        id, title, "", creationDate, Location(0.0, 0.0, "")
      )
    }
  }

  val fullParser = {
    def parseDate(s: String) : Date = new Date(java.lang.Long.valueOf(s))
  
    get[Pk[Long]]("blog_entry.id") ~ 
    get[String]("blog_entry.title") ~
    get[String]("blog_entry.content") ~
    get[Date]("blog_entry.creation_date") ~
    get[Double]("location.latitude") ~
    get[Double]("location.longitude") ~
    get[String]("location.name") map {
      case id~title~content~creationDate~latitude~longitude~locName => BlogEntry(
        id, title, content, creationDate, Location(latitude, longitude, locName)
      )
    }
  }
  
  // -- Queries
  
  override def find(id: Long): Option[BlogEntry] = {
    DB.withConnection { implicit connection =>
      SQL("select * from blog_entry join location on location.id = blog_entry.location_id where blog_entry.id = {id}").on(
        'id -> id
      ).as(fullParser *).headOption
    }
  }
  
  override def list(): Seq[BlogEntry] = {
    DB.withConnection { implicit connection =>
      SQL("select * from blog_entry order by creation_date desc").as(simpleParser *)
    }
  }
  
  override def save(entry: BlogEntry): BlogEntry = {
    DB.withConnection { implicit connection =>
      
      val id: Long = entry.id.getOrElse {
        SQL("select next value for blog_entry_seq").as(scalar[Long].single)
      }
      
      val formattedDate = {
        val h2DateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        h2DateFormatter.format(entry.creationDate)
      }

      SQL(
        """
          insert into location (id, latitude, longitude, name) values ({id}, {latitude}, {longitude}, {name})
        """
      ).on(
        'id -> id,
        'latitude -> entry.location.latitude,
        'longitude -> entry.location.longitude,
        'name -> entry.location.name
      ).executeUpdate()
      
      SQL(
        """
          insert into blog_entry (
            id, title, content, creation_date, location_id
          ) values (
            {id}, {title}, {content}, {creationDate}, {locationId}
          )
        """
      ).on(
        'id -> id,
        'title -> entry.title,
        'content -> entry.content,
        'creationDate -> formattedDate,
        'locationId -> id
      ).executeUpdate()
      
      entry.copy(id = Id(id))
    }
  }
}

