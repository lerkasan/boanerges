#locals {
#  task_name = "${var.project_name}-${var.environment}"

#  env_vars = [
#    {
#      name  = "DB_PORT",
#      value = var.database_port
#    },
#    {
#      name  = "SPRING_SERVER_PORT",
#      value = var.spring_server_port
#    }
#  ]

#  secret_params = [
#    "DB_HOST",
#    "DB_NAME",
#    "DB_USERNAME",
#    "DB_PASSWORD",
#    "AWS_STS_ROLE_ARN",
#    "DEEPGRAM_API_KEY",
#    "DEEPGRAM_PROJECT_ID",
#    "OPENAI_API_KEY",
#    "SMTP_USERNAME",
#    "SMTP_PASSWORD"
#  ]

#  secrets = [ for secret in var.secret_params:
#    {
#      name = secret,
#      valueFrom = data.aws_ssm_parameter.this[secret].arn
#    }
#  ]

#}

#locals {
#  secrets = [ for secret in var.secret_params:
#    {
#      name = secret,
#      valueFrom = data.aws_ssm_parameter.this[secret].arn
#    }
#  ]
#}

data "aws_ssm_parameter" "backend_secret" {
#  for_each = toset(var.secret_params)
  for_each = toset(var.services[0].secrets)

  name = each.value

  depends_on = [
    module.rds.ssm_param_db_host_arn,
    module.rds.ssm_param_db_name_arn,
    module.rds.ssm_param_db_username_arn,
    module.rds.ssm_param_db_password_arn
  ]
}

data "aws_iam_policy_document" "read_access_to_ssm_parameters" {

  statement {
    sid       = "SSMGetParameter"
    effect    = "Allow"
    actions   = [
      "ssm:GetParameter",
      "ssm:GetParameters"
    ]
    resources = [ for param in var.services[0].secrets : data.aws_ssm_parameter.backend_secret[param].arn ]
  }
}

////

resource "aws_iam_role" "backend_iam_role" {
  name        = join("", [title(var.project_name), "BackendECSTaskRole"])
  description = "The backend task role for ECS"
  assume_role_policy = data.aws_iam_policy_document.assume_role_ecs.json
}

data "aws_iam_policy_document" "assume_role_ecs" {
  statement {
    sid           = "ECSAssumeRole"
    effect        = "Allow"
    actions       = [ "sts:AssumeRole" ]
    principals {
      type        = "Service"
      identifiers = [ "ecs-tasks.amazonaws.com" ]
    }
  }
}

resource "aws_iam_role_policy_attachment" "DecryptAccessToKmsKey" {
  role       = aws_iam_role.backend_iam_role.name
  policy_arn = aws_iam_policy.decrypt_access_to_kms_key.arn
}

resource "aws_iam_role_policy_attachment" "GetSSMParameters" {
  role       = aws_iam_role.backend_iam_role.name
  policy_arn = aws_iam_policy.read_access_to_ssm_parameters.arn
}

resource "aws_iam_policy" "read_access_to_ssm_parameters" {
  name        = "read-access-to-parameters"
  description = "Allow to get deployment information to retrieve a commitId hash"
  policy      = data.aws_iam_policy_document.read_access_to_ssm_parameters.json
}

resource "aws_iam_policy" "decrypt_access_to_kms_key" {
  name        = "decrypt-access-to-kms-key"
  description = "Allow to get deployment information to retrieve a commitId hash"
  policy      = data.aws_iam_policy_document.decrypt_access_to_kms_key.json
}

data "aws_iam_policy_document" "decrypt_access_to_kms_key" {

  #  statement {
  #    sid       = "SSMGetParameter"
  #    effect    = "Allow"
  #    actions   = [ "ssm:GetParameter" ]
  #    resources = [
  #      var.ssm_param_db_host_arn,
  #      var.ssm_param_db_name_arn,
  #      var.ssm_param_db_username_arn,
  #      var.ssm_param_db_password_arn
  #    ]
  #  }

  statement {
    sid       = "KMSDecrypt"
    effect    = "Allow"
    actions   = [
      "kms:Decrypt",
      "kms:DescribeKey"
    ]
    resources = [ module.rds.kms_key_arn ]
  }

  #  statement {
  #    sid       = "CloudWatchLogs"
  #    effect    = "Allow"
  #    actions   = [
  #      "logs:CreateLogStream",
  #      "logs:PutLogEvents",
  #      "logs:CreateLogGroup"
  #    ]
  #    resources = [ "*" ]
  #  }
}

////

resource "aws_iam_role" "ecs_task_execution_role" {
  name        = join("", [title(var.project_name), "ECSTaskExecutionRole"])
  description = "The task execution role for ECS"
  assume_role_policy = data.aws_iam_policy_document.assume_role_ecs.json
}

resource "aws_iam_role_policy_attachment" "AmazonECSTaskExecutionRolePolicy_attachment" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "DecryptAccessToKmsKeyExecutionRole" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = aws_iam_policy.decrypt_access_to_kms_key.arn
}

resource "aws_iam_role_policy_attachment" "GetSSMParametersExecutionRole" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = aws_iam_policy.read_access_to_ssm_parameters.arn
}

#resource "aws_iam_policy" "create_cloudwatch_logs" {
#  name        = "create_cloudwatch_logs"
#  description = "Allow ECS agent to create cloudwatch logs"
#  policy      = data.aws_iam_policy_document.create_cloudwatch_logs.json
#}

#data "aws_iam_policy_document" "create_cloudwatch_logs" {
#
#    statement {
#      sid       = "CloudWatchLogs"
#      effect    = "Allow"
#      actions   = [
#        "logs:CreateLogStream",
#        "logs:PutLogEvents",
#        "logs:CreateLogGroup"
#      ]
#      resources = [ "*" ]
#    }
#}

#resource "aws_ecr_repository" "backend" {
#  name = "boanerges-backend"
#}
#
#resource "aws_ecr_repository" "frontend" {
#  name = "boanerges-frontend"
#}

resource "aws_cloudwatch_log_group" "ecs_logs" {
  name          = join("_", [var.project_name, "ecs_logs"])

  tags          = {
    Name        = join("_", [var.project_name, "nginx_log_group"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_ecs_cluster" "boanerges" {
  name = "boanerges"
}

module "ecs" {
#  for_each = toset(var.services.service_name)
  for_each = { for service in var.services : service.service_name => service }

  source = "./modules/ecs"

  auto_scaling_group_arn      = module.autoscaling_group.arn
  awslogs_group               = each.value.awslogs_group
  cluster_id                  = aws_ecs_cluster.boanerges.id
  cluster_name                = aws_ecs_cluster.boanerges.name
  container_count             = each.value.container_count
  container_cpu               = each.value.container_cpu
  container_image             = each.value.container_image
  container_memory            = each.value.container_memory
  container_port              = each.value.container_port
  ecs_task_role_arn           = aws_iam_role.ecs_task_execution_role.arn
  ecs_task_execution_role_arn = aws_iam_role.ecs_task_execution_role.arn
#  secrets                     = local.secrets
  grace_period_in_seconds     = each.value.grace_period_in_seconds
  private_subnets_ids         = module.network.private_subnets_ids
  security_group_ids          = [ each.value.service_name == "${var.project_name}-frontend" ? module.security.frontend_security_group_id : module.security.backend_security_group_id ]
  service_name                = each.value.service_name
  target_group_arn            = each.value.service_name == "${var.project_name}-frontend" ? module.loadbalancer.frontend_target_group_arn : module.loadbalancer.backend_target_group_arn
  task_name                   = each.value.task_name
#  tmp_size_in_mb              = each.value.tmp_size_in_mb
#  kms_key_arn                 = module.rds.kms_key_arn

  capabilities                = each.value.capabilities

  tmpfs                       = each.value.tmpfs

  env_vars                    = each.value.env_vars

  volumes                     = each.value.volumes

  secrets                     = [ for secret in each.value.secrets:
    {
#      name = secret,
      name = replace(secret, format("%s%s", upper(var.project_name),"_"), ""),
      valueFrom = data.aws_ssm_parameter.backend_secret[secret].arn
    }
  ]

  depends_on = [ module.rds ]
}
#
module "autoscaling_group" {
  source = "./modules/autoscaling_group"

  vpc_id                          = module.network.vpc_id
  private_subnets_ids             = module.network.private_subnets_ids
  ec2_sg_id                       = module.security.ec2_sg_id
#  frontend_sg_id                  = module.security.frontend_security_group_id  # for bridge network mode of task definition
#  backend_sg_id                   = module.security.backend_security_group_id   # for bridge network mode of task definition
  kms_key_arn                     = module.rds.kms_key_arn
  ssm_param_db_host_arn           = module.rds.ssm_param_db_host_arn
  ssm_param_db_name_arn           = module.rds.ssm_param_db_name_arn
  ssm_param_db_password_arn       = module.rds.ssm_param_db_password_arn
  ssm_param_db_username_arn       = module.rds.ssm_param_db_username_arn
#  codedeploy_deployment_group_arn = module.codedeploy.deployment_group_arn
  ec2_connect_endpoint_sg_id      = module.security.ec2_connect_endpoint_sg_id
#  alb_target_group_arn            = module.loadbalancer.target_group_arn

  ecs_cluster_name                = aws_ecs_cluster.boanerges.name

  project_name = var.project_name
  environment  = var.environment
  aws_region   = var.aws_region
  az_letters   = var.az_letters

  ec2_instance_type   = "t3.small"  # it's better (than t3.micro) for awsvpc network mode of ecs task definition, as t3.small can have a maximum of 3 ENI (elastic network interfaces) - 1 for ec2 instance and 1 per a ecs task. So only 2 tasks can run on t3.medium not causing RESOURCE:ENI error. In order to have more ENI awsvpctrunking is available for some ec2 instance types. t3 family is not compatible with awsvpctrunking https://docs.aws.amazon.com/AmazonECS/latest/developerguide/container-instance-eni.html
#  ec2_instance_type   = "t3.micro"
  os                  = "ubuntu"
  os_architecture     = "amd64"
  os_version          = "22.04"
  os_releases         = { "22.04" = "jammy" }
  ami_virtualization  = "hvm"
  ami_architectures   = { "amd64" = "x86_64" }
  ami_owner_ids       = {"ubuntu" = "099720109477" }   #Canonical

  appserver_private_ssh_key_name = "appserver_ssh_key"
  admin_public_ssh_keys = [ "admin_public_ssh_key"]

# Dependency is used to ensure that EC2 instance will have Internet access during userdata execution to be able to install packages
  depends_on = [module.network.internet_gateway_id, module.network.nat_gateway_ids]
}

module "loadbalancer" {
  source = "./modules/loadbalancer"

  vpc_id              = module.network.vpc_id
  public_subnets_ids  = module.network.public_subnets_ids
#  ec2_instances_ids   = module.ec2_template.ec2_instances_ids
  alb_sg_id           = module.security.alb_sg_id

  project_name = var.project_name
  environment  = var.environment
  aws_region   = var.aws_region
  az_letters   = var.az_letters

  depends_on = []
}

module "rds" {
  source = "./modules/rds"

  vpc_id              = module.network.vpc_id
  private_subnets_ids = module.network.private_subnets_ids
  rds_sg_id           = module.security.rds_sg_id
  iam_role_arn        = aws_iam_role.ecs_task_execution_role.arn
#  iam_role_arn        = module.ecs.task_role_arn
#  iam_role_arn        = module.ecs[join("-", [var.project_name, "-backend"])].task_role_arn

  project_name = var.project_name
  environment  = var.environment
  aws_region   = var.aws_region
  az_letters   = var.az_letters

  rds_name = "boanerges-db"
  database_engine = "mysql"
  database_engine_version = "8.0.32"
  database_instance_class = "db.t3.micro"

  database_name       = var.database_name
  database_password   = var.database_password
  database_username   = var.database_username
}

#module "codedeploy" {
#  source = "./modules/codedeploy"
#
#  target_group_name      = module.loadbalancer.target_group_name
#  autoscaling_group_name = module.autoscaling_group.name
#
#  project_name = var.project_name
#  environment  = var.environment
#}

module "network" {
  source = "./modules/network"

  alb_dns_name  = module.loadbalancer.dns_name
  alb_zone_id   = module.loadbalancer.zone_id

  project_name = var.project_name
  environment  = var.environment
  aws_region   = var.aws_region
  az_letters   = var.az_letters

  cidr_block      = "10.0.0.0/16"
  public_subnets  = [ "10.0.10.0/24", "10.0.20.0/24" ]
  private_subnets = [ "10.0.240.0/24", "10.0.250.0/24" ]
}

module "security" {
  source = "./modules/security"

  vpc_id = module.network.vpc_id

  project_name = var.project_name
  environment  = var.environment
}