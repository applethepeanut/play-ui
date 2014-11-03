package uk.gov.hmrc.play.views.helpers

import play.twirl.api.Html

trait FieldType

case class InputType(inputType: String, key: String, value: String,  divClass: Option[String] = None, labelClass: Option[String] = None, inputClass: Option[String] = None, dataAttribute: Option[String] = None, label:Option[String] = None) extends FieldType

object RadioButton {
  def apply(key: String, value: String, divClass: Option[String] = None, labelClass: Option[String] = None, inputClass: Option[String] = None, dataAttribute: Option[String] = None) = {
    InputType("radio", key, value, divClass, labelClass, inputClass, dataAttribute)
  }
}

object InputText {
  def apply(fieldLabel: String, divClass: Option[String] = None, labelClass: Option[String] = None, inputClass: Option[String] = None, label:Option[String] = None) = {
    InputType("text", "", fieldLabel, divClass, labelClass, inputClass, label)
  }
}

case class Select(values: Seq[(String, String)], emptyValueText: Option[String], label: String, labelClass: Option[String] = None, groupClass: Option[String] = None, additionalTitleText: Option[String] = None) extends FieldType

case class DateControl(yearRange: Range, extraClass: Option[String] = None) extends FieldType

case class FormField(field: play.api.data.Field, inputs: Seq[FieldType], explanationText: Option[Html] =  None)