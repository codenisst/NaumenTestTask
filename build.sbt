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

libraryDependencies ++=Seq("com.typesafe.play" %% "play" % "2.8.18")
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.4.1"
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.39.2.1"
