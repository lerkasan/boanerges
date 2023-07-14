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
