import mouse.all.anySyntaxMouse
import org.xml.sax.InputSource
import sttp.client3._
import sttp.client3.httpclient.zio.HttpClientZioBackend
import sttp.model.Uri
import zio.{Task, ZIO}

import java.io.{File, StringReader}
import scala.xml.Node

object Crawl {
  implicit class Nodes(val nodes: Seq[Node]) {
    def \>[T](transformer: Seq[Node] => T): T = {
      transformer(nodes)
    }
  }

  val backend = HttpClientZioBackend()

  def extract(response: Task[Response[Either[String, String]]]) = {
    response.map(_.body match {
      case Left(value) => {
        value
      }
      case Right(value) => {
        value
      }
    })
  }

  def extractFile(saveLocation: String)(response: Task[Response[Either[String, File]]]) = {
    response.map(_.body match {
      case Left(_) => new File(saveLocation)
      case Right(f) => f
    })
  }

  def findBy(nodes: Seq[Node], key: String, value: String): Seq[Node] = {
    nodes.filter(x =>
      x.attributes.asAttrMap.get(key) match {
        case Some(y) => y == value
        case None => false
      })
  }

  def getHref(node: Node): String = {
    getAttr("href")(node)
  }

  def getAttr(name: String)(node: Node): String = {
    node.attributes.asAttrMap(name)
  }

  def findById(value: String)(nodes: Seq[Node]): Seq[Node] = {
    findBy(nodes, "id", value)
  }

  def findByClass(value: String)(nodes: Seq[Node]): Seq[Node] = {
    findBy(nodes, "class", value)
  }

  def fetch(location: Uri): ZIO[Any, Throwable, Seq[Node]] = {
    for {
      wire <- backend
      html <- basicRequest.get(location).send(wire) |> extract
      rootNode = HtmlParser.loadXML(new InputSource(new StringReader(html)))
    } yield rootNode
  }

  def fetchImage(location: Uri)(saveLocation: String): ZIO[Any, Throwable, File] = {
    for {
      wire <- backend
      file <- basicRequest.get(location).response(asFile(new File(saveLocation))).send(wire) |> extractFile(saveLocation)
    } yield file
  }
}
