lazy val root = project
  .in(file("."))
  .settings(
    name         := "advent-of-code",
    description  := "sleepless december",
    version      := "0.1.0",
    scalaVersion := "3.1.0",
    libraryDependencies ++= Seq(
      ("org.scalaj"            %% "scalaj-http"                % "2.4.2").cross(CrossVersion.for3Use2_13),
      "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
      ("com.outr"              %% "hasher"                     % "1.2.2").cross(CrossVersion.for3Use2_13),
      "org.scalatest"          %% "scalatest"                  % "3.2.10" % "test"
    ),
    resolvers += DefaultMavenRepository,
    resolvers += "Maven Central Server" at "https://repo1.maven.org/maven2",
    Test / logBuffered := false
  )
