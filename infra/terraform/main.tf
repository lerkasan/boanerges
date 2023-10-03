module "ec2" {
  source = "./modules/ec2_instance"

  vpc_id                          = module.network.vpc_id
  private_subnets_ids             = module.network.private_subnets_ids
  ec2_sg_id                       = module.security.ec2_sg_id
  kms_key_arn                     = module.rds.kms_key_arn
  ssm_param_db_host_arn           = module.rds.ssm_param_db_host_arn
  ssm_param_db_name_arn           = module.rds.ssm_param_db_name_arn
  ssm_param_db_password_arn       = module.rds.ssm_param_db_password_arn
  ssm_param_db_username_arn       = module.rds.ssm_param_db_username_arn
  codedeploy_deployment_group_arn = module.codedeploy.deployment_group_arn
  ec2_connect_endpoint_sg_id      = module.security.ec2_connect_endpoint_sg_id

  project_name = var.project_name
  environment  = var.environment
  aws_region   = var.aws_region
  az_letters   = var.az_letters

  ec2_instance_type   = "t3.micro"
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
  ec2_instances_ids   = module.ec2.ec2_instances_ids
  alb_sg_id           = module.security.alb_sg_id

  project_name = var.project_name
  environment  = var.environment
  aws_region   = var.aws_region
  az_letters   = var.az_letters
}

module "rds" {
  source = "./modules/rds"

  vpc_id              = module.network.vpc_id
  private_subnets_ids = module.network.private_subnets_ids
  rds_sg_id           = module.security.rds_sg_id
  iam_role_arn        = module.ec2.iam_role_arn

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

module "codedeploy" {
  source = "./modules/codedeploy"

  target_group_name   = module.loadbalancer.target_group_name

  project_name = var.project_name
  environment  = var.environment
}

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