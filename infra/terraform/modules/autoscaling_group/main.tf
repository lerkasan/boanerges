#resource "aws_instance" "appserver" {
#  for_each                    = toset(local.availability_zones)
#
#  availability_zone           = each.value
####  subnet_id                   = aws_subnet.private[each.value].id
#  subnet_id                   = var.private_subnets_ids[index(local.availability_zones, each.value)]
#  associate_public_ip_address = false
#  ami                         = data.aws_ami.ubuntu.id
#  instance_type               = var.ec2_instance_type
#  user_data                   = data.cloudinit_config.user_data.rendered
#  key_name                    = var.appserver_private_ssh_key_name
#  vpc_security_group_ids      = [ var.ec2_sg_id ]
#  monitoring                  = true
#  iam_instance_profile        = aws_iam_instance_profile.this.name
#
#  root_block_device {
#    delete_on_termination = true
#    volume_type           = "gp3"
#    volume_size           = 10
#  }
#
#tags = {
#    Name        = join("_", [var.project_name, "_appserver"])
#    terraform   = "true"
#    environment = var.environment
#    project     = var.project_name
#  }
#
#  # Dependency is used to ensure that EC2 instance will have Internet access during userdata execution to be able to install packages
####  depends_on  = [ aws_internet_gateway.this, aws_nat_gateway.this ]
#}


resource "aws_autoscaling_group" "appserver" {
  name                      = join("_", [var.project_name, "_autoscaling_group"])
  max_size                  = 4
  min_size                  = 2 # 2
  health_check_grace_period = 1500
  health_check_type         = "ELB"
  desired_capacity          = 2 # 2
#  target_group_arns         = [ var.alb_target_group_arn ]
  vpc_zone_identifier       = var.private_subnets_ids

  protect_from_scale_in     = true   #  To enable managed termination protection for a capacity provider, the Auto Scaling group must have instance protection from scale in enabled

#  default_instance_warmup     = 300

  default_cooldown          = 300

  enabled_metrics = [
    "GroupMinSize",
    "GroupMaxSize",
    "GroupDesiredCapacity",
    "GroupInServiceInstances",
    "GroupPendingInstances",
    "GroupStandbyInstances",
    "GroupTerminatingInstances",
    "GroupTotalInstances"
  ]

  launch_template {
    id      = aws_launch_template.ecs_node.id
    version = "$Latest"
  }

  instance_refresh {
    strategy = "Rolling"
  }

  lifecycle {
    create_before_destroy = true

    # Optional: Allow external changes without Terraform plan difference
    ignore_changes = [ desired_capacity ]
  }

  timeouts {
    delete = "15m"
  }

  tag {
    key                 = "Name"
    value               = join("_", [var.project_name, "_appserver_autoscaling"])
    propagate_at_launch = true
  }

  tag {
    key                 = "terraform"
    value               = true
    propagate_at_launch = true
  }

  tag {
    key                 = "environment"
    value               = var.environment
    propagate_at_launch = true
  }

  tag {
    key                 = "project"
    value               = var.project_name
    propagate_at_launch = true
  }

  tag {
    key                 = "AmazonECSManaged"
    value               = true
    propagate_at_launch = true
  }
}

data "template_file" "ecs_node_user_data" {
  template = file("${path.module}/templates/ecs_userdata.sh")

  vars = {
    ecs_cluster_name = var.ecs_cluster_name
  }
}


resource "aws_launch_template" "ecs_node" {
  name                        = join("_", [var.project_name, "_ecs_node"])

  image_id                    = data.aws_ami.amazon_linux2.id
#  image_id                    = data.aws_ami.ubuntu.id
  instance_type               = var.ec2_instance_type
  user_data                   = base64encode(data.template_file.ecs_node_user_data.rendered)
#  user_data                   = data.cloudinit_config.user_data.rendered
  key_name                    = var.appserver_private_ssh_key_name
#  vpc_security_group_ids      = [ var.ec2_sg_id, var.frontend_sg_id, var.backend_sg_id ]   # for bridge network mode of task definition
  vpc_security_group_ids      = [ var.ec2_sg_id ]

  monitoring {
    enabled = true
  }

  iam_instance_profile {
    name = aws_iam_instance_profile.this.name
  }

#  network_interfaces {
#    associate_public_ip_address = false
#  }

  block_device_mappings {
    device_name = "/dev/sda1"

    ebs {
      delete_on_termination = true
      volume_type           = "gp3"
      volume_size           = 10
    }
  }

  tags = {
      Name        = join("_", [var.project_name, "_appserver"])
      terraform   = "true"
      environment = var.environment
      project     = var.project_name
    }
}


resource "aws_iam_instance_profile" "this" {
  name = join("_", [var.project_name, "_ec2_profile"])
  role = aws_iam_role.appserver_iam_role.name
}

resource "aws_iam_role" "appserver_iam_role" {
  name        = join("", [title(var.project_name), "AppserverRole"])
  description = "The role for EC2 instances for appserver"
  assume_role_policy = data.aws_iam_policy_document.assume_role_ec2.json
}

data "aws_iam_policy_document" "assume_role_ec2" {
  statement {
    sid           = "EC2AssumeRole"
    effect        = "Allow"
    actions       = [ "sts:AssumeRole" ]
    principals {
      type        = "Service"
      identifiers = [
        "ec2.amazonaws.com",
        "ecs.amazonaws.com"
      ]
    }
  }
}

resource "aws_iam_role_policy_attachment" "AmazonSSMManagedInstanceCore" {
  role       = aws_iam_role.appserver_iam_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_role_policy_attachment" "AmazonEC2ContainerServiceforEC2Role" {
  role       = aws_iam_role.appserver_iam_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2ContainerServiceforEC2Role"
}

#resource "aws_iam_role_policy_attachment" "GetDeploymentAndGetSSMParameter" {
#  role       = aws_iam_role.appserver_iam_role.name
#  policy_arn = aws_iam_policy.read_access_to_parameters_and_deployments.arn
#}
#
#resource "aws_iam_policy" "read_access_to_parameters_and_deployments" {
#  name        = "read-access-to-parameters-and-deployments"
#  description = "Allow to get deployment information to retrieve a commitId hash"
#  policy      = data.aws_iam_policy_document.read_access_to_parameters_and_deployments.json
#}

#data "aws_iam_policy_document" "read_access_to_parameters_and_deployments" {
##  statement {
##    sid       = "CodeDeployGetDeployments"
##    effect    = "Allow"
##    actions   = [
##      "codedeploy:GetDeployment",
##      "codedeploy:ListDeployments"
##    ]
##    resources = [ var.codedeploy_deployment_group_arn ]
##  }
#
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
#
#  statement {
#    sid       = "KMSDecrypt"
#    effect    = "Allow"
#    actions   = [
#      "kms:Decrypt",
#      "kms:DescribeKey"
#    ]
#    resources = [ var.kms_key_arn ]
#  }
#
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
#}

data "aws_ami" "amazon_linux2" {
  owners      = ["amazon"]
  most_recent = true

  filter {
    name   = "virtualization-type"
    values = [ var.ami_virtualization ]
  }

  filter {
    name   = "architecture"
    values = [ local.ami_architecture ]
  }

  filter {
    name   = "owner-alias"
    values = ["amazon"]
  }

  filter {
    name   = "name"
    values = ["amzn2-ami-ecs-hvm-*-x86_64-ebs"]
  }
}


#data "aws_ami" "amazon_linux_2" {
#  most_recent = true
#
#  filter {
#    name   = "virtualization-type"
#    values = ["hvm"]
#  }
#
#  filter {
#    name   = "owner-alias"
#    values = ["amazon"]
#  }
#
#  filter {
#    name   = "name"
#    values = ["amzn2-ami-ecs-hvm-*-x86_64-ebs"]
#  }
#
#  owners = ["amazon"]
#}


data "aws_ami" "ubuntu" {
  most_recent = true

  filter {
    name   = "name"
    values = [ local.ami_name ]
  }

  filter {
    name   = "architecture"
    values = [ local.ami_architecture ]
  }

  filter {
    name   = "virtualization-type"
    values = [ var.ami_virtualization ]
  }

  owners = [ local.ami_owner_id ]
}

data "aws_ssm_parameter" "admin_public_ssh_keys" {
  for_each = toset(var.admin_public_ssh_keys)

  name = each.value
  with_decryption = true
}

# ------------------- User data for cloud-init --------------------------
# The public ssh key will added to ec2 instances using cloud-init
data "cloudinit_config" "user_data" {
  gzip          = true
  base64_encode = true

  part {
    content_type = "text/cloud-config"
    content      = templatefile("${path.module}/templates/userdata.tftpl", {
      public_ssh_keys: [ for key in data.aws_ssm_parameter.admin_public_ssh_keys: key.value]
    })
  }
}

resource "aws_ec2_instance_connect_endpoint" "this" {
  subnet_id             = var.private_subnets_ids[0]
#  subnet_id             = aws_subnet.private[local.availability_zones[0]].id
  security_group_ids    = [ var.ec2_connect_endpoint_sg_id ]
  preserve_client_ip    = true

  tags = {
    Name        = join("_", [var.project_name, "ec2_connect_endpoint"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

# According to AWS documentation, there are no quotas for ec2 instance connect endpoints, however recently it is not possible to create more than 1 ec2 instance connect endpoint
#resource "aws_ec2_instance_connect_endpoint" "this" {
#  for_each              = toset(local.availability_zones)
#
#  subnet_id             = aws_subnet.private[each.value].id
#  security_group_ids    = [ aws_security_group.ec2_connect_endpoint.id ]
#  preserve_client_ip    = true
#
#  tags = {
#    Name        = join("_", [var.project_name, "ec2_connect_endpoint"])
#    terraform   = "true"
#    environment = var.environment
#    project     = var.project_name
#  }
#}

resource "aws_iam_policy" "connect_via_ec2_instance_connect_endpoint" {
  name        = "ec2-instance-connect"
  description = "Allow to connect to EC2 instance via EC2 Instance Connect Endpoint"
  policy      = data.aws_iam_policy_document.connect_to_ec2_via_ec2_instance_connect_endpoint.json
}

data "aws_iam_policy_document" "connect_to_ec2_via_ec2_instance_connect_endpoint" {
  #  dynamic "statement" {
  #    for_each = toset(local.availability_zones)

  statement {
    sid     = "EC2ConnectEndpoint"
    effect  = "Allow"
    actions = [
      "ec2-instance-connect:OpenTunnel"
    ]
    resources = [ aws_ec2_instance_connect_endpoint.this.arn ]
  #  resources = [for az in toset(local.availability_zones) : aws_ec2_instance_connect_endpoint.this[az].arn ]  # Because of the issue with creating more than 1 ec2 instance connect endpoint, we use only 1 ec2 instance connect endpoint

    condition {
      test     = "NumericEquals"
      variable = "ec2-instance-connect:remotePort"
      values   = [local.ssh_port]
    }

#    condition {
#      test     = "IpAddress"
#      variable = "ec2-instance-connect:privateIpAddress"
#      values   = [ for az in toset(local.availability_zones) : aws_instance.appserver[az].private_ip]
#    }
  }

  statement {
    sid     = "EC2DescribeConnectEndpoints"
    effect  = "Allow"
    actions = [
      "ec2:DescribeInstances",
      "ec2:DescribeInstanceConnectEndpoints"
    ]
    resources = ["*"]
  }
}

resource "aws_cloudwatch_log_group" "nginx_logs" {
  for_each      = toset(var.log_group_names)

  name          = join("_", [var.project_name, var.log_group_names[index(var.log_group_names, each.value)]])

  tags          = {
    Name        = join("_", [var.project_name, "nginx_log_group"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

locals {
  ssh_port = 22
  availability_zones     = [ for az_letter in var.az_letters : format("%s%s", var.aws_region, az_letter) ]
  ami_architecture       = var.ami_architectures[var.os_architecture]
  ami_owner_id           = var.ami_owner_ids[var.os]
  ami_name               = local.ubuntu_ami_name_filter
  ubuntu_ami_name_filter = format("%s/images/%s-ssd/%s-%s-%s-%s-%s-*", var.os, var.ami_virtualization, var.os,
    var.os_releases[var.os_version], var.os_version, var.os_architecture, var.os_product)
}
