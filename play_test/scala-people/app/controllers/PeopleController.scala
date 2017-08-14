package controllers

import javax.inject._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import play.api._
import play.api.mvc._
import play.api.libs.json._
import reactivemongo.play.json._, collection._
import reactivemongo.api.Cursor
import reactivemongo.api.ReadPreference
import play.modules.reactivemongo._

import models.Person
import models.JsonFormats._

@Singleton
class PeopleController @Inject()(implicit ec: ExecutionContext, cc: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents {

  /**
   * Fixes Error:
   * class PeopleController inherits conflicting members:
   * method parse in trait BaseControllerHelpers of type => play.api.mvc.PlayBodyParsers  and
   * lazy value parse in trait BodyParsers of type play.api.mvc.PlayBodyParsers
   * (Note: this can be resolved by declaring an override in class PeopleController.
   */
  override lazy val parse: PlayBodyParsers = cc.parsers

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("people"))

  /* GET /people */
  def index = Action.async {
    // let's do our query
    val cursor: Future[Cursor[Person]] = collection.map {
      _.find(Json.obj()).
        // perform the query and get a cursor of JsObject
        cursor[Person]
    }

    // gather all the JsObjects in a list
    val futurePeopleList: Future[List[Person]] =
      cursor.flatMap(_.collect[List]())

    // transform the list into a JsArray
    // val futurePersonsJsonArray: Future[JsArray] =
    //   futurePersonsList.map { people => Json.arr(people) }

    // everything's ok! Let's reply with the array
    futurePeopleList.map { people =>
      Ok(views.html.people.index(people))
    }
  }

  /* GET /people/:id */
  def show(id: Long) = TODO

  /* GET /people/new */
  def add = Action {
    Ok(views.html.people.add())
  }

  /* GET /people/:id/edit */
  def edit(id: Long) = TODO

  /* POST /people */
  def create = TODO

  /* PATCH/PUT /people/:id */
  def update(id: Long) = TODO

  /* DELETE /people/:id */
  def destroy(id: Long) = TODO
}
