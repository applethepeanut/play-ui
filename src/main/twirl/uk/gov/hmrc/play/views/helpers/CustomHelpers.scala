package uk.gov.hmrc.play.views.helpers

import views.html.helper.FieldConstructor
import uk.gov.hmrc.play.views.html.helpers.simpleFieldConstructor

object CustomHelpers {

  implicit val myFields = FieldConstructor(simpleFieldConstructor.f)

}
