
package io.kotlintest.matchers

infix fun Float.plusOrMinus(tolerance: Float): ToleranceMatcher = ToleranceMatcher(this, tolerance)

fun exactly(d: Float): Matcher<Float> = object : Matcher<Float> {
  override fun test(value: Float) = Result(value == d, "$value is not equal to expected value $d")
}

class ToleranceMatcher(val expected: Float, val tolerance: Float) : Matcher<Float> {

  override fun test(value: Float): Result {
    return if (Float.NaN.equals(expected) && Float.NaN.equals(value)) {
      println("[WARN] By design, Float.Nan != Float.Nan; see https://stackoverflow.com/questions/8819738/why-does-double-nan-double-nan-return-false/8819776#8819776")
      Result(false, "By design, Float.Nan != Float.Nan; see https://stackoverflow.com/questions/8819738/why-does-double-nan-double-nan-return-false/8819776#8819776")
    } else {
      if (tolerance == 0.0)
        println("[WARN] When comparing Float consider using tolerance, eg: a shouldBe b plusOrMinus c")
      val diff = Math.abs(value - expected)
      Result(diff <= tolerance, "$value should be equal to $expected")
    }
  }

  infix fun plusOrMinus(tolerance: Float): ToleranceMatcher = ToleranceMatcher(expected, tolerance)
}
