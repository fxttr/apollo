import scala.xml.parsing.NoBindingFactoryAdapter
import scala.xml._

object HtmlParser extends NoBindingFactoryAdapter {
  def loadXML(source: InputSource): Node = {
    import nu.validator.htmlparser.{common, sax}
    import common.XmlViolationPolicy
    import sax.HtmlParser

    val reader = new HtmlParser
    reader.setXmlPolicy(XmlViolationPolicy.ALLOW)
    reader.setContentHandler(this)
    reader.parse(source)
    rootElem
  }
}
