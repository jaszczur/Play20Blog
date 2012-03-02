import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "blog-web"
    val appVersion      = "1.0"

//    override def ivyRepositories = Seq(Resolver.defaultLocal)

    val appDependencies = Seq(
      // Add your project dependencies here,
//      "jaszczur" %% "blog-domain" % "DYNAMIC_SNAPSHOT"
    )

    val domainSubProject = Project("blog-domain", file("modules/domain"))

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).dependsOn(domainSubProject)
//    .settings(
//      resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
//    )

}
