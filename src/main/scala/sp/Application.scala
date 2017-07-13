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
      if (!dbCheckResult.next) statement.executeUpdate("CREATE DATABASE scala_people")
    } catch {
      case e: Throwable => println("Error: " + e)
    } finally {
      conn.close
    }
  }

  /**
   * Create the people table if its not already created.
   */
  def createTables(): Unit = {
    Class.forName("org.postgresql.Driver").newInstance
    val connection_string = "jdbc:postgresql://192.168.41.20/scala_people?user=postgres&password=password"
    val conn:Connection = DriverManager.getConnection(connection_string)
    try {
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS people (id serial primary key, name VARCHAR(255), age int, occupation VARCHAR(255))")
    } catch {
      case e: Throwable => println("Could not connect")
    } finally {
      conn.close
    }
  }

  def showChoices(): Unit = {
    readLine("What would you like to do? [Add, Remove, List, Quit]: ") match {
      case "Add" | "add" | "A" | "a" => promptAddPerson; showChoices
      case "Remove" | "remove" | "R" | "r" => promptRemovePerson; showChoices
      case "List" | "list" | "L" | "l" => promptListPeople; showChoices
      case "Quit" | "quit" | "Q" | "q" => println("Goodbye!")
      case _ => println("Invalid choice"); showChoices
    }
  }

  def promptAddPerson(): Unit = {
    println("You chose to add a new person!")
    val name = readLine("Name: ")
    val age = readLine("Age: ")
    val occupation = readLine("Occupation: ")

    Class.forName("org.postgresql.Driver").newInstance
    val connection_string = "jdbc:postgresql://192.168.41.20/scala_people?user=postgres&password=password"
    val conn:Connection = DriverManager.getConnection(connection_string)
    val insertQuery: String = "INSERT INTO people (" +
      " name," +
      " age," +
      " occupation ) VALUES (" +
      " ?, ?, ?)";
    try {
      val insertStatement = conn.prepareStatement(insertQuery)
      insertStatement.setString(1, name)
      insertStatement.setInt(2, age.toInt)
      insertStatement.setString(3, occupation)
      insertStatement.executeUpdate
    } catch {
      case e: Throwable => println("Error: " + e)
    } finally {
      conn.close
    }
  }

  def promptRemovePerson(): Unit = {
    println("You chose to remove a person!")
    val name = readLine("Name of person to remove: ")

    Class.forName("org.postgresql.Driver").newInstance
    val connection_string = "jdbc:postgresql://192.168.41.20/scala_people?user=postgres&password=password"
    val conn:Connection = DriverManager.getConnection(connection_string)
    val deleteQuery: String = "DELETE FROM people WHERE name = ?";
    try {
      val deleteStatement = conn.prepareStatement(deleteQuery)
      deleteStatement.setString(1, name)
      deleteStatement.executeUpdate
    } catch {
      case e: Throwable => println("Error: " + e)
    } finally {
      conn.close
    }
  }

  def promptListPeople(): Unit = {
    println("You chose to list all people")

    Class.forName("org.postgresql.Driver").newInstance
    val connection_string = "jdbc:postgresql://192.168.41.20/scala_people?user=postgres&password=password"
    val conn:Connection = DriverManager.getConnection(connection_string)
    try {
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
      val result = statement.executeQuery("SELECT * FROM people")
      if (!result.isBeforeFirst) println("There are no people to list")
      else {
        while (result.next) {
          println(result.getString("name").padTo(20, ' ') + " | " + result.getString("age").padTo(3, ' ') + " | " + result.getString("occupation"))
        }
      }
    } catch {
      case e: Throwable => println("Error: " + e)
    } finally {
      conn.close
    }
  }
}
