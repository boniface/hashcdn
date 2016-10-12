package repository

import domain.Key
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2016/10/12.
  */
class DocumentRepositoryTest extends FunSuite {

  test("testCreateKey") {
//    val key = Key("apiKey","keyapi_key")
//    val results = Await.result(DocumentRepository.apply.createKey(key),2.minutes)
//    println(" The Result", results.head)

  }

  test("testUpdateKey") {
    val key = Key("apiKey","keyapi_key")
    val results = Await.result(DocumentRepository.apply.updateKey(key),2.minutes)
    println(" The Result", results.head)

  }

  test("testGetKey") {
    val key = Key("apiKey","12345")
    val results = Await.result(DocumentRepository.apply.getKey("apiKey"),2.minutes)
    println(" The Result SCALAR KEY  ", results.head.get("_id"))
    println(" The Result SCALAR VALUE ", results.head.get("value"))

    val keyv = results.head.get("_id") match{
      case Some(k)=> k.asString().getValue
      case None=>""
    }

    println(" The Value is!!!!!! ", keyv)


  }

  test("testDeleteKey") {

  }

}
