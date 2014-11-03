package uk.gov.hmrc.play.views.helpers

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.play.views.html.helpers.{dateFieldsInline, dateFieldsFreeYearInline}
import play.twirl.api.Html
import play.api.data.Form
import play.api.data.Forms.{of => fieldOf, mapping}
import org.jsoup.Jsoup
import play.api.test.Helpers._
import play.api.data.format.Formats._

class DateFieldsSpec extends WordSpec with Matchers {

  val months = Seq("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")

  case class DummyFormData(day: Int, month: Int, year: Int)
  def dummyForm = Form(
    mapping(
      "dummyField.day" -> fieldOf[Int],
      "dummyField.month" -> fieldOf[Int],
      "dummyField.year" -> fieldOf[Int]
  )(DummyFormData.apply)(DummyFormData.unapply))

  "The Date Fields with a freeform year input box" should {
    "Display months using long nouns" in {
      val doc = Jsoup.parse(contentAsString(dateFieldsFreeYearInline(dummyForm, "dummyField", Html("label"))))
      months.zipWithIndex.foreach { case (month: String, i: Int) =>
        doc.getElementById(s"dummyField.month-${i+1}").text shouldBe month
      }
    }
  }

  "The Date Fields with a limited year input box" should {
    "Display months using long nouns" in {
      val doc = Jsoup.parse(contentAsString(dateFieldsInline(dummyForm, "dummyField", Html("label"), 1 to 2, None)))
      months.zipWithIndex.foreach { case (month: String, i: Int) =>
        doc.getElementById(s"dummyField.month-${i+1}").text shouldBe month
      }
    }
  }

}
