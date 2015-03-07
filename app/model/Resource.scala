package model

import java.net.URL


/**
 * Created by Antonio on 04/03/2015.
 */

case class Resource(id : String, unit : Option[String], sampleType : String, name : Option[String] = None){
  def urlId = new URL(id)
  override def equals(any: Any) : Boolean = {
    if(any.isInstanceOf[Resource])
      any.asInstanceOf[Resource].id == this.id
    else
      false
  }
}
object Resource {

  import play.api.libs.json._

  implicit val resourceWrites = new Writes[Resource] {
    def writes(res: Resource) = Json.obj(
      "id" -> res.id,
      "unit" -> res.unit,
      "sampleType" -> res.sampleType,
      "name" -> res.name
    )
  }
/*
  implicit val resourceReads: Reads[Resource] = (
    (JsPath \ "id").read[String] and
      (JsPath \ "unit").read[String] and
      (JsPath \ "sampleType").read[String] and
      (JsPath \ "name").read[Option[String]]
    )(Resource.apply _)
    */
}