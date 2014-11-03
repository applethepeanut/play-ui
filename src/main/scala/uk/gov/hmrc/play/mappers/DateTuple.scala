package uk.gov.hmrc.play.mappers

import org.joda.time.LocalDate
import play.api.data.Forms._
import play.api.data.Mapping
import java.text.{DateFormatSymbols => JDateFormatSymbols}

object DateTuple extends DateTuple

trait DateTuple {

  import DateFields._

  val dateTuple: Mapping[Option[LocalDate]] = dateTuple(validate = true)

  def mandatoryDateTuple(error: String): Mapping[LocalDate] = dateTuple.verifying(error, data => data.isDefined).transform(o => o.get, v => if (v == null) None else Some(v))

  def dateTuple(validate: Boolean = true) = tuple(
    year -> optional(text),
    month -> optional(text),
    day -> optional(text)
  ).verifying("error.invalid.date.format", data => {

    (data._1, data._2, data._3) match {
      case (None, None, None) => true
      case (yearOption, monthOption, dayOption) =>
        try {
          val y = yearOption.getOrElse(throw new Exception("Year missing")).trim
          if (y.length != 4) {
            throw new Exception("Year must be 4 digits")
          }
          new LocalDate(y.toInt, monthOption.getOrElse(throw new Exception("Month missing")).toInt, dayOption.getOrElse(throw new Exception("Day missing")).toInt)
          true
        } catch {
          case _: Throwable => if (validate) {
            false
          } else {
            true
          }
        }
    }

  }).transform(
  {
    case (Some(y), Some(m), Some(d)) =>
      try {
        Some(new LocalDate(y.trim.toInt, m.toInt, d.toInt))
      } catch {
        case e: Exception =>
          if (validate) {
            throw e
          } else {
            None
          }
      }
    case (a, b, c) => None
  },
  (date: Option[LocalDate]) => date match {
    case Some(d) => (Some(d.getYear.toString), Some(d.getMonthOfYear.toString), Some(d.getDayOfMonth.toString))
    case _ => (None, None, None)
  }
  )

}

object DateFields {
  val day = "day"
  val month = "month"
  val year = "year"
}


object DateFormatSymbols {

  val months = new JDateFormatSymbols().getMonths

  val monthsWithIndexes = months.zipWithIndex.take(12).map{case (s, i) => ((i+1).toString, s)}.toSeq
}



