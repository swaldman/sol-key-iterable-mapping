name := "sol-key-iterable-mapping"

version := "0.0.1-SNAPSHOT"

ethcfgScalaStubsPackage := "keyiterablemapping.contract"

libraryDependencies += "org.specs2" %% "specs2-core" % "4.0.2" % "test"

Test / parallelExecution := false

Test / ethcfgAutoDeployContracts := Seq( "KeyIterableMappingHolder" )






