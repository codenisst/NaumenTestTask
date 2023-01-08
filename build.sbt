ThisBuild / scalaVersion := "2.13.10"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """NaumenTestTask""",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )

libraryDependencies ++= Seq("com.typesafe.play" %% "play" % "2.8.18")

libraryDependencies += "com.typesafe.slick" %% "slick" % "3.4.1"
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.39.2.1"
libraryDependencies += "org.webjars" % "swagger-ui" % "4.15.0"
libraryDependencies += "com.iterable" %% "swagger-play" % "2.0.1"
libraryDependencies += "io.swagger" %% "swagger-scala-module" % "1.0.6"
libraryDependencies ++= Seq("org.flywaydb" %% "flyway-play" % "7.25.0")