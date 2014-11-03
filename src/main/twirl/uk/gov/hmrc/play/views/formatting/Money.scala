package uk.gov.hmrc.play.views.formatting

import play.twirl.api.Html
import uk.gov.hmrc.play.views.helpers.{MoneyPounds, RenderableMoneyMessage}

object Money {

  def pounds(value: BigDecimal, decimalPlaces: Int = 0) : Html = RenderableMoneyMessage(MoneyPounds(value, decimalPlaces)).render
}
