name := "SANSA-Template-SBT-Spark"

version := "0.6.1-SNAPSHOT"

scalaVersion := "2.11.11"

val varscalaVersion = "2.11.11"
val varscalaBinaryVersion = "2.11"
val sansaVersion = "0.6.1-SNAPSHOT"

val sparkVersion = "2.4.3"


dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core"      % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql"       % sparkVersion % "provided"
)

libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-library" % varscalaVersion
  , "com.intel.analytics.bigdl" % "bigdl-SPARK_2.2" % "0.4.0-SNAPSHOT"
  , "javax.ws.rs" % "javax.ws.rs-api" % "2.1" artifacts( Artifact("javax.ws.rs-api", "jar", "jar"))
)

// | Resolvers
resolvers ++= Seq(
  "AKSW Maven Releases" at "http://maven.aksw.org/archiva/repository/internal",
  "AKSW Maven Snapshots" at "http://maven.aksw.org/archiva/repository/snapshots",
  "oss-sonatype" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Apache repository (snapshots)" at "https://repository.apache.org/content/repositories/snapshots/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/", "NetBeans" at "http://bits.netbeans.org/nexus/content/groups/netbeans/", "gephi" at "https://raw.github.com/gephi/gephi/mvn-thirdparty-repo/",
  Resolver.defaultLocal,
  Resolver.mavenLocal,
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  "Apache Staging" at "https://repository.apache.org/content/repositories/staging/"
  )

// | SANSA Layers
libraryDependencies ++= Seq(
    "net.sansa-stack" %% "sansa-rdf-spark" % sansaVersion,
    "net.sansa-stack" %% "sansa-owl-spark" % sansaVersion,
    "net.sansa-stack" %% "sansa-inference-spark" % sansaVersion,
    "net.sansa-stack" %% "sansa-query-spark" % sansaVersion,
    "net.sansa-stack" %% "sansa-ml-spark" % sansaVersion
)