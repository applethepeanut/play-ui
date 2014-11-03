package uk.gov.hmrc.play.mappers

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import play.api.data.FormError

class DateTupleSpec extends WordSpec with Matchers {


  "dateTuple" should {

    import uk.gov.hmrc.play.mappers.DateFields._
    import uk.gov.hmrc.play.mappers.DateTuple._

    def assertError(dateFields: Map[String, String]) {
      val result = dateTuple.bind(dateFields)
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("", "error.invalid.date.format"))
    }

    "create a mapping for a valid date" in {
      val dateFields = Map(day -> "1", month -> "2", year -> "2014")
      val result = dateTuple.bind(dateFields)
      result.isRight shouldBe true
      result.right.get shouldBe Some(new LocalDate(2014, 2, 1))
    }

    "return None when all the fields are empty" in {
      val dateFields = Map(day -> "", month -> "", year -> "")
      val result = dateTuple.bind(dateFields)
      result.isRight shouldBe true
      result.right.get shouldBe None
    }

    "return a validation error for invalid date with characters" in {
      assertError(Map(day -> "1", month -> "2", year -> "bla"))
    }

    "return a validation error for invalid date with invalid month" in {
      assertError(Map(day -> "1", month -> "23", year -> "2014"))
    }

    "return a validation error for invalid date with only 2 digit year" in {
      assertError(Map(day -> "1", month -> "2", year -> "14"))
    }

    "return a validation error for invalid date with more than 4 digit year" in {
      assertError(Map(day -> "1", month -> "01", year -> "14444"))
    }

    "return a validation error for invalid date with more than 2 digit day" in {
      assertError(Map(day -> "122", month -> "01", year -> "2014"))
    }

    "return a validation error for invalid date with more than 2 digit month" in {
      assertError(Map(day -> "1", month -> "133", year -> "2014"))
    }

  }
}
