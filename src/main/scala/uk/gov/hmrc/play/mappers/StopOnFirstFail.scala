package uk.gov.hmrc.play.mappers

import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}

object StopOnFirstFail {

  def apply[T](constraints: Constraint[T]*) = Constraint {
    field: T =>
      constraints.toList dropWhile (_(field) == Valid) match {
        case Nil => Valid
        case constraint :: _ => constraint(field)
      }
  }

  def constraint[T](message: String, validator: (T) => Boolean) = {
    Constraint((data: T) => if (validator(data)) Valid else Invalid(Seq(ValidationError(message))))
  }
}