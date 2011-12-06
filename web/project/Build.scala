import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "Play20Blog"
    val appVersion      = "1.0"

//    override def ivyRepositories = Seq(Resolver.defaultLocal)

    val appDependencies = Seq(
      // Add your project dependencies here,
      "jaszczur" %% "blog-domain" % "DYNAMIC_SNAPSHOT"
    )


    val main = PlayProject(appName, appVersion, appDependencies).settings(defaultScalaSettings:_*).settings(
      resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
//      resolvers += "Local Ivy Repository" at "file://"+Path.userHome.absolutePath+"/.ivy2/local"
      //ivyRepositories += Resolver.defaultLocal(None)
    )

}
