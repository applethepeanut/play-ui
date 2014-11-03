package uk.gov.hmrc.play.views.helpers

import play.twirl.api.{Html, HtmlFormat}
import play.api.i18n.Messages

trait Target {
  protected val targetName: String
  def toAttr = Link.attr("target", targetName)
}
case object SameWindow extends Target {
  override val targetName = "_self"
}
case object NewWindow extends Target {
  override val targetName = "_blank"
}

trait PossibleSso {
  protected val value: String
  def toAttr = Link.attr("data-sso", value)
}
case object NoSso extends PossibleSso {
  override val value = "false"
}
case object ClientSso extends PossibleSso {
  override val value = "client"
}
case object ServerSso extends PossibleSso {
  override val value = "server"
}

case class Link(url: String,
                value: Option[String],
                id: Option[String] = None,
                target: Target = SameWindow,
                sso: PossibleSso = NoSso,
                cssClasses: Option[String] = None) {

  import Link._

  private def hrefAttr = attr("href", url)
  private def idAttr = id.map(attr("id", _)).getOrElse("")
  private def text = value.map(v => escape(Messages(v))).getOrElse("")
  private def cssAttr = cssClasses.map(attr("class", _)).getOrElse("")

  def toHtml = Html(s"<a$idAttr$hrefAttr${target.toAttr}${sso.toAttr}$cssAttr>$text</a>")
}
@deprecated("Use version of this class from https://github.tools.tax.service.gov.uk/HMRC/url-builder")
object Link {

  private def escape(str: String) = HtmlFormat.escape(str).toString()

  def attr(name: String, value: String) = s""" $name="${escape(value)}""""

  case class PreconfiguredLink(sso: PossibleSso, target: Target) {
    def apply(url: String, value: Option[String], id: Option[String] = None, cssClasses: Option[String] = None) =
      Link(url, value, id, target, sso, cssClasses)
  }

  def toInternalPage = PreconfiguredLink(NoSso, SameWindow)

  def toExternalPage = PreconfiguredLink(NoSso, NewWindow)

  def toPortalPage = PreconfiguredLink(ClientSso, SameWindow)

  def toInternalPageWithSso = PreconfiguredLink(ServerSso, SameWindow)

}