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
    println(" The Result", results.head)

  }

  test("testDeleteKey") {

  }

}
