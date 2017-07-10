import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.scala_people",// Convention: Reverse domain name
      scalaVersion := "2.12.1",
      version      := "0.2.0-SNAPSHOT"// Convention: "-SNAPSHOT" suffix for non-release versions
    )),
    name := "Scala People",
    libraryDependencies ++= Seq(
       scalaTest % Test,
       postgresqlDep
    )
  )
