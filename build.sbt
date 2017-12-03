name := "email-send"

version := "1.0"

scalaVersion := "2.12.2"

lazy val akkaVersion = "2.5.4"

unmanagedClasspath in Runtime += baseDirectory.value / "files"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "org.iq80.leveldb"            % "leveldb"          % "0.7",
"org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8",
  "javax.mail" % "javax.mail-api" % "1.6.0",
  "com.sun.mail" % "javax.mail" % "1.6.0",
  "com.opencsv" % "opencsv" % "4.0",
"junit" % "junit" % "4.12")

mainClass in(Compile, run) := Some("com.emaildispatch.EmailDispatchInitiator")