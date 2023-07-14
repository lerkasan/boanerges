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

# ---------------- VPC parameters ----------------

variable "cidr_block" {
  description = "A CIDR block of the VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "public_subnets" {
  description = "A list of public subnets inside the VPC"
  type        = list(string)
  default     = [ "10.0.10.0/24", "10.0.20.0/24" ]
}

variable "private_subnets" {
  description = "A list of private subnets inside the VPC"
  type        = list(string)
  default     = [ "10.0.240.0/24", "10.0.250.0/24" ]
}

# ---------------- ALB parameters ----------------
variable "alb_dns_name" {
  description   = "ALB DNS name"
  type          = string
}

variable "alb_zone_id" {
  description   = "ALB zone id"
  type          = string
}

