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

# ---------------- Network parameters -------------------

variable "vpc_id" {
  description = "VPC id"
  type        = string
}
