package sp

import java.sql.{Connection, DriverManager, ResultSet}

object Application {
  def main(args: Array[String]): Unit = {
    initDb
    println("Welcome to Scala People!")
    showChoices
  }

  def initDb(): Unit = {
    createDb
    createTables
  }

  /**
  *  Create the scala_people database if its not already created.
  */
  def createDb(): Unit = {
    Class.forName("org.postgresql.Driver").newInstance
    val connection_string = "jdbc:postgresql://192.168.41.20/?user=postgres&password=password"
    val conn:Connection = DriverManager.getConnection(connection_string)
    try {
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
      val dbCheckResult = statement.executeQuery("select datname from pg_catalog.pg_database where datname='scala_people'")
      if (!dbCheckResult.next) {
        val createDbResult = statement.executeUpdate("CREATE DATABASE scala_people")
      }
    } catch {
      case e: Throwable => println("Error: " + e)
    } finally {
      conn.close
    }
  }

  def createTables(): Unit = {
    Class.forName("org.postgresql.Driver").newInstance
    val connection_string = "jdbc:postgresql://192.168.41.20/scala_people?user=postgres&password=password"
    val conn:Connection = DriverManager.getConnection(connection_string)
    try {
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
      val result = statement.executeQuery("CREATE TABLE IF NOT EXISTS people (id serial primary key, name VARCHAR(255), age int, occupation VARCHAR(255))")
    } catch {
      case e: Throwable => println("Could not connect")
    } finally {
      conn.close
    }
  }

  def showChoices(): Unit = {
    val userCommand = readLine("What would you like to do? [Add, Remove, List]: ")
    userCommand match {
      case "add" => promptAddPerson
      case "remove" => promptRemovePerson
      case "list" => promptListPeople
      case _ => {
        println("Invalid choice")
        showChoices
      }
    }
  }

  def promptAddPerson(): Unit = {
    println("You chose to add a new person!")
    val name = readLine("Name: ")
    val age = readLine("Age: ")
    val occupation = readLine("Occupation: ")
  }

  def promptRemovePerson(): Unit = {
    println("You chose to remove a person!")
    val name = readLine("Name of person to remove: ")
  }

  def promptListPeople(): Unit = {
    println("You chose to list all people")
  }
}
