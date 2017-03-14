package $package$

import de.choffmeister.microserviceutils.json.JsonHome

class ServiceTest extends ServiceTestBase {
  import $package$.HttpServer._

  describe("Service") {
    it("should server home resource") {
      Get("/api") ~> routes ~> check {
        responseAs[JsonHome] should be(jsonHome)
      }
    }

    it("should greet") {
      Post("/api" + jsonHome.resources(greetRel).expandHref(), Name("foobar")) ~> routes ~> check {
        responseAs[Greeting] should be(Greeting("Hello, foobar!"))
      }
    }
  }
}
