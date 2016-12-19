name := "SANSA-Template-SBT-Spark"

version := "0.1.0"

scalaVersion := "2.11.8"

val varscalaVersion = "2.11.8"
val varscalaBinaryVersion = "2.11"
val sansaVersion = "0.1.0"

val sparkVersion = "2.0.0"

val sparkV = "2.0.0"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core"      % sparkV % "provided",
  "org.apache.spark" %% "spark-sql"       % sparkV % "provided",
  "org.apache.spark" %% "spark-hive"      % sparkV % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkV % "provided",
  "org.apache.spark" %% "spark-mllib"     % sparkV % "provided"
)


libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-library" % varscalaVersion
  , "org.apache.commons" % "commons-lang3" % "3.3.2"
  , "jline" % "jline" % "2.12.1" % "provided"
  , "org.slf4j" % "slf4j-api" % "1.7.10"
)


// | SANSA Layers

// | Extra libraries

resolvers ++= Seq(
  "AKSW Maven Releases" at "http://maven.aksw.org/archiva/repository/internal",
  "AKSW Maven Snapshots" at "http://maven.aksw.org/archiva/repository/snapshots",
  "oss-sonatype" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Apache repository (snapshots)" at "https://repository.apache.org/content/repositories/snapshots/"
)	

// Use local repositories by default
resolvers ++= Seq(
  Resolver.defaultLocal,
  Resolver.mavenLocal,
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  "Apache Staging" at "https://repository.apache.org/content/repositories/staging/"
  )

// | SANSA Layers
libraryDependencies ++= Seq(
    "net.sansa-stack" % "sansa-rdf-spark-bundle" % sansaVersion,
    "net.sansa-stack" % "sansa-owl-spark" % sansaVersion,
    "net.sansa-stack" %% "sansa-inference-parent" % sansaVersion,
    "net.sansa-stack" %% "sansa-inference-spark" % sansaVersion,
    "net.sansa-stack" % "sansa-query-spark-bundle" % sansaVersion,
    "net.sansa-stack" % "sansa-ml-spark" % sansaVersion
)
  
