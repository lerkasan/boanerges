resource "aws_security_group" "ec2_ecs_node" {
  name        = join("_", [var.project_name, "_ec2_ecs_node_security_group"])
  description = "security group for EC2 nodes of ECS cluster"
  vpc_id      = var.vpc_id

  tags = {
    Name        = join("_", [var.project_name, "_ec2_ecs_node_sg"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_security_group" "backend" {
  name        = join("_", [var.project_name, "_backend_security_group"])
  description = "security group for application backend server"
  vpc_id      = var.vpc_id

  tags = {
    Name        = join("_", [var.project_name, "_backend_sg"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_security_group" "frontend" {
  name        = join("_", [var.project_name, "_frontend_security_group"])
  description = "security group for application frontend server"
  vpc_id      = var.vpc_id

  tags = {
    Name        = join("_", [var.project_name, "_frontend_sg"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_security_group" "ec2_connect_endpoint" {
  name        = join("_", [var.project_name, "ec2_connect_endpoint_sg"])
  description = "security group for ec2 instance connect endpoint"
  vpc_id      = var.vpc_id

  tags = {
    Name        = join("_", [var.project_name, "ec2_connect_endpoint_sg"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_security_group" "alb" {
  name        = join("_", [var.project_name, "_alb_security_group"])
  description = "security group for loadbalancer"
  vpc_id      = var.vpc_id

  tags = {
    Name        = join("_", [var.project_name, "_alb_sg"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }

  #  # Dependency is used to ensure that VPC has an Internet gateway
  #  depends_on  = [ aws_internet_gateway.this ]
}

resource "aws_security_group" "database" {
  name        = join("_", [var.project_name, "_db_security_group"])
  description = "Demo security group for database"
  vpc_id      = var.vpc_id

  tags = {
    Name        = join("_", [var.project_name, "_database_sg"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }

  # Dependency is used to ensure that VPC has NAT gateways
  #  depends_on  = [ aws_nat_gateway.this ]
}

# -------------------- Load balancer rules ---------------------------

resource "aws_security_group_rule" "lb_allow_inbound_https_from_all" {
  type              = "ingress"
  description       = "HTTPS ingress"
  from_port         = local.https_port
  to_port           = local.https_port
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.alb.id
}

resource "aws_security_group_rule" "lb_allow_inbound_http_from_all" {
  type              = "ingress"
  description       = "HTTP ingress"
  from_port         = local.http_port
  to_port           = local.http_port
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.alb.id
}

resource "aws_security_group_rule" "lb_allow_outbound_http_to_frontend" {
  type              = "egress"
  description       = "HTTPS egress"
  from_port         = local.frontend_http_port
  to_port           = local.frontend_http_port
  protocol          = "tcp"
  source_security_group_id = aws_security_group.frontend.id
  security_group_id = aws_security_group.alb.id
}

resource "aws_security_group_rule" "lb_allow_outbound_http_to_backend" {
  type              = "egress"
  description       = "HTTP egress"
  from_port         = local.backend_http_port
  to_port           = local.backend_http_port
  protocol          = "tcp"
  source_security_group_id = aws_security_group.backend.id
  security_group_id = aws_security_group.alb.id
}

# -------------------- Appserver rules ---------------------------

resource "aws_security_group_rule" "frontend_allow_inbound_http_from_lb" {
  type              = "ingress"
  description       = "HTTPS ingress"
  from_port         = local.frontend_http_port
  to_port           = local.frontend_http_port
  protocol          = "tcp"
  source_security_group_id = aws_security_group.alb.id
  security_group_id = aws_security_group.frontend.id
}

resource "aws_security_group_rule" "backend_allow_inbound_http_from_lb" {
  type              = "ingress"
  description       = "HTTP ingress"
  from_port         = local.backend_http_port
  to_port           = local.backend_http_port
  protocol          = "tcp"
  source_security_group_id = aws_security_group.alb.id
  security_group_id = aws_security_group.backend.id
}

resource "aws_security_group_rule" "backend_allow_outbound_https_to_all" {
  type              = "egress"
  description       = "HTTPS egress"
  from_port         = local.https_port
  to_port           = local.https_port
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.backend.id
}

resource "aws_security_group_rule" "backend_allow_outbound_http_to_all" {
  type              = "egress"
  description       = "HTTP egress"
  from_port         = local.http_port
  to_port           = local.http_port
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.backend.id
}

resource "aws_security_group_rule" "frontend_allow_outbound_https_to_all" {
  type              = "egress"
  description       = "HTTPS egress"
  from_port         = local.https_port
  to_port           = local.https_port
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.frontend.id
}

resource "aws_security_group_rule" "frontend_allow_outbound_http_to_all" {
  type              = "egress"
  description       = "HTTP egress"
  from_port         = local.http_port
  to_port           = local.http_port
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.frontend.id
}

resource "aws_security_group_rule" "ec2_ecs_node_allow_outbound_https_to_all" {
  type              = "egress"
  description       = "HTTPS egress"
  from_port         = local.https_port
  to_port           = local.https_port
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.ec2_ecs_node.id
}

resource "aws_security_group_rule" "ec2_ecs_node_allow_outbound_http_to_all" {
  type              = "egress"
  description       = "HTTP egress"
  from_port         = local.http_port
  to_port           = local.http_port
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.ec2_ecs_node.id
}

resource "aws_security_group_rule" "backend_allow_outbound_smtps_to_all" {
  type              = "egress"
  description       = "SMTPS egress"
  from_port         = local.smtps_port
  to_port           = local.smtps_port
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.backend.id
}

resource "aws_security_group_rule" "ec2_ecs_node_allow_inbound_ssh_from_ec2_connect_endpoint" {
  type              = "ingress"
  description       = "SSH ingress"
  from_port         = local.ssh_port
  to_port           = local.ssh_port
  protocol          = "tcp"
  source_security_group_id = aws_security_group.ec2_connect_endpoint.id
  security_group_id = aws_security_group.ec2_ecs_node.id
}

resource "aws_security_group_rule" "ec2_ecs_node_allow_inbound_ssh_from_admin_ip" {
  type              = "ingress"
  description       = "SSH ingress"
  from_port         = local.ssh_port
  to_port           = local.ssh_port
  protocol          = "tcp"
  cidr_blocks       = [format("%s/%s", local.admin_public_ip, 32)]
  security_group_id = aws_security_group.ec2_ecs_node.id
}

resource "aws_security_group_rule" "backend_allow_outbound_to_database" {
  type                     = "egress"
  description              = "MySQL egress"
  from_port                = local.mysql_port
  to_port                  = local.mysql_port
  protocol                 = "tcp"

  source_security_group_id = aws_security_group.database.id
  security_group_id        = aws_security_group.backend.id
}

# -------------------- Database rules ---------------------------

resource "aws_security_group_rule" "database_allow_inbound_from_backend" {
  type                     = "ingress"
  description              = "MySQL ingress"
  from_port                = local.mysql_port
  to_port                  = local.mysql_port
  protocol                 = "tcp"

  source_security_group_id = aws_security_group.backend.id
  security_group_id        = aws_security_group.database.id
}

# -------------------- EC2 Instance Connect Endpoint rules ---------------------------

resource "aws_security_group_rule" "ec2_connect_endpoint_allow_outbound_ssh_to_ec2_ecs_node" {
  type              = "egress"
  description       = "SSH egress"
  from_port         = local.ssh_port
  to_port           = local.ssh_port
  protocol          = "tcp"
  source_security_group_id = aws_security_group.ec2_ecs_node.id
  security_group_id        = aws_security_group.ec2_connect_endpoint.id
}

locals {
  ssh_port   = 22
  http_port  = 80
  https_port = 443
  smtps_port = 587
  mysql_port = 3306
  frontend_http_port = 80
  backend_http_port  = 8080

  admin_public_ip = data.external.admin_public_ip.result["admin_public_ip"]
}

# ------------- Obtain my public IP to grant SSH access -------------------
data "external" "admin_public_ip" {
  program = ["bash", "-c", "jq -n --arg admin_public_ip $(dig +short myip.opendns.com @resolver1.opendns.com -4) '{\"admin_public_ip\":$admin_public_ip}'"]
}
