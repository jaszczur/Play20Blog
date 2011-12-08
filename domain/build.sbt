organization := "jaszczur"

name := "blog-domain"

version := "DYNAMIC_SNAPSHOT"

scalaVersion := "2.9.1"

publishMavenStyle := true

publishTo := Some(Resolver.file("Local Maven Repo", new File(Path.userHome + "/.m2/repository")))

