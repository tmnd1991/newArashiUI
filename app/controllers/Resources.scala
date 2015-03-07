package controllers

import model.ResourceTreeNode
import org.w3.banana._, syntax._, diesel._
import org.w3.banana.jena.JenaModule
import org.w3.banana.jena.io.JenaRDFWriter
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import readers.SparqlJenaReader
import java.net.URL

import scala.collection._

/**
 * Created by Antonio on 03/03/2015.
 */
object Resources extends Controller{

  lazy val reader = new SparqlJenaReader(new URL("http://137.204.57.150:3030/ds/query"), "<http://stormsmacs/tests/Resources>")

  def childrenOf(parentId : String) = Action{
    var nodeSet : Set[ResourceTreeNode]= Set()
    val converted = reader.result.map(x => ResourceTreeNode.convert(x,true))
    for (r <- converted)
      nodeSet ++= ResourceTreeNode.convertFamily(r,true)
    val filtered = nodeSet.filter(_.parentId == parentId.toInt)
    Ok(Json.toJson(filtered))
  }

  def root = childrenOf("#".hashCode.toString)

}
/** ROOTS **/
/*
[
{ "id" : "demo_root_1", "text" : "Root 1", "children" : true, "type" : "root" },
{ "id" : "demo_root_2", "text" : "Root 2", "type" : "root" }
]
*/