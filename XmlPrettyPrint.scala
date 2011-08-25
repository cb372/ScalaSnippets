import scala.xml._
import java.io.{File, FileInputStream}

/* 
 * XML pretty-printing utilities.
 * Assumes that XML files are in UTF-8.
 */
object XmlPP {

  /* Load an XML file and return its contents pretty-printed */
  def prettify(xmlFile: String): String = {
    val xml = XML.load(scala.xml.Source.fromFile(xmlFile))
    val sb = new StringBuilder
    new PrettyPrinter(80, 2).format(xml, sb)
    sb.toString
  }

  /* Load a file and write its pretty-printed contents to outFile */
  def prettify(xmlFile: String, outFile:String) {
    val pretty = prettify(xmlFile)
    printToFile(new File(outFile))(p => {
	p.println("""<?xml version="1.0" encoding="UTF-8" ?>""")
	p.print(pretty)
    })
  }  

  private def printToFile(f: java.io.File, charset: String = "UTF-8")(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f, charset)
    try { op(p) } finally { p.close() }
  }
}
