lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """wordpw""",
    organization := "com.noppaz",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      guice
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
