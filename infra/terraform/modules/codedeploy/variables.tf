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

# --------------------------------

variable "target_group_name" {
  description   = "A target group name of a load balancer"
  type          = string
}
