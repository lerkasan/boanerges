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


# -?????????
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

# ---------------- EC2 parameters ----------------

variable "ec2_instance_type" {
  description = "AWS EC2 instance type"
  type        = string
  default     = "t3.micro"
}

variable "appserver_private_ssh_key_name" {
  description = "Name of the SSH keypair to use with appserver"
  type        = string
  default     = "appserver_ssh_key"
  sensitive   = true
}

variable "admin_public_ssh_keys" {
  description = "List of names of the SSM parameters with admin public ssh keys"
  type        = list(string)
  default     = [ "admin_public_ssh_key", "lerkasan_ssh_public_key_bastion" ]
}

# ---------------- Logging parameters ----------------

variable "log_group_names" {
  description = "A list of names of log groups to create"
  type        = list(string)
  default     = ["/var/log/spring-boot"]
}

# ---------------- OS parameters --------------------

variable "os" {
  description = "AMI OS"
  type        = string
  default     = "ubuntu"
}

variable "os_product" {
  description = "AMI OS product. Values: server or server-minimal"
  type        = string
  default     = "server"
}

variable "os_architecture" {
  description = "OS architecture"
  type        = string
  default     = "amd64"
}

variable "os_version" {
  description = "OS version"
  type        = string
  default     = "22.04"
}

variable "os_releases" {
  description = "OS release"
  type        = map(string)
  default     = {
    "22.04"   = "jammy"
  }
}

# ---------------- AMI filters ----------------------

variable "ami_virtualization" {
  description = "AMI virtualization type"
  type        = string
  default     = "hvm"
}

variable "ami_architectures" {
  description = "AMI architecture filters"
  type        = map(string)
  default     = {
    "amd64"   = "x86_64"
  }
}

variable "ami_owner_ids" {
  description = "AMI owner id"
  type        = map(string)
  default     = {
    "ubuntu"  = "099720109477" #Canonical
  }
}

# ---------------- Network parameters -------------------

variable "private_subnets_ids" {
  description = "A list of private subnets ids"
  type        = list(string)
}

variable "vpc_id" {
  description = "VPC id"
  type        = string
}

# ---------------- CodeDeploy parameters -------------------

#variable "codedeploy_deployment_group_arn" {
#  description = "ARN of a CodeDeploy deployment group"
#  type        = string
#}

# ---------------- SSM parameters -------------------

variable "ssm_param_db_host_arn" {
  description = "ARN of the SSM parameter for the database host"
  type        = string
}

variable "ssm_param_db_name_arn" {
  description = "ARN of the SSM parameter for the database name"
  type        = string
}

variable "ssm_param_db_username_arn" {
  description = "ARN of the SSM parameter for the database username"
  type        = string
}

variable "ssm_param_db_password_arn" {
  description = "ARN of the SSM parameter for the database password"
  type        = string
}

variable "kms_key_arn" {
  description = "ARN of the KMS key used to encrypt the SSM parameters"
  type        = string
}

# ---------------- Security Groups -------------------

variable "ec2_sg_id" {
  description = "Id of the security group for EC2 instance"
  type        = string
}

#variable "frontend_sg_id" {
#  description = "Id of the frontend security group for EC2 instance"  #  for bridge network mode of task definition
#  type        = string
#}
#
#variable "backend_sg_id" {
#  description = "Id of the backend security group for EC2 instance"  # for bridge network mode of task definition
#  type        = string
#}

variable "ec2_connect_endpoint_sg_id" {
  description = "Id of the security group for EC2 instance connect endpoint"
  type        = string
}

#variable "alb_target_group_arn" {
#  description = "A target group arn of a load balancer"
#  type        = string
#}

variable "ecs_cluster_name" {
  description = "ECS cluster name"
  type        = string
}