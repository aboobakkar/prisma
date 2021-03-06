package com.prisma.deploy.connector

import com.prisma.shared.models.{Field, Model, Project}

import scala.concurrent.Future

trait DeployConnector {
  def projectPersistence: ProjectPersistence
  def migrationPersistence: MigrationPersistence
  def deployMutactionExecutor: DeployMutactionExecutor
  def clientDBQueries(project: Project): ClientDbQueries

  def initialize(): Future[Unit]
  def reset(): Future[Unit]
  def shutdown(): Future[Unit]

  // other methods
  def createProjectDatabase(id: String): Future[Unit]
  def deleteProjectDatabase(id: String): Future[Unit]
  def getAllDatabaseSizes(): Future[Vector[DatabaseSize]]

}

case class DatabaseSize(name: String, total: Double)

trait ClientDbQueries {
  def existsByModel(modelName: String): Future[Boolean]
  def existsByRelation(relationId: String): Future[Boolean]
  def existsNullByModelAndField(model: Model, field: Field): Future[Boolean]
  def enumValueIsInUse(models: Vector[Model], enumName: String, value: String): Future[Boolean]
}
