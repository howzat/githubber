import com.itv.scalapact.plugin.ScalaPactPlugin._
import com.itv.scalapact.plugin._

providerStateMatcher := {
  case key: String if key == "An access token 12345" =>
    println("Injecting key 1234 into the database...")
    true
}


