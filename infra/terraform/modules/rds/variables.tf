# ---------------- General parameters ----------------

variable "project_name" {
  description   = "Project name"
  type          = string
  default       = "boanerges"
}

variable "environment" {
  description   = "Environment: dev/stage/prod"
  type          = string
  default       = "stage"
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

# -------------- Database access parameters ---------------

variable "rds_name" {
  description = "The name of the RDS instance"
  type        = string
  default     = "boanerges-db"
}

variable "database_engine" {
  description = "database engine"
  type        = string
  default     = "mysql"
}

variable "database_engine_version" {
  description = "database engine version"
  type        = string
  default     = "8.0.32"
}

variable "database_instance_class" {
  description = "database instance class"
  type        = string
  default     = "db.t3.micro"
}

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

# -------------- IAM role to access SSM parameters ---------------

variable "iam_role_arn" {
  description = "IAM role to access SSM parameters"
  type        = string
}

# -------------- Network parameters ---------------

variable "vpc_id" {
  description = "VPC id"
  type        = string
}

variable "private_subnets_ids" {
  description = "A list of private subnets ids"
  type        = list(string)
}

# -------------- Security parameters ---------------

variable "rds_sg_id" {
  description = "A security group id of a RDS database"
  type        = string
}
