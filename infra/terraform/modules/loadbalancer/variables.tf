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

# ---------------- Network parameters -------------------

variable "vpc_id" {
  description = "VPC id"
  type        = string
}

variable "public_subnets_ids" {
  description = "A list of public subnets ids"
  type        = list(string)
}

# ----------------- EC2 parameters ---------------------

variable "ec2_instances_ids" {
  description = "A list of ec2 instances ids"
  type        = list(string)
}

# ----------------- Security parameters ---------------------

variable "alb_sg_id" {
  description = "A security group id of a load balancer"
  type        = string
}