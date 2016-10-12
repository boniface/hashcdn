package services

import domain.Key
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by hashcode on 2016/10/12.
  */
class DocumentServiceTest extends FunSuite {

  test("testCreateKey") {
    val key = Key("mmkey","12345678")
    val results = Await.result(DocumentService.apply.createKey(key),2.minutes)
    println(" The Results ", results)

  }

  test("testUpdateKey") {
    val key = Key("mmkey","kk12345678")
    val results = Await.result(DocumentService.apply.updateKey(key),2.minutes)
    println(" The Results ", results)

  }

  test("testGetKey") {
    val results = Await.result(DocumentService.apply.getKey("mmkey"),2.minutes)
    println(" The Results ", results)


  }

  test("testDeleteKey") {

  }

}
