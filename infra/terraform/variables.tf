# ---------------- General parameters ----------------

variable "project_name" {
  description   = "Project name"
  type          = string
  default       = "boanerges"
}

variable "environment" {
  description   = "Environment: dev/stage/prod"
  type          = string
  default       = "prod"
}

variable "aws_region" {
  description   = "AWS region"
  type          = string
  default       = "us-east-1"
}

variable "az_letters" {
  description = "A list of availability zones in the region"
  type        = list(string)
  default     = ["a", "b"]
}

# -------------------- Database access parameters --------------------------

variable "database_name" {
  description = "Database name variable passed through a file secret.tfvars or an environment variable TF_database_name"
  type        = string
  sensitive   = true
}

variable "database_username" {
  description = "Database username variable passed through a file secret.tfvars or environment variable TF_database_username"
  type        = string
  sensitive   = true
}

variable "database_password" {
  description = "Database password variable passed through a file secret.tfvars or environment variable TF_database_password"
  type        = string
  sensitive   = true
}

variable "database_port" {
  description = "Database port"
  type        = number
  default     = 3306
}

variable "spring_server_port" {
  description = "spring port"
  type        = number
  default     = 8080
}

variable "secret_params" {
  description = "list of secret ssm parameters"
  type        = list(string)
}

locals {
  secrets = [
    for secret in var.secret_params :
    {
      name      = secret,
      valueFrom = data.aws_ssm_parameter.this[secret].arn
    }
  ]

  env_vars = [
    {
      name  = "DB_PORT",
      value = var.database_port
    },
    {
      name  = "SPRING_SERVER_PORT",
      value = var.spring_server_port
    }
  ]

}

variable "services" {
  description = "ecs services"
  type        = list(object(
    {
      service_name                = string
      task_name                   = string
      awslogs_group               = string
      cluster_id                  = string
      container_image             = string
      container_count             = number
      container_cpu               = number
      container_memory            = number
      container_port              = number
      ecs_task_role_arn           = string
      ecs_task_execution_role_arn = string
      env_vars                    = list(object(
        {
          name = string
          value = string
        }
      ))
      secrets                     = list(object(
        {
          name = string
          valueFrom = string
        }
      ))
      grace_period_in_seconds     = number
      private_subnets_ids         = list(string)
      security_group_ids          = list(string)
      target_group_arn            = string
      tmp_size_in_mb              = number
    }
  ))
  default = []
}



