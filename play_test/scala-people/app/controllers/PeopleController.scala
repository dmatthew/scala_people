package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class PeopleController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  /* GET /people */
  def index = Action {
    Ok(views.html.people.index())
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
