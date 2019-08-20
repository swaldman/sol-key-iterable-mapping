name := "sol-key-iterable-mapping"

version := "0.0.1-SNAPSHOT"

ethcfgScalaStubsPackage := "keyiterablemapping.contract"

libraryDependencies += "org.specs2" %% "specs2-core" % "4.0.2" % "test"

Test / parallelExecution := false

Test / ethcfgAutoDeployContracts := Seq( "KeyIterableMappingHolder" )

import java.io.File
import java.nio.file.Files

val TemplateText = {
  val TemplateUrl = this.getClass().getClassLoader().getResource("template.sol")
  val source = scala.io.Source.fromURL( TemplateUrl )
  try {
    source.getLines().mkString("\n")
  }
  finally {
    source.close()
  }
}

def fillTemplate( keyType : String, valueType : String, optLibName : Option[String] = None ) : ( String, String ) = {
  val libName = {
    optLibName match {
      case Some( str ) => str
      case None        => s"${keyType.capitalize}${valueType.capitalize}KeyIterableMapping"
    }
  }
  ( s"${libName}.sol", TemplateText.replaceAll( """\$KEY\$""", keyType ).replaceAll( """\$VALUE\$""", valueType ).replaceAll( """\$LIBNAME\$""", libName ) )
}

def fillTemplateFile( parentDir : File, keyType : String, valueType : String, optLibName : Option[String] = None ) : File = {
  val ( filename, contents ) = fillTemplate( keyType, valueType, optLibName )
  val outFile = new File( parentDir, filename )
  Files.write( outFile.toPath, contents.getBytes( scala.io.Codec.UTF8.charSet ) )
  outFile
}

val parser = {
  import sbt.complete.Parsers._
  for {
    _         <- Space
    keyType   <- NotSpace
    _         <- Space
    valueType <- NotSpace
    mbLibName <- (Space ~> NotSpace).?
  } yield {
    ( keyType, valueType, mbLibName )
  }
}

val generateKeyIterableMapping = inputKey[File]("Generates a templated KeyIterableMapping")

generateKeyIterableMapping := {
  val parentDir = ( Compile / ethcfgSoliditySource ).value
  val ( keyType, valueType, mbLibName ) = parser.parsed
  fillTemplateFile( parentDir, keyType, valueType, mbLibName )
}



