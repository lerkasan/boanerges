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


variable "task_name" {
  description = "Task name"
  type        = string
}

variable "service_name" {
  description = "Service name"
  type        = string
}

variable "cluster_id" {
  description = "Cluster id"
  type        = string
}

variable "target_group_arn" {
  description = "ARN of a target group of a loadbalancer"
  type        = string
}

variable "ecs_task_role_arn" {
  description = "ARN of a ECS task role"
  type        = string
}

variable "ecs_task_execution_role_arn" {
  description = "ARN of a ECS task execution role"
  type        = string
}

variable "auto_scaling_group_arn" {
  description = "ARN of a auto scaling group of a capacity provider"
  type        = string
}

variable "cluster_name" {
  description = "Cluster name"
  type        = string
}

variable "awslogs_group" {
  description = "awslogs group for ECS service"
  type        = string
}

variable "container_image" {
  description = "Container image"
  type        = string
}

variable "container_port" {
  description = "Container port"
  type        = number
}

variable "container_count" {
  description = "Container desired count for service"
  type        = number
}

variable "container_cpu" {
  description = "Container CPU"
  type        = number
}

variable "container_memory" {
  description = "Container memory"
  type        = number
}

#variable "tmp_size_in_mb" {
#  description = "Container /tmp size in MB"
#  type        = number
#}

variable "grace_period_in_seconds" {
  description = "Container grace period in seconds"
  type        = number
}

variable "private_subnets_ids" {
  description = "List of ids of private subnets"
  type        = list(string)
}

variable "capabilities" {
  description = "List of linux kernel capabilities to add to service"
  type        = list(string)
}

variable "security_group_ids" {
  description = "List of ids of security groups"
  type        = list(string)
}

variable "tmpfs" {
  description = "Temporary filesystems"
  type        = list(object({
    containerPath = string
    size          = number
  }))
}

variable "volumes" {
  description = "Volumes"
  type        = list(object({
    name          = string
    hostPath      = string
    containerPath = string
  }))
}

variable "env_vars" {
  description = "Env variables"
  type        = list(object({
    name      = string
    value     = any
  }))
}

variable "secrets" {
  description = "Secrets"
  type        = list(object({
    name      = string
    valueFrom = string
  }))
  sensitive   = true
}

#variable "kms_key_arn" {
#  description = "ARN of the KMS key used to encrypt the SSM parameters"
#  type        = string
#}