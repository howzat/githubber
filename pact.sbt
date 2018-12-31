import com.itv.scalapact.plugin.ScalaPactPlugin._

providerStateMatcher := {
  case key: String if key == "An access token 12345" =>
    println("Injecting key 1234 into the database...")
    true
}
